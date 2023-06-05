package app.models.objet;

import java.util.Date;
import java.util.Vector;

import etu1834.framework.decorator.*;
import etu1834.framework.utils.FileUpload;
import etu1834.framework.view.ModelView;

public class Emp {
    int id;
    String nom;
    String adresse;
    Date naissance;
    int enfant;
    FileUpload photo;
    
    public FileUpload getPhoto() {
        return photo;
    }

    public void setPhoto(FileUpload photo) {
        this.photo = photo;
    }

    public Emp() {
    }

    public Emp(int id, String nom) {
        setId(id);
        setNom(nom);
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

    // public void setEnfant(String enfants) {
    //     this.setEnfant(Integer.valueOf(enfants));
    // }

}
