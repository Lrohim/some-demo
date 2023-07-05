package com.shop.service.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shop.dao.AlbumMapper;
import com.shop.goods.pojo.Album;
import com.shop.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public boolean add(Album album) {
        int i = albumMapper.insertSelective(album);
        return i==1;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albums = albumMapper.selectAll();
        return albums;
    }

    @Override
    public Album findById(Integer id) {
        Album album = albumMapper.selectByPrimaryKey(id);
        return album;
    }

    @Override
    public boolean update(Album album) {
        int i = albumMapper.updateByPrimaryKeySelective(album);
        return i==1;
    }

    @Override
    public boolean deleteById(Integer id) {
        int i = albumMapper.deleteByPrimaryKey(id);
        return i>=1;
    }

    @Override
    public List<Album> findBySelective(Album album) {
        List<Album> albums = albumMapper.selectByExample(createExample(album));
        return albums;
    }

    @Override
    public PageInfo<Album> findByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Album> albums = albumMapper.selectAll();
        return new PageInfo<Album>(albums);
    }

    @Override
    public PageInfo<Album> findByPageAndAlbum(Album album, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Album> albums = albumMapper.selectByExample(createExample(album));
        return new PageInfo<Album>(albums);
    }

    private Example createExample(Album album){
        Example example=new Example(Album.class);
        Example.Criteria criteria=example.createCriteria();
        if(album!=null){
            if(!StringUtils.isEmpty(album.getTitle())){
                criteria.andLike("title","%"+album.getTitle()+"%");
            }
        }
        return example;
    }
}
