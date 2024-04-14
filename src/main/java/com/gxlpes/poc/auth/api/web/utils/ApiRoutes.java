package com.gxlpes.poc.auth.api.web.utils;

public class ApiRoutes {

    public static class SYSTEM {
        public static final String AUTH = "/api/auth";
    }

    public static class AUTH {
        public static final String REGISTER = "/register";
        public static final String AUTHENTICATE = "/authenticate";
        public static final String REFRESH_TOKEN = "/refresh-token";
        public static final String LOGOUT_USER = "/logout";
        public static final String VERIFY = "/verify";
        public static final String CHANGE_PASSWORD = "/verify";
    }

}
