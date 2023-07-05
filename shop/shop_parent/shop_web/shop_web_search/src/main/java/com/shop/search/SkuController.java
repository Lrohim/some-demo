package com.shop.search;

import com.shop.search.feign.SkuFeign;
import com.shop.search.pojo.SkuInfo;
import entity.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(value = "/search")
public class SkuController {

    @Autowired
    private SkuFeign skuFeign;

    @GetMapping(value = "list")
    public String search(@RequestParam(required = false) Map<String,String> searchMap, Model model){
        Map<String,Object> search=skuFeign.search(searchMap);
        Page<SkuInfo> page=new Page<SkuInfo>(Long.parseLong(search.get("total").toString()),Integer.parseInt(search.get("pageNumber").toString())+1,Integer.parseInt(search.get("pageSize").toString()));
        model.addAttribute("pageInfo",page);
        model.addAttribute("resultMap",search);
        model.addAttribute("searchMap",searchMap);
        model.addAttribute("url",urlConcat(searchMap)[0]);
        model.addAttribute("sortUrl",urlConcat(searchMap)[1]);
        return "search";
    }

    private String[] urlConcat(Map<String,String> searchMap){
        StringBuilder stringBuilder=new StringBuilder("/search/list");
        StringBuilder stringBuilder2=new StringBuilder("/search/list");
        if(searchMap!=null && searchMap.size()!=0){
            stringBuilder.append("?");
            stringBuilder2.append("?");
            for (Map.Entry<String, String> entry : searchMap.entrySet()) {
                if(entry.getKey().equalsIgnoreCase("pageNum")){
                    continue;
                }
                stringBuilder.append(entry.getKey()+"="+entry.getValue()+"&");
                if(entry.getKey().equalsIgnoreCase("sortField") || entry.getKey().equalsIgnoreCase("sortRule")){
                    continue;
                }
                stringBuilder2.append(entry.getKey()+"="+entry.getValue()+"&");
            }
            stringBuilder.substring(0,stringBuilder.length()-1);
            stringBuilder2.substring(0,stringBuilder.length()-1);
        }
        return new String[]{stringBuilder.toString(),stringBuilder2.toString()};
    }
}
