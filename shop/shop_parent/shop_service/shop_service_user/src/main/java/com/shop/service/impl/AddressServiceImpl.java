package com.shop.service.impl;

import com.shop.dao.AddressMapper;
import com.shop.service.AddressService;
import com.shop.user.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<Address> list(String username) {
        Address address = new Address();
        address.setUsername(username);
        List<Address> select = addressMapper.select(address);
        return select;
    }
}
