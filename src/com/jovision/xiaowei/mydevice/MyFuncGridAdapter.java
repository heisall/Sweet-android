
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

public class MyFuncGridAdapter extends BaseAdapter {
    private ArrayList<Function> myFuncList;
    private Context mContext;
    private LayoutInflater inflater;
    
    public MyFuncGridAdapter(Context con){
        mContext = con;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    public void setData(ArrayList<Function> list){
        if(null == myFuncList){
            myFuncList = new ArrayList<Function>();
        }
        myFuncList.clear();
        myFuncList.addAll(list);
    }
    
    @Override
    public int getCount() {
        return myFuncList.size();
    }

    @Override
    public Object getItem(int positon) {
        return myFuncList.get(positon);
    }

    @Override
    public long getItemId(int positon) {
        return positon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mydevice_grid_item, null);
            viewHolder = new ViewHolder();
            viewHolder.myFuncImg = (ImageView) convertView
                    .findViewById(R.id.grid_img);
            viewHolder.myFuncTV = (TextView) convertView
                    .findViewById(R.id.grid_textview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        viewHolder.myFuncTV.setText(myFuncList.get(position).getFunctionName());
        return convertView;
    }

    class ViewHolder {
        ImageView myFuncImg;
        TextView myFuncTV;
    }
}
