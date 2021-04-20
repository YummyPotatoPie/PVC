package PVCSource;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

class Commands {

    private static Options pvcOptions = new Options();

    public final static String initializationOptName = "init";
    public final static String initializationOptDesc = "Repository initialization";
    private static Option initialization = Option.builder()
            .longOpt(initializationOptName)
            .hasArg(false)
            .desc(initializationOptDesc)
            .build();

    public static Options getPVCOptions() {
        pvcOptions.addOption(initialization);
        return pvcOptions;
    }

}
