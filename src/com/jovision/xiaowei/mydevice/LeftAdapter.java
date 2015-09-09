
package com.jovision.xiaowei.mydevice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jovision.xiaowei.R;

import java.util.ArrayList;

public class LeftAdapter extends BaseAdapter {
    private String[] leftArray;
    private int[] leftImgIdArray;
    private Context mContext;
    private LayoutInflater inflater;
    
    public LeftAdapter(Context con){
        mContext = con;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setData(String[] strArray,int[] imgIdArray){
        leftArray = strArray;
        leftImgIdArray = imgIdArray;
    }
    
    @Override
    public int getCount() {
        return leftArray.length;
    }

    @Override
    public Object getItem(int positon) {
        return leftArray[positon];
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.left_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.leftImg = (ImageView) convertView
                    .findViewById(R.id.left_img);
            viewHolder.leftTV = (TextView) convertView
                    .findViewById(R.id.left_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.leftImg.setImageDrawable(mContext.getResources().getDrawable(leftImgIdArray[position]));
        viewHolder.leftTV.setText(leftArray[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView leftImg;
        TextView leftTV;
    }
}
