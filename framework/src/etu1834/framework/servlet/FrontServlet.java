package etu1834.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import etu1834.framework.Mapping;
import etu1834.framework.decorator.Param;
import etu1834.framework.decorator.Url;
import etu1834.framework.utils.FileUpload;
import etu1834.framework.utils.Util;
import etu1834.framework.view.ModelView;
import fkException.UrlException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * FrontServlet
 */
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class FrontServlet extends HttpServlet {
    HashMap<String, Mapping> mappingUrls;

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();

        String urlTarget = Util.getUrl(req);
        try {
            Mapping target = Util.getTarget(urlTarget, mappingUrls);
            Class c = Class.forName(target.getClassName());
            Method action = c.getDeclaredMethod(target.getMethod(), target.getParameters());
            Object instance = c.getConstructor().newInstance();
            
            Field[] fields = c.getDeclaredFields();
            String setterName, fieldName, parameterValue, initial;
            Method setter;
            Class typeField;
            Object parameter = null;

        //set les attributs de l'instance
            for (Field field : fields) {
                fieldName = field.getName();
                initial = String.valueOf(fieldName.charAt(0));
                parameterValue = req.getParameter(fieldName); 
                typeField = field.getType();
                setterName = "set" + field.getName().replaceFirst(initial, initial.toUpperCase());
                setter = c.getDeclaredMethod(setterName, typeField);

                if(typeField.equals(FileUpload.class)) {
                    Part toUpload = req.getPart(fieldName);
                    if(toUpload != null) {
                        FileUpload tmp = new FileUpload();
                        tmp.setName(toUpload.getSubmittedFileName());
                        tmp.setFile(toUpload.getInputStream().readAllBytes());
                        parameter = tmp;
                    } 
                }
                else {
                    parameterValue = req.getParameter(fieldName); 
                    out.print(fieldName + " " + parameterValue + " zay ");
                    parameter = Util.castString(parameterValue, typeField);
                }
                if(parameter != null) {
                    setter.invoke(instance, parameter);
                }
            }

            Object actionReturn;
            Parameter[] actionParams = action.getParameters();
            String parameterName;
        //fonction avec arguments
            if(actionParams.length > 0) {
                Object[] actionParamValue = new Object[actionParams.length];
                for (int i = 0; i < actionParams.length; i++) {
                    if(actionParams[i].isAnnotationPresent(Param.class)) {
                        parameterName = actionParams[i].getAnnotation(Param.class).name();

                        if(actionParams[i].getType().equals(FileUpload.class)) {
                            Part toUpload = req.getPart(parameterName);
                            if(toUpload != null) {
                                FileUpload tmp = new FileUpload();
                                tmp.setName(toUpload.getSubmittedFileName());
                                tmp.setFile(toUpload.getInputStream().readAllBytes());
                                actionParamValue[i] = tmp;
                            }
                        }
                        else {
                            actionParamValue[i] = Util.castString(req.getParameter(parameterName), actionParams[i].getType());
                        }
                    }
                    else {
                        actionParamValue[i] = null;
                    }
                }
                actionReturn =  action.invoke(instance, actionParamValue);
            }
            else {
                actionReturn =  action.invoke(instance);
            }
            if(action.getReturnType().equals(ModelView.class)) {
                ModelView view = (ModelView)actionReturn;
    
                HashMap<String, Object> data = view.getData();
                for (Map.Entry<String, Object> d : data.entrySet()) {
                    req.setAttribute(d.getKey(), d.getValue());
                }
    
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/" + view.getView() + ".jsp");
                dispatcher.forward(req, res);
            }
            
        } catch (UrlException e) {
            out.print(e);
            e.printStackTrace(out);
        } catch (ParseException e) {
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
                Vector<Class<?>> classes = Util.getClasses(p, null);

                Method[] methods = null;
    
                for (Class c : classes) {
                    methods = c.getDeclaredMethods();
        
                    for (Method method : methods) {
                        if(method.isAnnotationPresent(Url.class)) {
                            this.mappingUrls.put(method.getAnnotation(Url.class).url(), new Mapping(c.getName(), method.getName(), method.getParameterTypes() ));
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