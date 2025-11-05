package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;

    EditText et_j_userid;

    Button btn_j_login;
    Button btn_j_register;
    Button btn_j_findid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_j_userid = findViewById(R.id.et_v_userid);
        btn_j_login = findViewById(R.id.btn_v_login);
        btn_j_register = findViewById(R.id.btn_v_register);
        btn_j_findid = findViewById(R.id.btn_v_findid);

        //make new instance of the dbHelper
        dbHelper = new DatabaseHelper(this);

        //initialize all of the tables with dummy data
        //there is logic in this function to ensure this is not done more than once.
        dbHelper.initAllTables();

        //just used for testing
        checkTableRecordCount();
        setButtonClickListeners();

    }

    //for testing purposes
    private void checkTableRecordCount()
    {
        Log.d("Users Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getUserTableName()) + "");
        Log.d("Posts Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getPostsTableName()) + "");
    }

    private void setButtonClickListeners() {
        btn_j_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_j_userid.getText().toString().isEmpty()) { return; }
                int enteredId = Integer.parseInt(et_j_userid.getText().toString());
                dbHelper.getAllUserDataGivenId(enteredId);
                if (SessionData.getLoggedInUser() != null) {
                    User u = SessionData.getLoggedInUser();
                    Log.d("Logged in: ",u.getFname()+ " " +u.getLname());
                    startActivity(new Intent(MainActivity.this,WelcomePage.class));
                }
                else {
                    Log.d("Logged in: ","Invalid User");
                }
            }
        });
        btn_j_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Register.class));
            }
        });
        btn_j_findid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FindID.class));
            }
        });
    }
}