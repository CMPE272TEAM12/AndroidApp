package com.vipulkanade.group12.cmpe272.model;

import com.vipulkanade.group12.cmpe272.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vipulkanade on 5/13/15.
 */
public class GetProductHistory {

    private String sItemName;
    private int iQuantity;
    private int iSentiment;

    public GetProductHistory() {
    }

    public GetProductHistory(JSONObject oJSONObject) {
        sItemName = oJSONObject.optString(Constants.ITEM_NAME);
        iQuantity = oJSONObject.optInt(Constants.QUANTITY);
        iSentiment = oJSONObject.optInt(Constants.SENTIMENT);
    }

    public String getItemName() {
        return sItemName;
    }

    public void setItemName(String sItemName) {
        this.sItemName = sItemName;
    }

    public int getQuantity() {
        return iQuantity;
    }

    public void setQuantity(int iQuantity) {
        this.iQuantity = iQuantity;
    }

    public int getSentiment() {
        return iSentiment;
    }

    public void setSentiment(int iSentiment) {
        this.iSentiment = iSentiment;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject oJSON = new JSONObject();
        oJSON.put(Constants.ITEM_NAME, sItemName);
        oJSON.put(Constants.QUANTITY, iQuantity);
        oJSON.put(Constants.SENTIMENT, iSentiment);
        return oJSON;
    }

}
