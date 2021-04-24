package pvc;

import java.io.File;

import static pvc.PathsAndTokens.*;

public class Checker {

    public static boolean isRepositoryExist() {
        return new File(System.getProperty("user.dir") + pvcMainFolderName).exists();
    }

    public static boolean isFolderExist(String path) {
        return new File(path).exists();
    }

    public static boolean isAddFileEmpty() {
        return new File(System.getProperty("user.dir") + "\\" + pvcAddFile).length() == 0;
    }

}
