package com.vipulkanade.group12.cmpe272.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vipulkanade.group12.cmpe272.retailat12.R;

/**
 * Created by vipulkanade on 5/12/15.
 */
public class SliderMenuListAdapter extends BaseAdapter {
    private Context mContext;
    public static final int iSliderMenuItems[] = {R.string.add_item, R.string.employee_details};
    private static final int iSliderMenuIcons[] = {};
    private static SliderMenuListAdapter mInstance;
    private static final Object obj = new Object();

    private SliderMenuListAdapter(Context context) {
        this.mContext = context;
    }

    public static SliderMenuListAdapter getInstance(Context context) {
        synchronized (obj) {
            if(mInstance == null) {
                mInstance = new SliderMenuListAdapter(context);
            }
        }
        return mInstance;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.slider_menu_list_item, null);
        }
        TextView oSliderMenuText = (TextView) convertView.findViewById(R.id.slider_menu_text);
        oSliderMenuText.setText(mContext.getString(iSliderMenuItems[position]));

        if(position < iSliderMenuItems.length - 2) {
            oSliderMenuText.setTextColor(mContext.getResources().getColor(R.color.green_color));
            ImageView oSliderMenuIcon = (ImageView) convertView.findViewById(R.id.slider_menu_icon);
            oSliderMenuIcon.setBackgroundResource(iSliderMenuIcons[position]);
            oSliderMenuIcon.setVisibility(View.VISIBLE);

        } else {

            oSliderMenuText.setTextColor(mContext.getResources().getColor(android.R.color.white));

        }
        return convertView;
    }


    @Override
    public int getCount() {
        return iSliderMenuItems.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
