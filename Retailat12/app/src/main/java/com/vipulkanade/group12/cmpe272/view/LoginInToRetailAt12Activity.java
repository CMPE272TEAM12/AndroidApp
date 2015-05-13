package com.vipulkanade.group12.cmpe272.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.vipulkanade.group12.cmpe272.constants.Constants;
import com.vipulkanade.group12.cmpe272.retailat12.R;
import com.vipulkanade.group12.cmpe272.webservices.WebserviceURL;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginInToRetailAt12Activity extends ActionBarActivity implements View.OnClickListener {


    private Button mScanButton;

    private RetailAt12BaseActivity mInstance;

    private JSONObject jsonObject = new JSONObject();

    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_in_to_retail_at12);

        mInstance = new RetailAt12BaseActivity();

        mScanButton = (Button) findViewById(R.id.scan_button);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loginning In...");
        pDialog.setCancelable(false);

        mScanButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan_button:
                IntentIntegrator integrator = new IntentIntegrator(LoginInToRetailAt12Activity.this);
                integrator.initiateScan();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String re = scanResult.getContents();
            if (re != null) {
                String lines[] = re.split("\\r?\\n");

                try {
                    jsonObject.put(Constants.EMPLOYEE_ID, lines[0].replace(Constants.EMPLOYEE_CODE + " : ", ""));
                    postRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void postRequest() {
        showpDialog();

        JsonObjectRequest req = new JsonObjectRequest(WebserviceURL.EMPLOYEE_LOGIN, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hidepDialog();
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Toast.makeText(getApplicationContext(), "Log In successful", Toast.LENGTH_SHORT).show();
                            openAddtoInventory();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidepDialog();
                VolleyLog.e("Error: ", error.getMessage());
                Toast.makeText(getApplicationContext(), "There was error logging in, Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

// add the request object to the queue to be executed
        mInstance.addToRequestQueue(req);
    }

    private void openAddtoInventory() {
        Intent intent = new Intent(this, RetailAt12AddToInventoryActivity.class);
        startActivity(intent);
        finish();
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
