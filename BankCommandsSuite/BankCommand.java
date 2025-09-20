package org.poo.BankCommandsSuite;

/**
 * Represents a generic bank command that can be executed.
 * Any specific bank command (such as adding funds or creating an account)
 * should implement this interface and define the specific behavior
 * of the command in the {@link #execute()} method.
 */
public interface BankCommand {

    /**
     * Executes the bank command.
     * The specific behavior of the command is defined by the implementing class.
     */
    void execute();
}
