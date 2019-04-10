package quickdocs.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static quickdocs.testutil.TypicalReminders.REM_A;
import static quickdocs.testutil.TypicalReminders.REM_B;
import static quickdocs.testutil.TypicalReminders.REM_E;
import static quickdocs.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import quickdocs.logic.CommandHistory;
import quickdocs.logic.commands.exceptions.CommandException;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;

public class AddRemCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executeValidAddReminder() throws Exception {
        CommandResult commandResult = new AddRemCommand(REM_E).execute(model, commandHistory);

        StringBuilder sb = new StringBuilder();
        sb.append("Reminder added:\n")
                .append(REM_E.toString() + "\n");

        Assert.assertEquals(sb.toString(), commandResult.getFeedbackToUser());
    }

    @Test
    public void executeDuplicateAddReminder() throws Exception {
        thrown.expect(CommandException.class);
        thrown.expectMessage(AddRemCommand.MESSAGE_DUPLICATE_REM);
        new AddRemCommand(REM_A).execute(model, commandHistory);
    }

    @Test
    public void equals() {
        AddRemCommand addRemA = new AddRemCommand(REM_A);

        // same object -> returns true
        assertTrue(addRemA.equals(addRemA));

        // same values -> returns true
        AddRemCommand addRemB = new AddRemCommand(REM_A);
        assertTrue(addRemA.equals(addRemB));

        // different types -> returns false
        assertFalse(addRemA.equals(1));

        // null -> returns false
        assertFalse(addRemA.equals(null));

        // different reminder -> returns false
        addRemB = new AddRemCommand(REM_B);
        assertFalse(addRemA.equals(addRemB));
    }
}