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

    DatabaseHelper db;

    public SearchBoxAdapter(Context c, ArrayList<Integer> ids) {
        context = c;
        userIds = ids;
        db = new DatabaseHelper(context);
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

        //db.GetAllPostsGivenCriteria("","","");
        User cur_user = db.getUserGivenId(userId);

        FirstName.setText("Firstname: "+cur_user.getFname());
        LastName.setText("Lastname: "+cur_user.getLname());

        return view;
    }
}
