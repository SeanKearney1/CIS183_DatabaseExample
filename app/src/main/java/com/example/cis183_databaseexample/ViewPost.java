package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewPost extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_j_back;
    EditText et_j_category;
    EditText et_j_postData;

    EditText et_j_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_viewPost_back);
        et_j_category = findViewById(R.id.et_v_viewPost_category);
        et_j_postData = findViewById(R.id.emt_v_viewPost_postdata);
        et_j_name = findViewById(R.id.et_v_viewPost_name);

        setOnClickListeners();
        fillPostData();
    }


    private void setOnClickListeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewPost.this, WelcomePage.class));
            }
        });
    }


    private void fillPostData() {
        Post post = SessionData.getCurrentlyViewedPost();
        User user = db.getUserGivenId(post.GetUserID());
        et_j_name.setText(user.getFname()+" "+user.getLname());
        et_j_category.setText(post.GetCategory());
        et_j_postData.setText(post.GetPostData());
    }
}