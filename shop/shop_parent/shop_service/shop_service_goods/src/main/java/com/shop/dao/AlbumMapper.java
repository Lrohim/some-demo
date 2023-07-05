package com.shop.dao;

import com.shop.goods.pojo.Album;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AlbumMapper extends Mapper<Album> {
}
