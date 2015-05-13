package com.vipulkanade.group12.cmpe272.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.vipulkanade.group12.cmpe272.constants.Constants;
import com.vipulkanade.group12.cmpe272.model.DataModelManager;
import com.vipulkanade.group12.cmpe272.model.EmployeeInStore;
import com.vipulkanade.group12.cmpe272.retailat12.R;
import com.vipulkanade.group12.cmpe272.webservices.WebserviceURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class EmployeeInStoreActivity extends RetailAt12BaseActivity {

    private static final String TAG = EmployeeInStoreActivity.class.getSimpleName();

    private RetailAt12BaseActivity mInstance;

    private ListView mListView;

    private List<EmployeeInStore> mEmployeeInStoresList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_in_store);

        mInstance = new RetailAt12BaseActivity();

        setupView();
    }

    private void setupView() {

        mListView = (ListView) findViewById(R.id.employee_in_store_list);

        makeRequest();
    }

    private void makeRequest() {

        Log.d(TAG, "In here");
        showProgressBar();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, WebserviceURL.GET_EMPLOYEE, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                hideProgressBar();
                Log.d(TAG, response.toString());
                JSONArray oResponse = response.optJSONArray(Constants.RESULTS_DATA);
                DataModelManager.getInstance().getEmployeeInStoreList().clear();
                for (int i = 0; i < oResponse.length(); i++) {
                    Log.d(TAG, oResponse.optJSONObject(i) + "");
                    EmployeeInStore oEmployeeInStore = new EmployeeInStore(oResponse.optJSONObject(i));
                    DataModelManager.getInstance().getEmployeeInStoreList().add(oEmployeeInStore);
                }
                setDisplay();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                VolleyLog.e(TAG, error);
                hideProgressBar();
            }
        });
        mInstance.addToRequestQueue(jsObjRequest);
    }

    private void setDisplay() {
        mEmployeeInStoresList = DataModelManager.getInstance().getEmployeeInStoreList();

        if (mEmployeeInStoresList != null && mEmployeeInStoresList.size() > 0) {
            mListView.setAdapter(new ListAdapter(this, mEmployeeInStoresList));
        }
    }

    private void showProgressBar() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }
}
