package com.vipulkanade.group12.cmpe272.webservices;

/**
 * Created by vipulkanade on 5/12/15.
 */
public interface WebserviceURL {

    String BASE_URL = "http://52.8.84.224:3232/";

    String ADD_PRODUCT_TO_INVENTORY = BASE_URL + "addproduct";
    String BUY_PRODUCT              = BASE_URL + "soldproduct";
    String GET_PRODUCT              = BASE_URL + "getproduct";
    String GET_EMPLOYEE             = BASE_URL + "getemployee";
}
