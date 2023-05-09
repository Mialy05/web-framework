import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import app.models.objet.Emp;

public class App {

    public static void main(String[] args) throws Exception {
        Method m = Emp.class.getDeclaredMethod("details", int.class);
        Parameter[] params = m.getParameters();
        
        for (Parameter p: params) {
            System.out.println(p.getName());
        }
    }

}
