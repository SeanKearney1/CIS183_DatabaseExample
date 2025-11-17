package com.example.cis183_databaseexample;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity {

    PostsAdapter pAdapter;
    Button btn_j_back;
    Button btn_j_update;
    Button btn_j_makePosts;
    ListView lv_j_posts;
    TextView tv_j_welcome;
    TextView tv_j_numPosts;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_j_back = findViewById(R.id.btn_v_welcome_back);
        btn_j_update = findViewById(R.id.btn_v_updateUserData);
        btn_j_makePosts = findViewById(R.id.btn_v_makePost);
        lv_j_posts = findViewById(R.id.lv_v_posts);
        tv_j_welcome = findViewById(R.id.tv_v_welcome_wMessage);
        tv_j_numPosts = findViewById(R.id.tv_v_welcome_numPosts);

        db = new DatabaseHelper(this);

        setWelcomeMessage();
        setNumberOfPosts();
        setListViewWithPosts();

        setClickListeners();

        fillPosts();

        //db.GetAllPostsGivenCriteria("","","");
    }


    void setClickListeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this,MainActivity.class));
            }
        });
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this,UpdateUser.class));
            }
        });
        btn_j_makePosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomePage.this,MakePost.class));
            }
        });
        lv_j_posts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Post post = (Post)lv_j_posts.getAdapter().getItem(position);
                SessionData.setCurrentlyViewedPost(post);
                startActivity(new Intent(WelcomePage.this,ViewPost.class));
            }
        });
    }


    private void fillPosts() {

        ArrayList<Post> posts = db.getAllBuiltPosts();
        // = db.GetAllPostsGivenCriteria("","","");

        pAdapter = new PostsAdapter(this,posts);
        lv_j_posts.setAdapter(pAdapter);
    }

    private void setWelcomeMessage() {
        String text = "Welcome "+SessionData.getLoggedInUser().getFname()+" "+SessionData.getLoggedInUser().getLname(); // hate the yellow
        tv_j_welcome.setText(text);
    }
    private void setNumberOfPosts() {
        String text = "Number of Posts: "+db.getNumPosts();
        tv_j_numPosts.setText(text);
    }

    private void setListViewWithPosts() {
        ArrayList<String> posts = db.getAllPosts();

        //lv_j_posts.setAdapter(post_adapter);

        for(int i = 0; i < posts.size();i++) {
            Log.d("Posts",posts.get(i));
        }
    }
}