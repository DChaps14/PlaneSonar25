package project.model;

import java.io.File;

/**
 * A Class that implements Loader and has a method to check whether the file is of the correct type
 */
public class Loader {

    /**
     * Deals with the error handling of the files and checks whether the file is of correct type.
     *
     * @param file The file being checked for correct format
     * @return true if the file is of a valid type (.csv or .dat) and false if the file is of invalid type
     */
    public boolean errorHandler(File file) {
        String extension = "";

        int i = file.getAbsolutePath().lastIndexOf('.');
        int p = Math.max(file.getAbsolutePath().lastIndexOf('/'), file.getAbsolutePath().lastIndexOf('\\'));

        if (i > p) {
            extension = file.getAbsolutePath().substring(i+1);
        }
        if (!extension.equals("dat") && !extension.equals("csv")) {
            return false;
        } else {
            return true;
        }
    }
}
