package springData.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springData.OrganizerApp;
import org.springframework.security.core.userdetails.User;
import springData.domain.Organizer;
import springData.domain.OrganizerUser;
import springData.domain.Role;
import springData.domain.Todo;
import springData.repository.OrganizerRepository;
import springData.repository.TodoRepository;
import springData.repository.UserRepository;

@Controller
@RequestMapping("/")
public class DisplayTodoController {

	@Autowired
	OrganizerRepository organizerRepository;

	@Autowired
	TodoRepository todoRepository;

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String start() {
		return "redirect:/list";
	}

	@RequestMapping("/list")
	public String listTodos(Model model) {
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OrganizerUser user = userRepository.findByLogin(authUser.getUsername()); // fetch the user from the database.
		List<Todo> todos = new ArrayList<>();
		populateTodoForUser(user, todos);

		if (todos.isEmpty()) {
			return "NoTodo";
		} else {
			model.addAttribute("todos", todos);
		}
		return "ListTodos";
	}

	@RequestMapping(value = "/next", method = RequestMethod.GET)
	public String next(Model model) {
		User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // fetches the current User
		OrganizerUser user = userRepository.findByLogin(authUser.getUsername()); // fetch the user from the database.
		List<Todo> todos = new ArrayList<>();
		populateTodoForUser(user, todos);
		Todo t = null;
		try {
			t = todos.stream().max(Comparator.comparing(Todo::getPriority)).orElseThrow(Exception::new);
		} catch (Exception e) {
			return "NoTodo";
		}
		model.addAttribute("todo", t);
		return "NextTodo";
	}


	/**
	 * Populates the passed in list with todos based on the current Users role.
	 * @param user
	 * @param todos
	 */

	private void populateTodoForUser(OrganizerUser user, List<Todo> todos) {
		switch (user.getRole().getRole()){
			case "MANAGER":
				List<Organizer> organizers = organizerRepository.findByOwner(user);
				for(Organizer o : organizers){
					todos.addAll(o.getTodos());
				}
				break;
			case "ASSISTANT":
				todos.addAll((List<Todo>) todoRepository.findAll());
				break;
		}
	}

}
