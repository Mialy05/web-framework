package fkException;

public class PermissionException extends Exception {

    public PermissionException(String methodName) {
        super("Permission denied while calling " + methodName + " This method is annoted with @auth and user must be connected with specific profil ");
    }

}
