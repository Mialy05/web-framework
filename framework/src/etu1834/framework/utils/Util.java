package etu1834.framework.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import etu1834.framework.Mapping;
import fkException.UrlException;
import jakarta.servlet.http.HttpServletRequest;


public class Util {
    public static Vector<Class> getClasses(File file, Vector<Class> classes) throws ClassNotFoundException {
        if(classes == null)
            classes = new Vector<Class>();
        
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
        return url.split("/")[2];  
    }

    public static Mapping getTarget(String url, HashMap<String, Mapping> dictionary) throws Exception {
        Mapping target = dictionary.get(url);
        if(target == null) {
            throw new UrlException("URL introuvable");
        }
        return target;
    }
}
