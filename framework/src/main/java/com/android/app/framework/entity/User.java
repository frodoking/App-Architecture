package com.android.app.framework.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * example
 * Created by frodoking on 2014/12/19.
 */
public class User extends Entity<User> {
    private int id;
    private String username;
    private String password;
    private String headUrl;
    private int phoneNumber;
    private int sex;
    private int age;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getSex() {
        return sex;
    }

    public int getAge() {
        return age;
    }

    @Override
    public Entity<User> fromJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            id = jsonObject.optInt("id");
            username = jsonObject.optString("username");
            password = jsonObject.optString("password");
            headUrl = jsonObject.optString("headUrl");
            phoneNumber = jsonObject.optInt("phoneNumber");
            sex = jsonObject.optInt("sex");
            age = jsonObject.optInt("age");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return super.fromJson(jsonString);
    }
}
