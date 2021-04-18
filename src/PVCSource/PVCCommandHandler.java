package PVCSource;

import org.apache.commons.cli.*;

public class PVCCommandHandler {

    public static void main(String[] args) {
        Options options = new Options();

        options.addOption("i", "init", false, "Repository initialization");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("pvc", options);
        }

        if (cmd.hasOption("i")) {
            System.out.println("Initialization of repository...");
            System.out.println("Done!");
        }

    }

}
