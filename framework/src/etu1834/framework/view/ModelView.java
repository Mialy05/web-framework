package etu1834.framework.view;

import java.util.HashMap;

public class ModelView {
    String view;
    HashMap<String, Object> data;

    public ModelView() {
        data = new HashMap<String, Object>();
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

    
    
}
