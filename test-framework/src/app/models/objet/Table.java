package app.models.objet;

import java.util.HashMap;

import etu1834.framework.decorator.Param;
import etu1834.framework.decorator.Session;
import etu1834.framework.decorator.Url;
import etu1834.framework.view.ModelView;

public class Table {
    int count; 
    HashMap<String, Object> session; 

    public Table() {
        this.session = new HashMap<String, Object>();
    }

    @Url(url = "table-show.fwk")
    public ModelView show() {
        ModelView view = new ModelView();
        view.setView("table");
        return view;
    }

    @Url(url = "table-count.fwk")
    public ModelView count() {
        ModelView view = new ModelView();
        view.setView("count");
        view.addItem("count", this.count);
        return view;
    }

    @Url(url = "table-change.fwk")
    public ModelView updateCount(@Param(name = "c") int c) {
        ModelView view = new ModelView();
        this.count = c;
        view.setView("count");
        view.addItem("count", this.count);
        return view;
    }

    @Session
    @Url(url = "table-session.fwk")
    public ModelView ajouter(@Param(name = "name") String name) {
        ModelView view = new ModelView();

        this.session.put("article", name);

        view.setView("session");
        view.addItem("name", "article");
        view.addItem("value", name);
        return view;
    }

    @Session
    @Url(url = "table-liste.fwk")
    public ModelView liste() {
        ModelView view = new ModelView();

        view.setView("liste-table");
        view.addItem("article", this.session.get("article"));
        return view;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
}
