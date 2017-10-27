package com.example.lixiao.deomdemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lixiao.deomdemo.R;
import com.example.lixiao.deomdemo.bean.DataBean;

import java.util.List;

/**
 * Created by lixiao on 2017/10/26.
 */

public class ListAdapter extends BaseAdapter{
        private Context context;
        private List<DataBean> list;

        public ListAdapter(List<DataBean> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @Override
        public int getCount() {
            return list==null?0:list.size();
        }

        @Override
        public DataBean getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context,R.layout.item,null);
         TextView indexView = (TextView) convertView.findViewById(R.id.find_nameitem_index);
         TextView nameView = (TextView) convertView.findViewById(R.id.find_nameitem_name);
         indexView.setText(String.valueOf(list.get(position).getIndexName()));
         nameView.setText(list.get(position).getName());
         if (position != 0 && list.get(position - 1).getIndexName() == (list.get(position).getIndexName())) {
             indexView.setVisibility(View.GONE);
         }
         return convertView;
        }
}
