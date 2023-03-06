package utils;

import jakarta.servlet.http.HttpServletRequest;

public class Util {
    public static String getUrlInfo(HttpServletRequest req) {
        return req.getPathInfo();
    }
}
