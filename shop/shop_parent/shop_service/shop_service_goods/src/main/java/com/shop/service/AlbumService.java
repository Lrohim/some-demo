package com.shop.service;

import com.github.pagehelper.PageInfo;
import com.shop.goods.pojo.Album;

import java.util.List;

public interface AlbumService {

    /**
     * 新增品牌
     */
    boolean add(Album album);

    /**
     * 查询所有
     * @return
     */
    List<Album> findAll();

    /**
     * 根据ID查询
     */
    Album findById(Integer id);

    /**
     * 根据ID修改商品
     */
    boolean update(Album album);

    /**
     * 根据ID删除商品
     */
    boolean deleteById(Integer id);

    /**
     * 根据条件查询
     */
    List<Album> findBySelective(Album album);

    /**
     * 分页查询
     */
    PageInfo<Album> findByPage(Integer pageNum, Integer pageSize);

    /**
     * 根据条件分页查询
     */
    PageInfo<Album> findByPageAndAlbum(Album album,Integer pageNum, Integer pageSize);
}
