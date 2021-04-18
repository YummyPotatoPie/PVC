import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class PVCCommandHandler {

    public static void main(String[] args) throws ParseException {
        Options options = new Options();

        Option logfile = Option.builder()
                .longOpt("logFile")
                .argName("file")
                .hasArg()
                .desc("Use given file for log")
                .build();

        options.addOption(logfile);

        HelpFormatter formatter = new HelpFormatter();

        formatter.printHelp("P", options);
    }

}
