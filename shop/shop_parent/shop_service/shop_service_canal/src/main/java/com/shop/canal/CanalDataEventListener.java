package com.shop.canal;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.annotation.CanalEventListener;
import com.wwjd.starter.canal.annotation.ListenPoint;
import com.wwjd.starter.canal.annotation.content.DeleteListenPoint;
import com.wwjd.starter.canal.annotation.content.InsertListenPoint;
import com.wwjd.starter.canal.annotation.content.UpdateListenPoint;

@CanalEventListener
public class CanalDataEventListener {

    /**
     * 增加监听
     */
    @InsertListenPoint
    public void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for(CanalEntry.Column column:rowData.getAfterColumnsList()){
            System.out.println("列明:" + column.getName() + "---------变更的数据：" + column.getValue());
        }
    }

    /**
     * 修改监听
     */
    @UpdateListenPoint
    public void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for(CanalEntry.Column column:rowData.getAfterColumnsList()){
            System.out.println("列明:" + column.getName() + "---------变更的数据：" + column.getValue());
        }
    }

    /**
     * 删除监听
     */
    @DeleteListenPoint
    public void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for(CanalEntry.Column column:rowData.getAfterColumnsList()){
            System.out.println("列明:" + column.getName() + "---------变更的数据：" + column.getValue());
        }
    }

    /**
     * 自定义（指定监听DML，监听的数据库、监听的表）
     */
    @ListenPoint(eventType = {CanalEntry.EventType.DELETE, CanalEntry.EventType.UPDATE},schema = {"goods"},destination = "example")
    public void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for(CanalEntry.Column column:rowData.getAfterColumnsList()){
            System.out.println("列明:" + column.getName() + "---------变更的数据：" + column.getValue());
        }
    }
}
