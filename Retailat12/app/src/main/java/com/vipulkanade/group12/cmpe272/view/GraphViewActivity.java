package com.vipulkanade.group12.cmpe272.view;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.vipulkanade.group12.cmpe272.constants.Constants;
import com.vipulkanade.group12.cmpe272.model.DataModelManager;
import com.vipulkanade.group12.cmpe272.model.GetProductHistory;
import com.vipulkanade.group12.cmpe272.retailat12.R;
import com.vipulkanade.group12.cmpe272.webservices.WebserviceURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraphViewActivity extends RetailAt12BaseActivity {

    private BarChart chart;

    private RetailAt12BaseActivity mInstance;

    private List<GetProductHistory> mListGetProductHistory = new ArrayList<GetProductHistory>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        mInstance = new RetailAt12BaseActivity();

        chart = (BarChart) findViewById(R.id.chart);

        makeRequest();
    }

    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();

        if (mListGetProductHistory.size() > 0 && mListGetProductHistory != null) {

            for (int i = 0; i < mListGetProductHistory.size(); i++) {
                BarEntry v1e = new BarEntry(mListGetProductHistory.get(i).getQuantity(), i);
                valueSet1.add(v1e);

                BarEntry v2e = new BarEntry(mListGetProductHistory.get(i).getSentiment(), i);
                valueSet2.add(v2e);
                Log.d(TAG, v1e + "");
                Log.d(TAG, v2e + "");
            }

            BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Quantity Sold");
            barDataSet1.setColor(Color.rgb(0, 155, 0));
            BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Reviews");
            barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

            dataSets = new ArrayList<>();
            dataSets.add(barDataSet1);
            dataSets.add(barDataSet2);
            return dataSets;
        }
        return null;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();

        if (mListGetProductHistory.size() > 0 && mListGetProductHistory != null) {
            for (int i = 0; i < mListGetProductHistory.size(); i++) {
                xAxis.add(mListGetProductHistory.get(i).getItemName().trim());
            }
        }
        return xAxis;
    }

    private void makeRequest() {

        showProgressBar();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, WebserviceURL.GET_PRODUCT_HISTORY, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // TODO Auto-generated method stub
                hideProgressBar();

                JSONArray oResponse = response.optJSONArray(Constants.RESULTS_DATA);
                DataModelManager.getInstance().getGetProductHistoryList().clear();
                for (int i = 0; i < oResponse.length(); i++) {

                    GetProductHistory oGetProductHistory = new GetProductHistory(oResponse.optJSONObject(i));
                    DataModelManager.getInstance().getGetProductHistoryList().add(oGetProductHistory);
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
        mListGetProductHistory = DataModelManager.getInstance().getGetProductHistoryList();
        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("Last 7 days");
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private void showProgressBar() {
        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        chart.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        chart.setVisibility(View.VISIBLE);
    }
}
