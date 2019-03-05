package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.medicine.Medicine;

/**
 * A command to record purchase of a medicine into the model
 */
public class PurchaseMedicineViaPathCommand extends PurchaseMedicineCommand {

    private final String[] path;
    private final int quantity;
    private final int cost;

    public PurchaseMedicineViaPathCommand(String[] path, int quantity, int cost) {
        this.path = path;
        this.quantity = quantity;
        this.cost = cost;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        Optional<Medicine> medicine = model.findMedicine(path);
        if (!medicine.isPresent()) {
            throw new CommandException("No such medicine found.");
        }
        try {
            medicine.get().addQuantity(quantity);
        } catch (Exception ex) {
            throw new CommandException(ex.getMessage());
        }
        //model update purchase history
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (
                other instanceof PurchaseMedicineViaPathCommand
                && Arrays.equals(path, ((PurchaseMedicineViaPathCommand) other).path)
                && quantity == ((PurchaseMedicineViaPathCommand) other).quantity
                && cost == ((PurchaseMedicineViaPathCommand) other).cost);
    }
}