package com.shop.dao;

import com.shop.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {

    @Update("update tb_user set points=points+#{points} where username=#{username}")
    void addPoint(@Param("username") String username,@Param("points")  Integer points);
}
