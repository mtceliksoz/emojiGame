package com.example.emojigame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "emojiGame";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_POINT = "keypoint";
    private static final String KEY_QUESTION_NUMBER= "keyquestionnumber";


    private static SharedPrefManager mInstance;
    private static Context ctx;

    public SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void setValues(String username,int point, int questionNumber) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERNAME, username);
        editor.putInt(KEY_POINT, point);
        editor.putInt(KEY_QUESTION_NUMBER, questionNumber);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public  boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }


    public  String getUsername() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }
    public  int getPoint() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_POINT, 0);
    }
    public  int getQuestionNumber() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_QUESTION_NUMBER, 0);
    }

    //this method will logout the user
    public  void endGame() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Toast.makeText(ctx, "game ended your point is: "+sharedPreferences.getInt(KEY_POINT,0), Toast.LENGTH_SHORT).show();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}
