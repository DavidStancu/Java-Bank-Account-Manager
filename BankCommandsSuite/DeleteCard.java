package org.poo.BankCommandsSuite;

import org.poo.AccountsSuite.Account;
import org.poo.CardsSuite.Card;
import org.poo.TransactionsSuite.TransactionFactory;
import org.poo.TransactionsSuite.TransactionTag;
import org.poo.User;
import org.poo.fileio.CommandInput;

import java.util.ArrayList;
import java.util.List;

import static org.poo.BankTeller.findUserByEmail;

/**
 * Represents a command that deletes a card from a user's account.
 * The card is identified by its card number, and once deleted,
 * a transaction record is created for the action.
 */
public class DeleteCard implements BankCommand {
    private final List<User> users;
    private final CommandInput commandInput;

    /**
     * Constructs a DeleteCard command.
     *
     * @param users the list of users whose accounts may contain the card to be deleted
     * @param commandInput the input data containing the card details and timestamp
     */
    public DeleteCard(final List<User> users, final CommandInput commandInput) {
        this.users = users;
        this.commandInput = commandInput;
    }

    /**
     * Executes the command to delete a card from a user's account.
     * The card is identified by the card number provided in the input.
     * If the card is deleted successfully, a transaction record is created.
     * If the card number is not found, no action is taken.
     */
    public void execute() {
        String email = commandInput.getEmail();
        String cardNumber = commandInput.getCardNumber();

        User user = findUserByEmail(email);
        if (user == null) {
            return;
        }

        for (Account account : user.getAccounts()) {
            List<Card> updatedCards = new ArrayList<>();
            boolean cardDeleted = false;

            for (Card card : account.getCards()) {
                if (!card.getCardNumber().equals(cardNumber)) {
                    updatedCards.add(card);
                } else {
                    cardDeleted = true;
                    user.addTransaction(TransactionFactory
                            .createTransaction(TransactionTag.CARD_DELETED,
                                    commandInput.getTimestamp(), account.getIBAN(),
                                    card.getCardNumber(), user.getEmail()));
                }
            }

            if (cardDeleted) {
                account.setCards(updatedCards);
                return;
            }
        }
    }
}
