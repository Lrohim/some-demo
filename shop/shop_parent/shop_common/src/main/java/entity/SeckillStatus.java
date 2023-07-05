package entity;

import java.io.Serializable;
import java.util.Date;

public class SeckillStatus implements Serializable {

    private String username;

    private Integer status;

    private Long goodsId;

    private Long orderId;

    private String time;

    private Float money;

    private Date createTime;

    public SeckillStatus() {
    }

    public SeckillStatus(String username,Date createTime,Integer status, Long goodsId,String time){
        this.username = username;
        this.status = status;
        this.goodsId = goodsId;
        this.time = time;
        this.createTime = createTime;
    }

    public SeckillStatus(String username, Integer status, Long goodsId, Long orderId, String time, Float money, Date createTime) {
        this.username = username;
        this.status = status;
        this.goodsId = goodsId;
        this.orderId = orderId;
        this.time = time;
        this.money = money;
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
