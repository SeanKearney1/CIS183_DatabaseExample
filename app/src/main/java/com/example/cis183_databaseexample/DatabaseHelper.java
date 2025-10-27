package com.example.cis183_databaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Sean', 'Kearney', 'skearney@mail.com');");


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


}
