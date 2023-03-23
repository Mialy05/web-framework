package app.models.test;
import etu1834.framework.decorator.*;

public class Emp {
    int id;
    String nom;

    public Emp() {
    }

    @Url(url = "emp-hello")
    public void sayHello() {
        System.out.println("Hello");
    }

}
