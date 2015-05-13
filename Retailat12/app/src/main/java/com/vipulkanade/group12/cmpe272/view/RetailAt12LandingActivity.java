package com.vipulkanade.group12.cmpe272.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageButton;

import com.vipulkanade.group12.cmpe272.retailat12.R;

public class RetailAt12LandingActivity extends ActionBarActivity implements View.OnClickListener {

    private ImageButton mShopItemsImageButton;
    private ImageButton mEmployeeLoginImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_at12_landing);

        mShopItemsImageButton = (ImageButton) findViewById(R.id.shop_items);
        mEmployeeLoginImageButton = (ImageButton) findViewById(R.id.login);

        mShopItemsImageButton.setOnClickListener(this);
        mEmployeeLoginImageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_items:
                Intent intent = new Intent(this, RetailAt12MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.login:
                Intent oIntent = new Intent(this, LoginInToRetailAt12Activity.class);
                startActivity(oIntent);
                finish();
                break;

            default:
                break;
        }
    }
}
