package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FindID extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_j_findId_back;
    Button btn_j_findId_search;

    EditText et_j_fname;
    EditText et_j_lname;

    ListView lv_j_search;

    SearchBoxAdapter sbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_id);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        btn_j_findId_back = findViewById(R.id.btn_findId_back);
        btn_j_findId_search = findViewById(R.id.btn_findId_find);

        et_j_fname = findViewById(R.id.et_findId_fname);
        et_j_lname = findViewById(R.id.et_findId_lname);

        lv_j_search = findViewById(R.id.lv_findId_search);

        setButtonOnClickListeners();
    }




    private void setButtonOnClickListeners() {
        btn_j_findId_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FindID.this,MainActivity.class));
            }
        });
        btn_j_findId_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_filter_db();
                Log.d("Hello?","Hello?");
            }
        });
        lv_j_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switchUser(position);

            }
        });
    }

    private void search_filter_db() {
        String fname = et_j_fname.getText().toString();
        String lname = et_j_lname.getText().toString();
        ArrayList<Integer> userIds = db.findUserGivenCriteria(fname,lname);

        sbAdapter = new SearchBoxAdapter(this,userIds);
        lv_j_search.setAdapter(sbAdapter);


        for (int i = 0; i < userIds.size();i++) {
            Log.d("Search("+fname+", "+lname+")",""+userIds.get(i));
        }


        db.close();
    }


    private void switchUser(int position) {
        User user = db.getUserGivenId((int)lv_j_search.getAdapter().getItem(position));
        db.close();
        SessionData.setLoggedInUser(user);
        startActivity(new Intent(FindID.this,WelcomePage.class));
    }




}