package com.vipulkanade.group12.cmpe272.model;

import com.vipulkanade.group12.cmpe272.constants.Constants;

import org.json.JSONObject;

/**
 * Created by vipulkanade on 5/13/15.
 */
public class GetProduct {

    private int iItemID;
    private String sItemName;
    private int iItemQuantity;
    private int iStoreID;
    private String sItemCode;
    private int iItemPrice;

    public GetProduct() {
    }

    public GetProduct(JSONObject oJSONObject) {
        iItemID = oJSONObject.optInt(Constants.ITEM_ID);
        sItemName = oJSONObject.optString(Constants.ITEM_NAME);
        iItemQuantity = oJSONObject.optInt(Constants.ITEM_QUANTITY);
        iStoreID = oJSONObject.optInt(Constants.STORE_ID);
        sItemCode = oJSONObject.optString(Constants.ITEM_CODE);
        iItemPrice = oJSONObject.optInt(Constants.ITEM_PRICE);
    }

    public int getItemID() {
        return iItemID;
    }

    public void setItemID(int iItemID) {
        this.iItemID = iItemID;
    }

    public String getItemName() {
        return sItemName;
    }

    public void setItemName(String sItemName) {
        this.sItemName = sItemName;
    }

    public int getItemQuantity() {
        return iItemQuantity;
    }

    public void setItemQuantity(int iItemQuantity) {
        this.iItemQuantity = iItemQuantity;
    }

    public int getStoreID() {
        return iStoreID;
    }

    public void setStoreID(int iStoreID) {
        this.iStoreID = iStoreID;
    }

    public String getItemCode() {
        return sItemCode;
    }

    public void setItemCode(String sItemCode) {
        this.sItemCode = sItemCode;
    }

    public int getItemPrice() {
        return iItemPrice;
    }

    public void setItemPrice(int iItemPrice) {
        this.iItemPrice = iItemPrice;
    }
}
