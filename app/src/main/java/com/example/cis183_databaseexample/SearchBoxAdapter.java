package com.example.cis183_databaseexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBoxAdapter extends BaseAdapter {

    Context context;
    ArrayList<Integer> userIds;

    public SearchBoxAdapter(Context c, ArrayList<Integer> ids) {
        context = c;
        userIds = ids;
    }

    @Override
    public int getCount() {
        return userIds.size();
    }

    @Override
    public Object getItem(int position) {
        return userIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(FindID.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.activity_list_view_find_id,null);
        }

        TextView FirstName = view.findViewById(R.id.tv_listview_fname);
        TextView LastName = view.findViewById(R.id.tv_listview_lname);

        int userId = userIds.get(position);





        FirstName.setText("ID: "+position);
        LastName.setText("ID: "+position);

        return view;
    }
}
