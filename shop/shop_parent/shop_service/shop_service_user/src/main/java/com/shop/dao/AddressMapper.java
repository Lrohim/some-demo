package com.shop.dao;

import com.shop.user.pojo.Address;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AddressMapper extends Mapper<Address> {

}
