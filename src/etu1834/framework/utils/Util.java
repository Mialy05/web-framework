package etu1834.framework.utils;

import java.io.File;
import java.util.Vector;

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
}
