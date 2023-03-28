package etu1834.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import etu1834.framework.Mapping;
import etu1834.framework.decorator.Url;
import etu1834.framework.utils.Util;
import etu1834.framework.view.ModelView;
import fkException.UrlException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * FrontServlet
 */
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        // for (Map.Entry<String, Mapping> mapping : mappingUrls.entrySet()) {
        //     out.println(">> url : " + mapping.getKey() + " => classe: " + mapping.getValue().getClassName() + " et  methode: " + mapping.getValue().getMethod());
        // }

        String urlTarget = Util.getUrl(req);
       
        try {
            Mapping target = Util.getTarget(urlTarget, mappingUrls);
            // out.println(target.getMethod());
            Class c = Class.forName(target.getClassName());
            Method action = c.getDeclaredMethod(target.getMethod(), null);
            Object instance = c.getConstructor().newInstance();
            ModelView view = (ModelView)action.invoke(instance);
            out.println(view.getView());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/" + view.getView());
            dispatcher.forward(req, res);

        } catch (UrlException e) {
            out.print(e);
            e.printStackTrace(out);
        } catch (Exception e) {
            e.printStackTrace(out);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
     
        try {
            URL classesURL = getServletContext().getResource("/WEB-INF/classes/");
        
            String path = classesURL.toString();
            path = path.replace("%20", " ");
            File classesDir = new File(path.substring(6));  // alana ilay soratra hoe file:/
            
            File[] packages = classesDir.listFiles();
            this.mappingUrls = new HashMap<String, Mapping>();

            for (File p : packages) {
                Vector<Class> classes = Util.getClasses(p, null);

                Method[] methods = null;
    
                for (Class c : classes) {
                    methods = c.getDeclaredMethods();
        
                    for (Method method : methods) {
                        if(method.isAnnotationPresent(Url.class)) {
                            this.mappingUrls.put(method.getAnnotation(Url.class).url(), new Mapping(c.getName(), method.getName()));
                        }
                    }
                }
            } 
            
        } catch (IOException e) {
            throw new ServletException("Erreur à l'initialisation de FrontServlet. Impossible de charger le ServletContext et avoir l'url de WEB-INF" );
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erreur à l'initialisation de FrontServlet. Classe introuvable lors du chargement des dictionnaires d'urls" );
        }
        
    }

    
}