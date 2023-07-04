package etu1834.framework.view;

import java.util.HashMap;
import java.util.Vector;

public class ModelView {
    String view;
    HashMap<String, Object> data;
    HashMap<String, Object> session; 
    boolean json; 
    boolean invalidateSession; 
    Vector<String> removeSession; 

    public ModelView() {
        data = new HashMap<String, Object>();
        this.setJson(false);
        this.setSession(new HashMap<String, Object>());
        this.setInvalidateSession(false);
        this.setRemoveSession(new Vector<String>());
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

    public boolean getInvalidateSession() {
        return invalidateSession;
    }

    public void setInvalidateSession(boolean invalidateSession) {
        this.invalidateSession = invalidateSession;
    }

    public Vector<String> getRemoveSession() {
        return removeSession;
    }

    public void setRemoveSession(Vector<String> removeSession) {
        this.removeSession = removeSession;
    }

    public void addRemoveSession(String ... attributes) {
        for (String a : attributes) {
            this.removeSession.add(a);
        }
    }
    
}
