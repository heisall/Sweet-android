
package com.jovision.xiaowei.aboutus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jovision.xiaowei.R;

import java.util.ArrayList;

public class AboutUsAdapter extends BaseAdapter {
    private String[] aboutUsArray;
    private int[] aboutUsIdArray;
    private Context mContext;
    private LayoutInflater inflater;

    public AboutUsAdapter(Context con) {
        mContext = con;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(String[] strArray, int[] imgIdArray) {
        aboutUsArray = strArray;
        aboutUsIdArray = imgIdArray;
    }

    @Override
    public int getCount() {
        return aboutUsArray.length;
    }

    @Override
    public Object getItem(int positon) {
        return aboutUsArray[positon];
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.aboutus_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.ahoutUsImg = (ImageView) convertView
                    .findViewById(R.id.aboutus_img);
            viewHolder.ahoutUsTV = (TextView) convertView
                    .findViewById(R.id.aboutus_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.ahoutUsImg.setImageDrawable(mContext.getResources().getDrawable(
//                aboutUsIdArray[position]));
        viewHolder.ahoutUsTV.setText(aboutUsArray[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView ahoutUsImg;
        TextView ahoutUsTV;
    }
}
