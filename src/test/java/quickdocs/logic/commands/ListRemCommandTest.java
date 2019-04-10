package quickdocs.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static quickdocs.logic.commands.CommandTestUtil.assertCommandSuccess;
import static quickdocs.testutil.TypicalReminders.REM_B;
import static quickdocs.testutil.TypicalReminders.REM_C;
import static quickdocs.testutil.TypicalReminders.REM_D;
import static quickdocs.testutil.TypicalReminders.getTypicalRemindersQuickDocs;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;
import quickdocs.model.QuickDocs;
import quickdocs.model.UserPrefs;
import quickdocs.model.reminder.ReminderWithinDatesPredicate;

public class ListRemCommandTest {
    private QuickDocs quickDocs = getTypicalRemindersQuickDocs();
    private Model model = new ModelManager(quickDocs, new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();
    private LocalDate testStart = LocalDate.parse("2019-10-24");
    private LocalDate testEnd = LocalDate.parse("2019-10-26");

    @Test
    public void execute_listRem_noReminderFound() {
        LocalDate testStart = LocalDate.parse("2019-01-01");
        LocalDate testEnd = LocalDate.parse("2019-02-01");
        String expectedMessage = String.format(ListRemCommand.MESSAGE_SUCCESS, testStart, testEnd);
        ListRemCommand command = new ListRemCommand(testStart, testEnd);

        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        Model expectedModel = new ModelManager(getTypicalRemindersQuickDocs(), new UserPrefs());
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredReminderList());
    }

    @Test
    public void execute_listRem_remindersFound() {
        String expectedMessage = String.format(ListRemCommand.MESSAGE_SUCCESS, testStart, testEnd);
        ListRemCommand command = new ListRemCommand(testStart, testEnd);

        ReminderWithinDatesPredicate predicate = new ReminderWithinDatesPredicate(testStart, testEnd);
        Model expectedModel = new ModelManager(getTypicalRemindersQuickDocs(), new UserPrefs());
        expectedModel.updateFilteredReminderList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(REM_B, REM_C, REM_D), model.getFilteredReminderList());
    }

    @Test
    public void equals() {
        ListRemCommand listRemA = new ListRemCommand(testStart, testEnd);

        // same object -> returns true
        Assert.assertTrue(listRemA.equals(listRemA));

        // same values -> returns true
        ListRemCommand listRemACopy = new ListRemCommand(testStart, testEnd);
        Assert.assertTrue(listRemA.equals(listRemACopy));

        // different types -> returns false
        Assert.assertFalse(listRemA.equals(1));

        // null -> returns false
        Assert.assertFalse(listRemA.equals(null));

        // different start date -> returns false
        ListRemCommand listRemB = new ListRemCommand(testStart.minusDays(1), testEnd);
        Assert.assertFalse(listRemA.equals(listRemB));

        // different end date -> returns false
        listRemB = new ListRemCommand(testStart, testEnd.minusDays(1));
        Assert.assertFalse(listRemA.equals(listRemB));
    }
}