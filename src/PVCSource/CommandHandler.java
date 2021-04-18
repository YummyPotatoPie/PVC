package PVCSource;

import org.apache.commons.cli.*;

public class CommandHandler implements Handler<CommandLine> {

    public static void main(String[] args) {
        Options options = Commands.getPVCOptions();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        }
        catch (ParseException pe) {
            HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("pvc", options);
        }

        CommandHandler commandHandler = new CommandHandler();
        commandHandler.handle(cmd);

    }

    public void handle(CommandLine cmd) {
        if (cmd.hasOption(Commands.initializationOptName)) {
            System.out.println("Initialization of repository...");
            System.out.println("Done!");
        }
    }

}
