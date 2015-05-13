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
import com.vipulkanade.group12.cmpe272.model.GetProduct;
import com.vipulkanade.group12.cmpe272.retailat12.R;
import com.vipulkanade.group12.cmpe272.webservices.WebserviceURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListProductActivity extends RetailAt12BaseActivity {

    private RetailAt12BaseActivity mInstance;

    private ListView mListView;

    private List<GetProduct> mGetProductList = new ArrayList<GetProduct>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        mInstance = new RetailAt12BaseActivity();

        mListView = (ListView) findViewById(R.id.product_listview);

        makeRequest();

    }

    private void makeRequest() {

        Log.d(TAG, "In here");
        showProgressBar();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, WebserviceURL.GET_PRODUCT, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                hideProgressBar();
                JSONArray oResponse = response.optJSONArray(Constants.RESULTS_DATA);
                DataModelManager.getInstance().getGetProductList().clear();
                for (int i = 0; i < oResponse.length(); i++) {
                    GetProduct oGetProduct = new GetProduct(oResponse.optJSONObject(i));
                    DataModelManager.getInstance().getGetProductList().add(oGetProduct);
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
        mGetProductList = DataModelManager.getInstance().getGetProductList();

        if (mGetProductList.size() > 0 && mGetProductList != null) {
            mListView.setAdapter(new ListAdapter(this, mGetProductList));
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
