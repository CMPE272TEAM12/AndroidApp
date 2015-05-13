package com.vipulkanade.group12.cmpe272.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.vipulkanade.group12.cmpe272.retailat12.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RetailAt12MainActivity extends RetailAt12BaseActivity implements View.OnClickListener {

    // json object response url
    private String urlJsonObj = "http://abe393ba.ngrok.io/getproduct";

    private ArrayList<Object> addedItemsList;

    private ListAdapter addedItemArrayAdapter;

    private RetailAt12BaseActivity mInstance;

    // Progress dialog
    private ProgressDialog pDialog;
    private ListView addedItemListView;
    private FloatingActionButton scanItemToAddButton;
    private FloatingActionButton checkoutButton;

    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInstance = new RetailAt12BaseActivity();

        pDialog = new ProgressDialog(this);

        addedItemListView = (ListView) findViewById(R.id.addedItemListView);

        scanItemToAddButton = (FloatingActionButton) findViewById(R.id.scan_to_add_button);
        checkoutButton = (FloatingActionButton) findViewById(R.id.checkout_button);

        setupView();

    }

    private void setupView() {

        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        scanItemToAddButton.setOnClickListener(this);
        checkoutButton.setOnClickListener(this);

        addedItemsList = new ArrayList<Object>();
        addedItemsList.clear();

        addedItemArrayAdapter = new ListAdapter(this, addedItemsList);
        addedItemListView.setAdapter(addedItemArrayAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();
            if (re != null) {
                addedItemsList.add(0, re);

                addedItemArrayAdapter.notifyDataSetChanged();
            }

        }
    }

    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
                urlJsonObj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("resultsData");


                    jsonResponse = "";
                    jsonResponse += "Name: " + name + "\n\n";

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        mInstance.addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_to_add_button:
                IntentIntegrator integrator = new IntentIntegrator(RetailAt12MainActivity.this);
                integrator.initiateScan();
                break;

            case R.id.checkout_button:
                Toast.makeText(this, "CheckOut Clicked", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
