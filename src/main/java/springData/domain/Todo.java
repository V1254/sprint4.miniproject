package springData.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A todo is an action item with a task, a description, and a year.
 *
 */
@Entity(name = "todos")
public class Todo {

	/**
	 * a unique identifier for the todo
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;

	@Column(nullable=false)
	private String task;
	private String description;
	private int priority = 0;

	private boolean important;


	/**
	 * Creates a new Todo with the task and description
	 * 
	 * @param task        the name of the task
	 * @param description the description of the task
	 */
	public Todo(String task, String description) {
		this.task = task;
		this.description = description;
	}

	/**
	 * default constructor
	 */
	public Todo() {
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int newPrio) {
		if (priority <= newPrio) {
			this.priority = newPrio;
		} else {
			throw new RuntimeException("Cannot reduce priority!");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

}