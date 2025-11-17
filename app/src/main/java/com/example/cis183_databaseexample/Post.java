package com.example.cis183_databaseexample;

public class Post {

    private int userId;
    private String category;
    private String postData;

    public Post(int u, String c, String pd) {
        userId = u;
        category = c;
        postData = pd;
    }
    public Post() {}

    public int GetUserID() { return userId; }
    public String GetCategory() { return category; }
    public String GetPostData() { return postData; }

    public void SetUserID(int u) { userId = u; }
    public void SetCategory(String c) { category = c; }
    public void SetPostData(String pd) { postData = pd; }

}
