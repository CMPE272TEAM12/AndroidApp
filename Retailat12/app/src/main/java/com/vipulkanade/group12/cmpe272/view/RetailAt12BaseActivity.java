package com.vipulkanade.group12.cmpe272.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.vipulkanade.group12.cmpe272.model.RequestType;
import com.vipulkanade.group12.cmpe272.retailat12.R;

public class RetailAt12BaseActivity extends ActionBarActivity {
    static final String TAG = "RetailAt12BaseActivity";

    private RetailAt12BaseActivity mActivity;


    protected RequestType mRequestType;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mSliderList;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private LinearLayout mContentLayout;

    private boolean bErrorDialogShowing = false;

    private RequestQueue mRequestQueue;

    public RetailAt12BaseActivity() {
        mActivity = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.base_activity_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {


        super.setContentView(R.layout.base_activity_layout);

        // Add inflated UI to container
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout);

        // Inflate actual UI provided by child activity
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View oView = inflater.inflate(layoutResID, null);

		/*
		 *  Even if you give match parent height in xml, still view becomes height becomes.
		 *  So adding this line
		 */
        oView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));


        mContentLayout.addView(oView);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            mTitle = mDrawerTitle = getTitle();
            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mSliderList = (ListView) findViewById(R.id.slider_list);

            // set a custom shadow that overlays the main content when the drawer opens
            // mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

            // set up the drawer's list view with items and click listener
            mSliderList.setAdapter(SliderMenuListAdapter.getInstance(this));
            mSliderList.setOnItemClickListener(new DrawerItemClickListener());

            // enable ActionBar app icon to behave as action to toggle nav drawer
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            // ActionBarDrawerToggle ties together the the proper interactions
            // between the sliding drawer and the action bar app icon
            mDrawerToggle = new ActionBarDrawerToggle(
                    this,                   // host Activity
                    mDrawerLayout,
                    R.string.drawer_open,   // "open drawer" description for accessibility
                    R.string.drawer_close   // "close drawer" description for accessibility // DrawerLayout object
            ) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(mTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(mDrawerTitle);
                    invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            mDrawerToggle.syncState();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            // The action bar home/up action should open or close the drawer.
            // ActionBarDrawerToggle will take care of this.
            if (mDrawerToggle.onOptionsItemSelected(item)) {
                return true;
            }

        } else {

            int iPosition = 0;
            switch(item.getItemId()) {
                case R.id.buy_item:
                    iPosition = 0;
                    break;
                case R.id.add_item_to_inventory:
                    iPosition = 1;
                    break;
                case R.id.employee_details:
                    iPosition = 2;
                    break;
            }
            handleMenuClick(iPosition);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Inform Pause event to AsyncTask

    }

    public RetailAt12BaseActivity getActivity() {
        return mActivity;
    }

    /**
     *  The click listener for ListView in the slider menu
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(mSliderList != null && mDrawerLayout != null) {
                mSliderList.setItemChecked(position, true);
                setTitle(getString(SliderMenuListAdapter.iSliderMenuItems[position]));
                mDrawerLayout.closeDrawer(mSliderList);
                handleMenuClick(position);

            }
        }
    }


    /**
     * Handles slider click. Open appropriate activity as per clicked slider list item position
     * @param position
     */
    private void handleMenuClick(int position) {
        switch(position) {
            case 0:		// buy items
                if (!(mActivity instanceof RetailAt12MainActivity)) {
                    Intent oRetailAt12MainActivity = new Intent(mActivity, RetailAt12MainActivity.class);
                    startActivity(oRetailAt12MainActivity);
                    finish();
                }
                break;

            case 1:		// Add items to Inventory
                if (!(mActivity instanceof RetailAt12AddToInventoryActivity)) {
                    Intent oRetailAt12AddToInventoryActivity = new Intent(mActivity, RetailAt12AddToInventoryActivity.class);
                    startActivity(oRetailAt12AddToInventoryActivity);
                    finish();
                }
                break;

            case 2:		// Employee in store
                if (!(mActivity instanceof EmployeeInStoreActivity)) {
                    Intent oEmployeeInStoreActivity = new Intent(mActivity, EmployeeInStoreActivity.class);
                    startActivity(oEmployeeInStoreActivity);
                    finish();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Displays Error Dialog to handle various exception As Network Unavailable, Cannot Connect to Internet, Connection Timeout etc
     * @param msg Error Message
     */
    public void showErrorDialog(final String msg) {
        bErrorDialogShowing = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);

        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton(mActivity.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                bErrorDialogShowing = false;

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(RetailAt12Application.getInstance());
        }

        return mRequestQueue;
    }

    public void addToRequestQueue(Request req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void addToRequestQueue(Request req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
