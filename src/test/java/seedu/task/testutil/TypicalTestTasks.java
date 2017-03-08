package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

	public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

	public TypicalTestTasks() {
		try {
			alice = new TaskBuilder().withName("Alice Pauline")
					.withAddress("123, Jurong West Ave 6, #08-111").withEmail("123456 1234")
					.withPhone("123456 1234")
					.withTags("friends").build();
			benson = new TaskBuilder().withName("Benson Meier").withAddress("311, Clementi Ave 2, #02-25")
					.withEmail("123456 1234").withPhone("123456 1234")
					.withTags("owesMoney", "friends").build();
			carl = new TaskBuilder().withName("Carl Kurz").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("wall street").build();
			daniel = new TaskBuilder().withName("Daniel Meier").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("10th street").build();
			elle = new TaskBuilder().withName("Elle Meyer").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("michegan ave").build();
			fiona = new TaskBuilder().withName("Fiona Kunz").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("little tokyo").build();
			george = new TaskBuilder().withName("George Best").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("4th street").build();

			// Manually added
			hoon = new TaskBuilder().withName("Hoon Meier").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("little india").build();
			ida = new TaskBuilder().withName("Ida Mueller").withPhone("123456 1234")
					.withEmail("123456 1234").withAddress("chicago ave").build();
		} catch (IllegalValueException e) {
			e.printStackTrace();
			assert false : "not possible";
		}
	}

	public static void loadTaskManagerWithSampleData(TaskManager ab) {
		for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
			try {
				ab.addTask(new Task(task));
			} catch (UniqueTaskList.DuplicateTaskException e) {
				assert false : "not possible";
			}
		}
	}

	public TestTask[] getTypicalTasks() {
		return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
	}

	public TaskManager getTypicalTaskManager() {
		TaskManager ab = new TaskManager();
		loadTaskManagerWithSampleData(ab);
		return ab;
	}
}
