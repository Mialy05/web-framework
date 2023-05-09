package app.models.objet;

import etu1834.framework.decorator.Url;
import etu1834.framework.view.ModelView;

public class Table {
    
    @Url(url = "table-show.fwk")
    public ModelView show() {
        ModelView view = new ModelView();
        view.setView("table");
        return view;
    }
}
