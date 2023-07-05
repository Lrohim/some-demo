package com.shop.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.QueryBindings;
import com.shop.goods.feign.SkuFeign;
import com.shop.goods.pojo.Sku;
import com.shop.search.dao.SkuEsMapper;
import com.shop.search.pojo.SkuInfo;
import com.shop.search.service.SkuService;
import entity.Result;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuFeign skuFeign;

    @Autowired
    private SkuEsMapper skuEsMapper;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Override
    public Map<String, Object> search(Map<String, String> searchMap) {
        NativeSearchQueryBuilder builder = buildBasicQuery(searchMap);

        Map<String, Object> resultMap = searchList(builder);

        Map<String,Object> map = searchFieldList(builder, searchMap);

        if(searchMap==null || StringUtils.isEmpty(searchMap.get("category"))){
            resultMap.put("categoryNames",map.get("categoryNames"));
        }

        if(searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            resultMap.put("brandNames", map.get("brandNames"));
        }

        resultMap.put("specNames", map.get("specNames"));

        return resultMap;
    }

    private NativeSearchQueryBuilder buildBasicQuery(Map<String, String> searchMap) {
        NativeSearchQueryBuilder builder= new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        if(searchMap!=null && searchMap.size()>0){
            String keywords = searchMap.get("keywords");
            if(!StringUtils.isEmpty(keywords)){
                boolQueryBuilder.must(QueryBuilders.queryStringQuery(keywords).field("name"));
            }

            String category=searchMap.get("category");
            if(!StringUtils.isEmpty(category)){
                boolQueryBuilder.must(QueryBuilders.termQuery("categoryName",category));
            }

            String brand=searchMap.get("brand");
            if(!StringUtils.isEmpty(brand)){
                boolQueryBuilder.must(QueryBuilders.termQuery("brandName",brand));
            }

            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                String key = entry.getKey();
                if(key.startsWith("spec_")){
                    String value=entry.getValue();
                    boolQueryBuilder.must(QueryBuilders.termQuery("specMap."+key.substring(5)+".keyword",value));
                }
            }

            String price=searchMap.get("price");
            if(!StringUtils.isEmpty(price)){
                price=price.replace("元","").replace("以上","");
                String[] split = price.split("-");
                if(split!=null && split.length>0){
                    boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gt(Integer.parseInt(split[0])));
                    if(split.length==2){
                        boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(Integer.parseInt(split[1])));
                    }
                }
            }

            Integer pageNum = convertPage(searchMap);
            Integer pageSize=10;
            builder.withPageable(PageRequest.of(pageNum-1,pageSize));

            String sortField=searchMap.get("sortField");
            String sortRule=searchMap.get("sortRule");
            if(!StringUtils.isEmpty(sortField) && !StringUtils.isEmpty(sortRule)){
                builder.withSort(new FieldSortBuilder(sortField).order(SortOrder.valueOf(sortRule)));
            }

            builder.withQuery(boolQueryBuilder);
        }
        return builder;
    }



    private Integer convertPage(Map<String, String> searchMap) {
        Integer pageNum=1;
        String num=searchMap.get("pageNum");
        if(!StringUtils.isEmpty(num)){
            pageNum=Integer.parseInt(num);
        }
        return pageNum;
    }

    private Map<String, Object> searchList(NativeSearchQueryBuilder builder) {
        HighlightBuilder.Field field=new HighlightBuilder.Field("name");
        field.preTags("<em style=\"color:red;\">");
        field.postTags("</em>");
        field.fragmentSize(100);
        builder.withHighlightFields(field);
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<T> list=new ArrayList<>();
                for (SearchHit hit : searchResponse.getHits()) {
                    SkuInfo skuInfo = JSON.parseObject(hit.getSourceAsString(),SkuInfo.class);
                    HighlightField highlightField = hit.getHighlightFields().get("name");
                    if(highlightField!=null && highlightField.getFragments()!=null){
                        Text[] fragment=highlightField.getFragments();
                        StringBuffer buffer=new StringBuffer();
                        for (Text text : fragment) {
                            buffer.append(text.toString());
                        }
                        skuInfo.setName(buffer.toString());
                    }
                    list.add((T) skuInfo);
                }
                return new AggregatedPageImpl<T>(list,pageable,searchResponse.getTotalShards());
            }
        });
        long totalElements = skuInfos.getTotalElements();
        int totalPages = skuInfos.getTotalPages();
        List<SkuInfo> content = skuInfos.getContent();
        Map<String,Object> resultMap=new HashMap<String,Object>();
        NativeSearchQuery query=builder.build();
        Pageable pageable = query.getPageable();
        resultMap.put("pageSize",pageable.getPageSize());
        resultMap.put("pageNumber",pageable.getPageNumber());
        resultMap.put("rows",content);
        resultMap.put("total",totalElements);
        resultMap.put("totalPages",totalPages);
        return resultMap;
    }

    private List<String> getGroupList(StringTerms stringTerms){
        List<String> strings=new ArrayList<>();
        for(StringTerms.Bucket bucket:stringTerms.getBuckets()){
            strings.add(bucket.getKeyAsString());
        }
        return strings;
    }

    private Map<String,Object> searchFieldList(NativeSearchQueryBuilder builder, Map<String, String> searchMap) {
        builder.addAggregation(AggregationBuilders.terms("skuCategory").field("categoryName"));
        builder.addAggregation(AggregationBuilders.terms("skuBrand").field("brandName"));
        builder.addAggregation(AggregationBuilders.terms("skuSpec").field("spec.keyword").size(10000));
        AggregatedPage<SkuInfo> skuInfos = elasticsearchTemplate.queryForPage(builder.build(), SkuInfo.class);
        List<String> skuCategory=getGroupList(skuInfos.getAggregations().get("skuCategory"));
        List<String> skuBrand=getGroupList(skuInfos.getAggregations().get("skuBrand"));
        List<String> skuSpec=getGroupList(skuInfos.getAggregations().get("skuSpec"));
        Map<String,Object> map=new HashMap<>();
        if(searchMap==null || StringUtils.isEmpty(searchMap.get("category"))){
            map.put("categoryNames",skuCategory);
        }

        if(searchMap==null || StringUtils.isEmpty(searchMap.get("brand"))) {
            map.put("brandNames", skuBrand);
        }

        Map<String, Set<String>> allSpec = new HashMap<>();
        for(String spec:skuSpec){
            Map<String,String> stringMap = JSON.parseObject(spec, Map.class);
            for(Map.Entry<String,String> entry:stringMap.entrySet()){
                Set<String> specSet = allSpec.get(entry.getKey());
                if(specSet==null || specSet.isEmpty()){
                    specSet=new HashSet<String>();
                }
                specSet.add(entry.getValue());
                allSpec.put(entry.getKey(),specSet);
            }
        }
        map.put("specNames", allSpec);

        return map;
    }

    @Override
    public void importDate() {
        Result<List<Sku>> skuResult = skuFeign.findAll();
        List<SkuInfo> skuInfos= JSON.parseArray(JSON.toJSONString(skuResult.getData()),SkuInfo.class);

        for(SkuInfo skuInfo:skuInfos){
            Map<String,Object> map=JSON.parseObject(skuInfo.getSpec(),Map.class);
            skuInfo.setSpecMap(map);
        }

        skuEsMapper.saveAll(skuInfos);
    }
}
