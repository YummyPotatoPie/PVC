package Tests;

import pvc.Exceptions.PVCException;
import pvc.RepositoryInitializer;
import org.junit.Test;

import java.io.File;

public class RepositoryInitializerTest {

    @Test
    public void createMainFolderTest() {

        RepositoryInitializer repInit = new RepositoryInitializer();

        try {
            repInit.handle("src\\Tests\\RepositoryInitializatorTestFolder");
        }
        catch (PVCException pvce) {
            System.out.println(pvce.getMessage());
        }

        File pvcMainFolder = new File("src\\Tests\\RepositoryInitializatorTestFolder\\.pvc");
        pvcMainFolder.delete();

    }

}