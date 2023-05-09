import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import java.lang.reflect.Executable;

import etu1834.framework.Mapping;
import etu1834.framework.decorator.Url;
import etu1834.framework.utils.Util;

public class App {
    static HashMap<String, Mapping> mappingUrls;

    public static void main(String[] args) throws Exception {
        Mapping m = new Mapping();
        Method setter = m.getClass().getDeclaredMethod("setMethod", String.class);
        Parameter[] p = setter.getParameters();
        for (Parameter parameter : p) {
            System.out.println(parameter.isNamePresent());
        }
    }

    

}
