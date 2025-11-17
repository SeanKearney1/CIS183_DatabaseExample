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

public class MakePost extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_j_back;
    Button btn_j_post;

    EditText et_j_category;
    EditText et_j_postData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_make_post);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = new DatabaseHelper(this);

        btn_j_back = findViewById(R.id.btn_v_makePost_back);
        btn_j_post = findViewById(R.id.btn_v_makePost_post);

        et_j_category = findViewById(R.id.et_v_makePost_category);
        et_j_postData = findViewById(R.id.emt_v_makePost_postdata);


        setOnClickListeners();
    }



    private void setOnClickListeners() {
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakePost.this,WelcomePage.class));
            }
        });
        btn_j_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post();
                post.SetUserID(SessionData.getLoggedInUser().getId());
                post.SetCategory(et_j_category.getText().toString());
                post.SetPostData(et_j_postData.getText().toString());
                db.addPostToDB(post);
                startActivity(new Intent(MakePost.this,WelcomePage.class));
            }
        });
    }
}