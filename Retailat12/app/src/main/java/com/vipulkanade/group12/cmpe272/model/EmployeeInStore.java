package com.vipulkanade.group12.cmpe272.model;

import com.vipulkanade.group12.cmpe272.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vipulkanade on 5/13/15.
 */
public class EmployeeInStore {

    private String sEmployeeName;
    private int iEmployeeID;

    public EmployeeInStore() {
    }

    public EmployeeInStore(JSONObject oJSONObject) {
        sEmployeeName = oJSONObject.optString(Constants.EMPLOYEE_NAME);
        iEmployeeID = oJSONObject.optInt(Constants.EMPLOYEE_ID);

    }

    public String getEmployeeName() {
        return sEmployeeName;
    }

    public void setEmployeeName(String sEmployeeName) {
        this.sEmployeeName = sEmployeeName;
    }

    public int getEmployeeID() {
        return iEmployeeID;
    }

    public void setEmployeeID(int iEmployeeID) {
        this.iEmployeeID = iEmployeeID;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject oJSON = new JSONObject();
        oJSON.put(Constants.EMPLOYEE_NAME, sEmployeeName);
        oJSON.put(Constants.EMPLOYEE_ID, iEmployeeID);
        return oJSON;
    }
}
