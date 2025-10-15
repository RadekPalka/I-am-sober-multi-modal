package com.example.global;

public class Global {
    public static final String URL= "https://mentoring-api.vercel.app/api/v1";
    public static final String REGISTER_URL = URL + "/account";
    public static final String LOGIN_URL= REGISTER_URL + "/login";
    public static final String USER_DETAILS = REGISTER_URL + "/me";
    public static final String PAGINATED_ADDICTIONS_URL = URL + "/addiction";
    public static final String LOGOUT_URL = REGISTER_URL + "/logout";
}
