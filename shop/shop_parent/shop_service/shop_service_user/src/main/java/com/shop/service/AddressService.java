package com.shop.service;

import com.shop.user.pojo.Address;

import java.util.List;

public interface AddressService {

    List<Address> list(String username);
}
