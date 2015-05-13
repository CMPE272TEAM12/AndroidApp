package com.vipulkanade.group12.cmpe272.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vipulkanade.group12.cmpe272.retailat12.R;

import java.util.List;

/**
 * Created by vipulkanade on 5/12/15.
 */
public class ListAdapter extends ArrayAdapter<List<? extends Object>> {

    private Context mContext;
    private int iLayoutResId;
    private List<? extends Object> mListItems;

    private TextView mTextView;

    public ListAdapter(Context context, List<? extends Object> listItems) {
        super(context, R.layout.list_item);
        this.mContext 		= context;
        this.iLayoutResId 	= R.layout.list_item;
        this.mListItems 	= listItems;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(iLayoutResId, null);
            LinearLayout oListItemLayout = (LinearLayout) convertView.findViewById(R.id.list_item_layout);
        }

        mTextView = (TextView) convertView.findViewById(R.id.list_item_text);
        mTextView.setText(mListItems.get(position).toString());

        return convertView;
    }
}
