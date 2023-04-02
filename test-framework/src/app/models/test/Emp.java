package app.models.test;
import etu1834.framework.decorator.*;
import etu1834.framework.view.ModelView;

public class Emp {
    int id;
    String nom;

    public Emp() {
    }

    @Url(url = "emp-hello.fwk")
    public ModelView sayHello() {
        ModelView view = new ModelView();
        view.setView("welcome.jsp");
        return view;
    }

}
