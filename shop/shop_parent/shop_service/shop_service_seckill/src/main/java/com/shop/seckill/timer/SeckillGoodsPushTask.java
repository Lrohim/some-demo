package com.shop.seckill.timer;

import com.shop.seckill.dao.SeckillMapper;
import com.shop.seckill.feign.SeckillGoods;
import entity.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class SeckillGoodsPushTask {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillMapper seckillMapper;

    @Scheduled(cron = "0/5 * * * * ?")
    public void loadGoodsPushRedis(){
        List<Date> dateMenus = DateUtil.getDateMenus();
        for (Date date : dateMenus) {
            String timeSpace="SeckillGoods_"+DateUtil.date2Str(date);
            Example example=new Example(SeckillGoods.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status",1);
            criteria.andGreaterThan("stockCount",0);
            criteria.andGreaterThanOrEqualTo("startTime",date);
            criteria.andLessThan("endTime",date);
            Set keys = redisTemplate.boundHashOps(date).keys();
            if(keys!=null && keys.size()>0){
                criteria.andNotIn("id",keys);
            }
            List<SeckillGoods> seckillGoods = seckillMapper.selectByExample(example);
            for (SeckillGoods good : seckillGoods) {
                redisTemplate.boundHashOps(timeSpace).put(good.getId(),good);
                redisTemplate.boundListOps("SeckillGoodsCountList_"+good.getId()).leftPushAll(putAllIds(good.getStockCount(),good.getId()));
            }
        }
    }

    public Long[] putAllIds(Integer num,Long id){
        Long[] ids=new Long[num];
        Arrays.stream(ids).parallel().forEach(x -> x=id);
        return ids;
    }
}
