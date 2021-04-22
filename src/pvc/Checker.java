package pvc;

import java.io.File;

public class Checker {

    public static boolean isRepositoryExist() {
        return new File(System.getProperty("user.dir") + RepositoryInitializer.pvcMainFolderName).exists();
    }

    public static boolean isFolderExist(String path) {
        return new File(path).exists();
    }

}
