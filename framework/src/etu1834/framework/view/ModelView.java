package etu1834.framework.view;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String, Object> data;
    HashMap<String, Object> session; 
    boolean json; 

    public ModelView() {
        data = new HashMap<String, Object>();
        this.setJson(false);
    }

    public void addItem(String key , Object value) {
        if(this.getData() == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, value);
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }

    public HashMap<String, Object> getSession() {
        return session;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }

    public void addSession(String key, Object value) {
        if(this.getSession() == null) {
            this.session = new HashMap<String, Object>();
        }
        this.session.put(key, value);
    }

    public boolean getJson() {
        return json;
    }

    public void setJson(boolean json) {
        this.json = json;
    }
    
}
