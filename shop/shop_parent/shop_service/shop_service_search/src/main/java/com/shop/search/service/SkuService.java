package com.shop.search.service;

import java.util.List;
import java.util.Map;

public interface SkuService {

    Map<String,Object> search(Map<String,String> searchMap);

    void importDate();
}
