package etu1834.framework;

public class Mapping {
    String className;
    String method;
    Class<?>[] parameters;

    public Mapping() {
    }

    public Mapping(String className, String method, Class<?>[] parameters) {
        setClassName(className);
        setMethod(method);
        setParameters(parameters);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParameters(Class<?>[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParameters() {
        return this.parameters;
    }

}
