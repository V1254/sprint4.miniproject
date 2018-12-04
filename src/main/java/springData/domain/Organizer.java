package springData.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * An organizer contains and manages a list of Todos.
 *
 */
@Entity(name="organizers")
public class Organizer {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	public List<Todo> todos;
	
	@ManyToOne(optional=false,cascade=CascadeType.PERSIST)
	private OrganizerUser owner;
	
	public Organizer() {
		todos = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrganizerUser getOwner() {
		return owner;
	}

	public void setOwner(OrganizerUser owner) {
		this.owner = owner;
	}

	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}

	/**
	 * retrieve all todos
	 * 
	 * @return list of todos
	 */
	public List<Todo> getTodos() {
		return todos;
	}

	/**
	 * adds a todo to the list
	 * 
	 * @param t
	 */
	public void addTodo(Todo t) {
		todos.add(t);
	}

	/**
	 * obtain a todo with the highest priority
	 * 
	 * @return
	 */
	public Todo getHighestPrioTodo() {
		Todo highest = todos.get(0);
		for (Todo t : todos) {
			if (t.getPriority() > highest.getPriority()) {
				highest = t;
			}
		}
		return highest;
	}

	/**
	 * Deletes a the todo for a given id.
	 * 
	 * @param id
	 */
	public void deleteTodo(int id) {
		todos.removeIf(t -> t.getId() == id);
	}
}