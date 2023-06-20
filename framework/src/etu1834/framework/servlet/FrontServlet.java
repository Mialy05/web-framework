package etu1834.framework.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import etu1834.framework.Mapping;
import etu1834.framework.decorator.Auth;
import etu1834.framework.decorator.Param;
import etu1834.framework.decorator.Scope;
import etu1834.framework.decorator.Session;
import etu1834.framework.decorator.Url;
import etu1834.framework.utils.FileUpload;
import etu1834.framework.utils.Util;
import etu1834.framework.view.ModelView;
import fkException.PermissionException;
import fkException.UrlException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    HashMap<String, Object> singletons;
    HashMap<String, Integer> nbrSingleton;
    boolean permission = false;


    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        permission = false;
        HttpSession session = req.getSession();

        for (Map.Entry<String, Object> d : singletons.entrySet()) {
            out.println(d.getKey() + " " + d.getValue());
        }

        String urlTarget = Util.getUrl(req);
        try {
            Mapping target = Util.getTarget(urlTarget, mappingUrls);
            Class c = Class.forName(target.getClassName());
            Method action = c.getDeclaredMethod(target.getMethod(), target.getParameters());
            Auth authState = action.getAnnotation(Auth.class) ;

        // Gestion session et permission
            if(authState == null) {
                permission = true;
            }
            else {
                String profil = authState.profil();   
            
                if(session.getAttribute(getInitParameter("sessionName")) != null) {
                    if(profil.equals("")) {
                        permission = true;
                    }
                    else if(session.getAttribute(getInitParameter("sessionName")).equals(profil)){
                        // if(session.getAttribute(getInitParameter("profil")).equals(profil))
                        permission = true;
                    }
                }

            }
        
            if(!permission) {
                throw new PermissionException(target.getMethod());
            }    

            Object instance = null;

        // traitement singleton
            if(this.singletons.containsKey(c.getName())) {
                if(this.singletons.get(c.getName()) == null) {
                    instance = c.getConstructor().newInstance();
                    this.singletons.put(c.getName(), instance);
                }
                else {
                    instance = this.singletons.get(c.getName());
                    // Util.restoreInstance(instance);
                }
            }
            else {
                instance = c.getConstructor().newInstance();
            }
            
        //set les attributs de l'instance
            Field[] fields = c.getDeclaredFields();
            String setterName, fieldName, parameterValue, initial;
            Method setter;
            Class typeField;
            Object parameter = null;
            for (Field field : fields) {
                field.setAccessible(true);
                fieldName = field.getName();
                // out.println(fieldName + " => " + field.get(instance));
                initial = String.valueOf(fieldName.charAt(0));
                parameterValue = req.getParameter(fieldName); 
                typeField = field.getType();
                setterName = "set" + field.getName().replaceFirst(initial, initial.toUpperCase());
                setter = c.getDeclaredMethod(setterName, typeField);

                // out.print(req.getContentType().startsWith("multipart/"));
                String reqContent = req.getContentType();
                if( reqContent != null && reqContent.startsWith("multipart/") && typeField.equals(FileUpload.class)) {
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
                    parameter = Util.castString(parameterValue, typeField);
                }
                if(parameter != null) {
                    setter.invoke(instance, parameter);
                }
            }

        // envoyer les sessions vers la classe si elle est annotée @session
            Field sessionField = instance.getClass().getDeclaredField("session");
            if(action.isAnnotationPresent(Session.class) ) {
                if(sessionField == null) {
                    throw new Exception( target.getMethod() + "require session. Your class must have field HashMap<String, Object>session .");
                }
                sessionField.setAccessible(true);
                HashMap<String, Object> classSession = ((HashMap<String, Object>)sessionField.get(instance));
                if(classSession == null) {
                    classSession = new HashMap<String, Object>();
                }

                Enumeration<String> sessionAttributes = session.getAttributeNames();
                String key;
                while (sessionAttributes.hasMoreElements()) {
                    key = (String)sessionAttributes.nextElement();
                    out.println(key + ": " + session.getAttribute(key) + "<br>");
                    classSession.put(key, session.getAttribute(key));
                }

            }

            Object actionReturn;
            Parameter[] actionParams = action.getParameters();
            String parameterName;
        //fonction avec arguments
        // out.println("param " + req.getParameter("name"));
            if(actionParams.length > 0) {
                Object[] actionParamValue = new Object[actionParams.length];
                for (int i = 0; i < actionParams.length; i++) {
                    if(actionParams[i].isAnnotationPresent(Param.class)) {
                        parameterName = actionParams[i].getAnnotation(Param.class).name();
                        out.println("paramName " + parameterName);
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
                            out.println("value " + actionParamValue[i]);
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

        // récupérer les sessions modifié dans la méthode annoté @Session pour mettre à jout httpSession  
            if(action.isAnnotationPresent(Session.class)) {
                HashMap<String, Object> classSession = ((HashMap<String, Object>)sessionField.get(instance));
                
                for (Map.Entry<String, Object> s : classSession.entrySet()) {
                    out.print(s.getKey() + " " + s.getValue());
                    session.setAttribute(s.getKey() , s.getValue());
                }
            }

            if(action.getReturnType().equals(ModelView.class)) {
                ModelView view = (ModelView)actionReturn;
    
                HashMap<String, Object> data = view.getData();
                for (Map.Entry<String, Object> d : data.entrySet()) {
                    req.setAttribute(d.getKey(), d.getValue());
                }

                HashMap<String, Object> newSession = view.getSession();
                if(newSession != null) {
                    for (Map.Entry<String, Object> s : newSession.entrySet()) {
                        out.print(s.getKey() + " " + s.getValue());
                        session.setAttribute(s.getKey() , s.getValue());
                    }
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
            this.singletons = new HashMap<String, Object>();

            for (File p : packages) {
                Vector<Class<?>> classes = Util.getClasses(p, null);

                Method[] methods = null;
    
                for (Class c : classes) {
                // vérifier si la classe est un singleton et l'ajouter à l'hashmap
                    Scope scopeAnnotation = ((Scope)c.getAnnotation(Scope.class));    
                    if(scopeAnnotation != null) {
                        if(scopeAnnotation.value().compareToIgnoreCase("singleton") == 0) {
                            this.singletons.put(c.getName(), null);
                        }
                    }
                // ajouter class, methode et url de mapping    
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