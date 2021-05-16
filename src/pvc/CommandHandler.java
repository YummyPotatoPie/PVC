package pvc;

import org.apache.commons.cli.*;
import pvc.Exceptions.PVCException;

import static pvc.PathsAndTokens.nullMessage;

public class CommandHandler implements Handler<CommandLine> {

    /**
     * Main method of pvc program
     */
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

            System.exit(1);
        }

        CommandHandler commandHandler = new CommandHandler();

        try {
            commandHandler.handle(cmd);
        }
        catch (PVCException pvce) {
            System.out.println(pvce.getMessage());
        }

    }

    /**
     * Method which handle command from command line
     * @param cmd Command from command line
     */
    public void handle(CommandLine cmd) throws PVCException  {
        if (cmd.hasOption(Commands.initializationOptName)) {

            RepositoryInitializer repInitializer = new RepositoryInitializer();
            repInitializer.handle(System.getProperty("user.dir"));

        }
        if (cmd.hasOption(Commands.contributorConfigOptName)) {

            ContributorConfig contConfig = new ContributorConfig();
            contConfig.handle(cmd.getOptionValues(Commands.contributorConfigOptName));

        }
        if (cmd.hasOption(Commands.addFileOptName)) {

            FileAdder fileAdder = new FileAdder();
            fileAdder.handle(cmd.getOptionValues(Commands.addFileOptName)[0]);

        }
        if (cmd.hasOption(Commands.commitOptName)) {

            Committer commiter = new Committer();
            if (cmd.getOptionValues(Commands.commitOptName) != null)
                commiter.handle(cmd.getOptionValues(Commands.commitOptName)[0]);
            else
                commiter.handle(nullMessage);

        }
        if (cmd.hasOption(Commands.branchOptName)) {

            Brancher brancher = new Brancher();
            brancher.handle(cmd.getOptionValue(Commands.branchOptName));

        }
        if (cmd.hasOption(Commands.switchOptName)) {

            BranchSwitch switcher = new BranchSwitch();
            switcher.handle(cmd.getOptionValue(Commands.switchOptName));

        }
        if (cmd.hasOption(Commands.mergeOptName)) {

            Merger merger = new Merger();
            merger.handle(cmd.getOptionValue(Commands.mergeOptName));

        }

        if (cmd.hasOption(Commands.headOptName)) {

            HEADInformator informator = new HEADInformator();
            informator.handle(cmd.getOptionValue(Commands.headOptName));

        }
    }

}
