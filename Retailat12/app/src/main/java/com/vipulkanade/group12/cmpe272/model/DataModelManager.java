package com.vipulkanade.group12.cmpe272.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vipulkanade on 5/13/15.
 */
public class DataModelManager {

    private static DataModelManager mInstance;
    private static final Object obj = new Object();

    private List<EmployeeInStore> mEmployeeInStoreList = new ArrayList<EmployeeInStore>();

    public DataModelManager() {
    }

    public static DataModelManager getInstance() {
        synchronized (obj) {
            if(mInstance == null)
                mInstance = new DataModelManager();
        }
        return mInstance;
    }


    public List<EmployeeInStore> getEmployeeInStoreList() {
        return mEmployeeInStoreList;
    }

    public void setEmployeeInStoreList(List<EmployeeInStore> mEmployeeInStoreList) {
        this.mEmployeeInStoreList = mEmployeeInStoreList;
    }
}
