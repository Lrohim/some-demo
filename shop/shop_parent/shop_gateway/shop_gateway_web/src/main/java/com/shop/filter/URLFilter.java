package com.shop.filter;

public class URLFilter {

    private static final String allUrl="/user/login,/api/user/add";

    public static boolean hasAuthorize(String url){
        String[] urls=allUrl.split(",");
        for (String s : urls) {
            if(s.equals(url)){
                return false;
            }
        }
        return true;
    }
}
