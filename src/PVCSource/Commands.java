package PVCSource;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

class Commands {

    private static Options pvcOptions = new Options();

    public final static String initializationOptName = "init";
    private static Option initialization = Option.builder()
            .longOpt(initializationOptName)
            .hasArg(false)
            .desc("Repository initialization")
            .build();

    public static Options getPVCOptions() {
        pvcOptions.addOption(initialization);
        return pvcOptions;
    }

}
