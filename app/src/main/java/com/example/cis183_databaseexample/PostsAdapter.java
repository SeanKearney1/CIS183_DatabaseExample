package com.example.cis183_databaseexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PostsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> Posts;

    DatabaseHelper db;
    public PostsAdapter(Context c, ArrayList<Post> p) {
        context = c;
        Posts = p;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return Posts.size();
    }

    @Override
    public Object getItem(int position) {
        return Posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater mInflator = (LayoutInflater) context.getSystemService(FindID.LAYOUT_INFLATER_SERVICE);
            view = mInflator.inflate(R.layout.post_adapter_cell,null);
        }

        TextView Username = view.findViewById(R.id.tv_postCell_user);
        TextView Category = view.findViewById(R.id.tv_postCell_category);
        TextView PostData = view.findViewById(R.id.tv_postCell_postData);

        Post post = Posts.get(position);


        User user = db.getUserGivenId(post.GetUserID());

        Log.d("User LOGIN",post.GetUserID()+"");

        if (user != null) {
            Username.setText(user.getFname() + " " + user.getLname());
            Category.setText(post.GetCategory());
            PostData.setText(post.GetPostData());
        }
        //FirstName.setText("Firstname: "+cur_user.getFname());
        //LastName.setText("Lastname: "+cur_user.getLname());



        return view;
    }
}
