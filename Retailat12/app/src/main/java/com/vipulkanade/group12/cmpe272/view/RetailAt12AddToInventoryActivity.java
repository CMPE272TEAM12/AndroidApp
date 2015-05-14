package com.vipulkanade.group12.cmpe272.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.vipulkanade.group12.cmpe272.constants.Constants;
import com.vipulkanade.group12.cmpe272.retailat12.R;
import com.vipulkanade.group12.cmpe272.webservices.WebserviceURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import de.timroes.android.listview.EnhancedListView;

/**
 * Created by vipulkanade on 5/12/15.
 */
public class RetailAt12AddToInventoryActivity extends RetailAt12BaseActivity implements View.OnClickListener {

    private ArrayList<Object> addedItemsList;

    private ListAdapter addedItemArrayAdapter;

    private RetailAt12BaseActivity mInstance;

    private JSONArray addedItemsJSONArray = new JSONArray();

    // Progress dialog
    private ProgressDialog pDialog;
    private EnhancedListView addedItemListView;
    private FloatingActionButton scanItemToAddButton;
    private FloatingActionButton checkoutButton;

    private TextView mNoDataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInstance = new RetailAt12BaseActivity();

        pDialog = new ProgressDialog(this);

        mNoDataTextView = (TextView) findViewById(R.id.no_data);
        mNoDataTextView.setVisibility(View.VISIBLE);
        addedItemListView = (EnhancedListView) findViewById(R.id.addedItemListView);

        scanItemToAddButton = (FloatingActionButton) findViewById(R.id.scan_to_add_button);
        checkoutButton = (FloatingActionButton) findViewById(R.id.checkout_button);

        checkoutButton.setTitle("Add items to Inventory");

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


        addedItemListView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView enhancedListView, final int position) {

                //final String item = (String) addedItemArrayAdapter.getItem(position);

                addedItemArrayAdapter.remove(position);

                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        //addedItemArrayAdapter.insert(position);
                    }
                };

            }
        });

        addedItemListView.enableSwipeToDismiss();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();
            String lines[] = re.split("\\r?\\n");
            if (lines[0].toLowerCase().contains(Constants.RETAIL_AT_12.toLowerCase())) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Item", lines[1].replace("Item Name : ",""));
                    jsonObject.put("Code", lines[2].replace("Code : ", ""));
                    jsonObject.put("Price", lines[3].replace("Price : $", ""));
                    jsonObject.put("Quantity", Integer.parseInt(lines[4].replace("Quantity : ", "")));
                    addedItemsJSONArray.put(jsonObject);
                    Log.d("JSON", addedItemsJSONArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (re != null) {
                addedItemsList.add(0, re);
                mNoDataTextView.setVisibility(View.GONE);

                addedItemArrayAdapter.notifyDataSetChanged();
            }

        }
    }


    private void postRequest() {
        showpDialog();
        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("itemList", addedItemsJSONArray.toString());

        JsonObjectRequest req = new JsonObjectRequest(WebserviceURL.ADD_PRODUCT_TO_INVENTORY, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hidepDialog();
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Toast.makeText(getApplicationContext(), "Thank you for Shopping with us", Toast.LENGTH_SHORT).show();
                            addedItemsList.clear();
                            addedItemArrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(), "There was error during transaction, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

    // add the request object to the queue to be executed
        mInstance.addToRequestQueue(req);
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
                IntentIntegrator integrator = new IntentIntegrator(RetailAt12AddToInventoryActivity.this);
                integrator.initiateScan();
                break;

            case R.id.checkout_button:
                if (!addedItemsList.isEmpty() && addedItemsList != null && addedItemsList.size() != 0) {
                    postRequest();
                } else {
                    Toast.makeText(this, "Please add items to cart first", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }
}
