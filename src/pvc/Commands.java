package pvc;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

class Commands {

    private static Options pvcOptions = new Options();

    public final static String initializationOptName = "init";
    public final static String initializationOptDesc = "Repository initialization";
    private final static Option initialization = Option.builder()
            .longOpt(initializationOptName)
            .desc(initializationOptDesc)
            .hasArg(false)
            .build();

    public final static String contributorConfigOptName = "config";
    public final static String contributorConfigOptDesc = "Changing the configuration of the contributor";
    private final static Option contributorConfig = Option.builder()
            .longOpt(contributorConfigOptName)
            .desc(contributorConfigOptDesc)
            .hasArgs()
            .optionalArg(true)
            .numberOfArgs(2)
            .build();

    public static Options getPVCOptions() {
        pvcOptions.addOption(initialization);
        pvcOptions.addOption(contributorConfig);
        return pvcOptions;
    }

}
