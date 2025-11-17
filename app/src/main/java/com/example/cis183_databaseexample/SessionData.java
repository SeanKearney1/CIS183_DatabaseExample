package com.example.cis183_databaseexample;

public class SessionData {

    private static User loggedInUser;

    private static Post currentlyViewedPost;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(User loggenInUser) {
        SessionData.loggedInUser = loggenInUser;
    }

    public static Post getCurrentlyViewedPost() { return currentlyViewedPost; }

    public static void setCurrentlyViewedPost(Post p) {
        currentlyViewedPost = p;
    }
}
