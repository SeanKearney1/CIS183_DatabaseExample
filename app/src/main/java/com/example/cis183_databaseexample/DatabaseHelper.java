package com.example.cis183_databaseexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String database_name = "Blog.db";
    private static final String users_table_name = "Users";
    private static final String posts_table_name = "Posts";
    public DatabaseHelper(Context c)
    {
        //we will use this to create the database
        //it accepts: the context, the name of the database, factory (leave null), and version number
        //If your database becomes corrupt or the information in the database is wrong
        //change the version number
        //super is used to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended (DatabaseHelper)
        super(c, database_name, null, 1);
    }

    //this is called when a new database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //this is where we will create the tables in our database
        //Create table in the database
        //execute the sql statement on the database that was passed to the function called db
        //This can be tricky because we have to write our sql statement as strings
        //CREATE TABLE Users (userId integer primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50));
        db.execSQL("CREATE TABLE " + users_table_name + " (userId integer primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50));");
        db.execSQL("CREATE TABLE " + posts_table_name + " (postId integer primary key autoincrement not null, userId integer, category varchar(50), postData varchar(255), foreign key (userId) references " + users_table_name + " (userId));");
    }

    //this is called when a new database version is created
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //delete the tables in the db if they exist
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + posts_table_name + ";");

        //recreate the tables
        onCreate(db);
    }

    public String getUserTableName()
    {
        return users_table_name;
    }

    public String getPostsTableName()
    {
        return posts_table_name;
    }

    //initialize all tables with dummy data
    public void initAllTables()
    {

        initUsers();
        init_posts();
    }

    //this function will only be used once to add dummy data to my users table
    private void initUsers()
    {
        //this will do its own error checking
        //we only want to add the data if nothing is currently in the table
        //this wil ensure we do not add the data more than once.
        if(countRecordsFromTable(users_table_name) == 0)
        {
            //get a writeable version of the database
            //we need a writeable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //insert dummy data into the user table if there is nothing in the table
            //we do not want to do this more than once so it needs to be surrounded with the if
            //statement above.
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Zackary', 'Moore', 'zmoore@monroecccc.edu');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Shannon', 'Thomas', 'sthomas@umich.edu');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Gabriel', 'Smith', 'BigB@gmail.com');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Harrison', 'Moore', 'hsm@yahoo.com');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Tito', 'Williams', 'Tito_Boy@company.gov');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Willow', 'Branch', 'Willow_Branch@hotmail.com');");

            //close the database
            db.close();
        }
    }

    private void init_posts() {
        if (countRecordsFromTable(posts_table_name) == 0) {
            SQLiteDatabase db = this.getWritableDatabase();

            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (1,'Technology', 'This is my first pos about tech. I am posting about my new computer.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (6,'Math', 'I really like math.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (6,'Computer', 'Help I have a virus and now my computer is broken!');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (5,'Water', 'I hate sand, its small and gets everywhere and I really dont remember the rest of the quote.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (4,'Chair', 'I really like chair.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (3,'Animal', 'I saw a bird today. Here is pic. bird.png');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (2,'Animal', 'You didnt send the picture.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (1,'Game', 'I really like game.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (2,'Trees', 'I was a tree today. Dont have a pic.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (2,'Space', ' ');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (4,'Game', 'SMG Movie coming out in April!');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES (4,'Game', 'TF2 Scream Fortress 2025 is almost over >:( ');");

            db.close();
        }
    }

    public int countRecordsFromTable(String tableName)
    {
        //get an instance of the a readable database
        //we only need readable because we are not adding anything to the database with this action
        SQLiteDatabase db = this.getReadableDatabase();

        //count the number of entries in the table that was passed to the function
        //this is a built-in function
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        //whenever you open the database you need to close it
        db.close();

        return numRows;
    }

    public void getAllUserDataGivenId(int userId) {
        if (userIdExists(userId)) {
            User loggedInUser = new User();
            String selectAll = "SELECT * FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }

            loggedInUser.setId(cursor.getInt(0));
            loggedInUser.setFname(cursor.getString(1));
            loggedInUser.setLname(cursor.getString(2));
            loggedInUser.setEmail(cursor.getString(3));

            SessionData.setLoggedInUser(loggedInUser);
        }
        else {
            SessionData.setLoggedInUser(null);
        }
    }

    public boolean userIdExists(int userid) {

        SQLiteDatabase db = this.getReadableDatabase();

        String checkUserId = "SELECT count(userId) FROM " + users_table_name + " WHERE userId = '" + userid + "';";

        Cursor cursor = db.rawQuery(checkUserId,null);

        cursor.moveToFirst();
        int count = cursor.getInt(0);

        db.close();

        if (count != 0) {
            return true;
        }

        return false;
    }

    public int getNumPosts() {
        int numPosts = 0;

        String selectStatement = "SELECT COUNT(userId) FROM " + posts_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);
        if (cursor != null) {
            cursor.moveToFirst();
            numPosts = cursor.getInt(0);
        }
        db.close();
        return numPosts;
    }

    public ArrayList<String> getAllPosts() {
        String selectStatement = "SELECT postData FROM " +posts_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        ArrayList<String> posts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
              String post = cursor.getString(0);
              posts.add(post);
            }
            while(cursor.moveToNext());
        }


        db.close();
        return posts;

    }

    public ArrayList<Post> getAllBuiltPosts() {
        String selectStatement = "SELECT * FROM " +posts_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        ArrayList<Post> posts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Post post = new Post();
                post.SetUserID(cursor.getInt(1));
                post.SetCategory(cursor.getString(2));
                post.SetPostData(cursor.getString(3));

                posts.add(post);
            }
            while(cursor.moveToNext());
        }


        db.close();
        return posts;

    }

    public void addUserToDB(User u) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertUser = "INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('" + u.getFname() + "', '" + u.getLname() + "', '" + u.getEmail() + "');";
        db.execSQL(insertUser);
        db.close();
    }


    public void addPostToDB(Post p) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insertPost = "INSERT INTO " + posts_table_name + " (userid, category, postData) VALUES ('" + p.GetUserID() + "', '" + p.GetCategory() + "', '" + p.GetPostData() + "');'" ;
        db.execSQL(insertPost);
        db.close();
    }



    public ArrayList<Integer> findUserGivenCriteria(String f, String l) {
        ArrayList<Integer> listUsers = new ArrayList<>();

        String selectStatement = "SELECT userId FROM "+ users_table_name + " WHERE ";

        if (f.isEmpty()) {
            selectStatement += "fname is not null and ";
        }
        else {
            selectStatement += "fname = '" + f + "' and ";
        }
        if (l.isEmpty()) {
            selectStatement += "lname is not null;";
        }
        else {
            selectStatement += "lname = '" + l + "';";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectStatement,null);

        if (cursor.moveToFirst()) {
            do {
                int user_id = cursor.getInt(0);
                listUsers.add(user_id);
            }
            while(cursor.moveToNext());
        }

        return listUsers;
    }

    public ArrayList<String> GetAllPostsGivenCriteria(String fname, String lname, String cat) {

        ArrayList<String> listPosts = new ArrayList<String>();

        String fnameCol = users_table_name + ".fname";
        String lnameCol = users_table_name + ".lname";
        String categoryCol = posts_table_name + ".category";
        String postDataCol = posts_table_name + ".postData";
        String userIdU = users_table_name + ".userId";
        String userIdP = posts_table_name + ".userId";
        String selectStatement = "SELECT " + fnameCol + "," + lnameCol + "," + categoryCol + "," + postDataCol + " FROM " +posts_table_name + " INNER JOIN " + users_table_name + " ON " + userIdU + " = " + userIdP + " WHERE ";



        if (fname.isEmpty()) {
            selectStatement += "fname is not null and ";
        }
        else {
            selectStatement += "fname = '" + fname + "' and ";
        }


        if (lname.isEmpty()) {
            selectStatement += "lname is not null and ";
        }
        else {
            selectStatement += "lname = '" + lname + "' and ";
        }


        if (cat.isEmpty()) {
            selectStatement += "category is not null;";
        }
        else {
            selectStatement += "category = '" + cat + "';";
        }

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectStatement,null);
        if (cursor.moveToFirst()) {
            do {

                String firstname = cursor.getString(0);
                String lastname = cursor.getString(1);
                String category = cursor.getString(2);
                String post = cursor.getString(3);

                String info = firstname + " " + lastname + " " + category + " " + post;

                listPosts.add(info);
            }
            while (cursor.moveToNext());
        }

        for (int i = 0; i < listPosts.size();i++) {
            Log.d("Userrrr",listPosts.get(i));
        }

        db.close();

        return listPosts;
    }


    public User getUserGivenId(int userId) {
        if (userIdExists(userId)) {
            Log.d("USER EXISTS","----"+userId);
            User loggedInUser = new User();
            String selectAll = "SELECT * FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectAll, null);

            if (cursor != null) {
                cursor.moveToFirst();
            }

            loggedInUser.setId(cursor.getInt(0));
            loggedInUser.setFname(cursor.getString(1));
            loggedInUser.setLname(cursor.getString(2));
            loggedInUser.setEmail(cursor.getString(3));

            return loggedInUser;
        }
        return null;
    }


    public void updateUser(User u) {
        int id = u.getId();
        String f = u.getFname();
        String l = u.getLname();
        String e = u.getEmail();

        String updateCommand = "UPDATE " + users_table_name + " SET fname = '" + f + "', lname = '" + l + "', email = '" + e + "' WHERE userId = '" + id + "';";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(updateCommand);
        db.close();
    }

}
