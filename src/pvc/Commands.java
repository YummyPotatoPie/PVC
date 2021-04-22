package pvc;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;

class Commands {

    private final static Options pvcOptions = new Options();

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

    public final static String addFileOptName = "add";
    public final static String addFileOptDesc = "Saves changes in repository";
    private final static Option addOpt = Option.builder()
            .longOpt(addFileOptName)
            .desc(addFileOptDesc)
            .hasArg(true)
            .build();

    public final static String commitOptName = "commit";
    public final static String commitOptDesc = "Commit added files";
    private final static Option commitOpt = Option.builder()
            .longOpt(commitOptName)
            .desc(commitOptDesc)
            .optionalArg(true)
            .numberOfArgs(1)
            .build();

    public static Options getPVCOptions() {
        pvcOptions.addOption(initialization);
        pvcOptions.addOption(contributorConfig);
        pvcOptions.addOption(addOpt);
        pvcOptions.addOption(commitOpt);
        return pvcOptions;
    }

}
