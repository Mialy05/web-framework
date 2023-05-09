package etu1834.framework.utils;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Vector;

import etu1834.framework.Mapping;
import fkException.UrlException;
import jakarta.servlet.http.HttpServletRequest;


public class Util {
    public static Vector<Class<?>> getClasses(File file, Vector<Class<?>> classes) throws ClassNotFoundException {
        if(classes == null)
            classes = new Vector<Class<?>>();
        
        if(file.isDirectory() == false) {
            int debutPrefix = file.getPath().indexOf("classes\\") + "classes\\".length();
            String name = String.valueOf(file.getPath().substring(debutPrefix).replace("\\", "."));
            name = name.subSequence(0, name.length() - 6).toString();
            classes.add(Class.forName(name));
        }
        else {
            File[] contents = file.listFiles();
            for (File content : contents) {
                getClasses(content, classes);
            }
        }

        return classes;
    }

    public static String getUrl(HttpServletRequest req) {
        String url = req.getRequestURI();
        // url = /context/url
        // return url.split("/")[2];
        url = url.substring(1);  
        return (String)url.subSequence(url.indexOf("/") + 1, url.length());
    }

    public static Mapping getTarget(String url, HashMap<String, Mapping> dictionary) throws Exception {
        Mapping target = dictionary.get(url);
        if(target == null) {
            throw new UrlException("URL introuvable");
        }
        return target;
    }

    public static Object castString(String data, Class<?> type) throws ParseException {
        if(data == null || data == "" )
            return null;
        if(type == int.class || type == Integer.class)
            return Integer.parseInt(data);
        if(type == float.class || type == Float.class )
            return Float.parseFloat(data);
        if(type == double.class || type == Double.class)
            return Double.parseDouble(data);
        if(type == boolean.class || type == Boolean.class )
            return Boolean.parseBoolean(data);
        if(type == Date.class ) 
            return Date.valueOf(data);
        if(type == java.util.Date.class ) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(data);
        }
        if(type == Timestamp.class )
            return Timestamp.valueOf(data);
        if(type == String.class )
            return data;
        if(type == Time.class)
            return Time.valueOf(data);
        throw new ParseException(" Cannot parse " + data + " to " + type.getName(), 0);
    }
}
