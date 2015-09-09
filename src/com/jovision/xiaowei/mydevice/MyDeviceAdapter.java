
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

public class MyDeviceAdapter extends BaseAdapter {
    private ArrayList<Device> myDevList;
    private Context mContext;
    private LayoutInflater inflater;
    
    public MyDeviceAdapter(Context con){
        mContext = con;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setData(ArrayList<Device> list){
        if(null == myDevList){
            myDevList = new ArrayList<Device>();
        }
        myDevList.clear();
        myDevList.addAll(list);
    }
    
    @Override
    public int getCount() {
        return myDevList.size();
    }

    @Override
    public Object getItem(int positon) {
        return myDevList.get(positon);
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mydevice_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.myDevImg = (ImageView) convertView
                    .findViewById(R.id.mydev_img);
            viewHolder.myDevTopImg = (ImageView) convertView
                    .findViewById(R.id.mydev_top_img);
            viewHolder.myDevNameTV = (TextView) convertView
                    .findViewById(R.id.devname_textview);
            viewHolder.myDevFunc1IV = (ImageView) convertView
                    .findViewById(R.id.devfunc1_img);
            viewHolder.myDevFunc2IV = (ImageView) convertView
                    .findViewById(R.id.devfunc2_img);
            viewHolder.myDevFunc3IV = (ImageView) convertView
                    .findViewById(R.id.devfunc3_img);
            viewHolder.myDevFunc4IV = (ImageView) convertView
                    .findViewById(R.id.devfunc4_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.myDevNameTV.setText(myDevList.get(position).getFullNo());
        return convertView;
    }

    class ViewHolder {
        ImageView myDevImg;
        ImageView myDevTopImg;
        TextView myDevNameTV;
        ImageView myDevFunc1IV;
        ImageView myDevFunc2IV;
        ImageView myDevFunc3IV;
        ImageView myDevFunc4IV;
    }
}
