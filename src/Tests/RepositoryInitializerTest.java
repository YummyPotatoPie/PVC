package Tests;

import pvc.Exceptions.PVCException;
import pvc.RepositoryInitializator;
import org.junit.Test;

public class RepositoryInitializatorTest {

    @Test
    public void createMainFolderTest() {

        RepositoryInitializator repInit = new RepositoryInitializator();

        try {
            repInit.handle("src\\Tests\\RepositoryInitializatorTestFolder");
        }
        catch (PVCException pvce) {
            pvce.getMessage();
        }

    }

}