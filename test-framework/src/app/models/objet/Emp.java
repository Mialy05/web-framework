package app.models.objet;

import java.util.Date;
import java.util.Vector;

import etu1834.framework.decorator.*;
import etu1834.framework.utils.FileUpload;
import etu1834.framework.view.ModelView;

@Scope(value = "singleton")
public class Emp {
    int id;
    String nom;
    String adresse;
    Date naissance;
    int enfant;
    FileUpload photo;
    int count;
    
    
    
    public Emp() {
    }
    
    public Emp(int id, String nom) {
        setId(id);
        setNom(nom);
    }

    @Url(url = "emp-count.fwk")
    public ModelView count() {
        ModelView view = new ModelView();
        view.setView("count");
        view.addItem("count", this.count);
        return view;
    }

    @Url(url = "emp-change.fwk")
    public ModelView updateCount(@Param(name = "c") int c) {
        ModelView view = new ModelView();
        this.count = c;
        view.setView("count");
        view.addItem("count", this.count);
        return view;
    }
    
    @Url(url = "emp-hello.fwk")
    public ModelView getListe() {
        ModelView view = new ModelView();
        view.setView("welcome");
        Vector<Emp> emps = new Vector<Emp>(2);
        emps.add(new Emp(1, "Nante"));
        emps.add(new Emp(2, "Mialisoa"));
        view.addItem("employes", emps);
        return view;
    }
    
    @Url(url = "emp-form.fwk")
    public ModelView setForm() {
        ModelView view = new ModelView();
        view.setView("add-emp");
        return view;
    }
    
    @Url(url = "emp-new.fwk")
    public ModelView save() {
        ModelView view = new ModelView();
        view.setView("info");
        view.addItem("emp", this);
        return view;
    }

    @Url(url = "emp-save.fwk")
    public ModelView save(@Param(name = "numero") int numero) {
        ModelView view = new ModelView();
        view.setView("info");
        view.addItem("emp", this);
        view.addItem("numero", numero);
        return view;
    }
    
    @Url(url = "details.fwk")
    public ModelView details(@Param(name = "id") int id, String name) {
        // int id = 1;
        ModelView view = new ModelView();
        view.setView("details");
        view.addItem("numero", id);
        view.addItem("name", "Tsisy");
        return view;
    }
    
    @Url(url = "add-photo.fwk")
    public ModelView newPhoto( @Param(name = "photo") FileUpload photo ) {
        ModelView view = new ModelView();
        view.setView("view-photo");
        view.addItem("photo", photo);
        return view;
    }
    
    public FileUpload getPhoto() {
        return photo;
    }

    public void setPhoto(FileUpload photo) {
        this.photo = photo;
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
    
    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getNaissance() {
        return naissance;
    }

    public void setNaissance(Date naissance) {
        this.naissance = naissance;
    }

    // public void setNaissance(String naissance) throws ParseException {
    //     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    //     this.setNaissance(formatter.parse(naissance));
    // }

    public int getEnfant() {
        return enfant;
    }

    public void setEnfant(int enfants) {
        this.enfant = enfants;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // public void setEnfant(String enfants) {
    //     this.setEnfant(Integer.valueOf(enfants));
    // }

}
