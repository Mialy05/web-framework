package etu1834.framework.utils;

/**
 * FileUpload
 */
public class FileUpload {
    String name;
    String path;
    byte[] file;

    public FileUpload() {
    }

    public FileUpload(String name, String path, byte[] file) {
        this.name = name;
        this.path = path;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}