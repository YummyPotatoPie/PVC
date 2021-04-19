package Tests;

import PVCSource.RepositoryInitializator;
import org.junit.Test;

public class RepositoryInitializatorTest {

    @Test
    public void createMainFolderTest() {

        RepositoryInitializator repInit = new RepositoryInitializator();

        repInit.handle("src\\Tests\\RepositoryInitializatorTestFolder");

    }

}