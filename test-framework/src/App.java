import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.google.gson.Gson;

import app.models.objet.Emp;

public class App {

    public static void main(String[] args) throws Exception {
        Gson builderGson = new Gson();
        String json = builderGson.toJson(builderGson.toJson(new Emp(1, "Rakoto")));
        Emp e = builderGson.fromJson(json, Emp.class);
        System.out.println(e.getId());
    }

}
