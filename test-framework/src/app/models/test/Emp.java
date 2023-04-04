package app.models.test;
import java.util.Vector;

import etu1834.framework.decorator.*;
import etu1834.framework.view.ModelView;

public class Emp {
    int id;
    String nom;

    public Emp() {
    }

    public Emp(int id, String nom) {
        setId(id);
        setNom(nom);
    }

    @Url(url = "emp-hello.fwk")
    public ModelView getListe() {
        ModelView view = new ModelView();
        view.setView("welcome.jsp");
        Vector<Emp> emps = new Vector<Emp>(2);
        emps.add(new Emp(1, "Nante"));
        emps.add(new Emp(2, "Mialisoa"));
        view.addItem("employes", emps);
        return view;
    }



    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }



    public String getNom() {
        return nom;
    }



    public void setNom(String nom) {
        this.nom = nom;
    }

}
