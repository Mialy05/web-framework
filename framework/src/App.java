import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import etu1834.framework.Mapping;
import etu1834.framework.decorator.Url;
import etu1834.framework.utils.Util;

public class App {
    static HashMap<String, Mapping> mappingUrls;

    public static void main(String[] args) throws Exception {
        String num = "4";
        Integer i = Integer.parseInt(num);
        int n = 4;
        // System.out.println(Integer.parseInt(num));
        test(i);
    }

    public static void test(int n) {
        System.out.println(double.class == Double.class);

    }

}
