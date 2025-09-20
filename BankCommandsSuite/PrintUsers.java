package org.poo.BankCommandsSuite;

import org.poo.OutputBuilder;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.List;

/**
 * Command class responsible for printing the list of users.
 * This class uses the provided OutputBuilder to output all users at a specific timestamp.
 */
public class PrintUsers implements BankCommand {
    private final List<User> users;
    private final OutputBuilder outputBuilder;
    private final CommandInput commandInput;

    /**
     * Constructs a PrintUsers command.
     *
     * @param users the list of users to be printed
     * @param outputBuilder the output builder used to print the users
     */
    public PrintUsers(final List<User> users,
                      final OutputBuilder outputBuilder, final CommandInput commandInput) {
        this.users = users;
        this.outputBuilder = outputBuilder;
        this.commandInput = commandInput;
    }

    /**
     * Executes the command to print the users' information at the specified timestamp.
     * This method calls the outputBuilder to print all users.
     */
    @Override
    public void execute() {
        int timestamp = commandInput.getTimestamp();
        outputBuilder.printUsers(users, timestamp);
    }
}
