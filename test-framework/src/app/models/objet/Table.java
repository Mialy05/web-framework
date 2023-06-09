package app.models.objet;

import etu1834.framework.decorator.Param;
import etu1834.framework.decorator.Url;
import etu1834.framework.view.ModelView;

public class Table {
    int count; 

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
