package com.example.rika.spk;

/**
 * Created by Rika on 11/21/2016.
 */
public class config {
    //url to our login.php
public static final String LOGIN_URL = "http://192.168.1.193/spk/spk/login.php";
    //public static final String LOGIN_URL = "http://192.168.56.1/spk/spk/login.php";
//    public static final String LOGIN_URL = "http://192.168.1.101:8080/spk/spk/login.php";
//    public static final String LOGIN_URL = "http://10.47.217.200:8080/spk/spk/login.php";

    //key for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "username";
    public static final String KEY_PASSWORD ="password";

    //if server response is equal to this that means login successful
    public static final String LOGIN_SUCCESS = "success";

    //key for sharedpreferences

    //this would be the name of our shared preferences
    public static final String SHARED_PREF_NAME ="mylogginapp";

    //this would be used to store the email of current logged in user
    public static final String EMAIL_SHARED_PREF ="username";

    //we will use this to store the boolean in shared preference to track user is logged in or not
    public static final String LOGGEDIN_SHARED_PREF ="loggedin";

    public static final int USER_LOGIN = 0;
}
