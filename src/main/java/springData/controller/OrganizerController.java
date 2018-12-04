package springData.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springData.OrganizerApp;
import springData.domain.Organizer;
import springData.domain.OrganizerUser;
import springData.domain.Todo;
import springData.repository.OrganizerRepository;
import springData.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/")
public class OrganizerController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrganizerRepository organizerRepository;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(new TodoValidator());
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("todo", new Todo());
		return "CreateTodo";
	}

	@RequestMapping(value = "create", params = "add", method = RequestMethod.POST)
	public String addNewTodo(@Valid @ModelAttribute("todo") Todo t, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "CreateTodo";
		} else {
			User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			OrganizerUser currentUser = userRepository.findByLogin(authUser.getUsername());
			List<Organizer> organizers = currentUser.getOrganizers();
			if(organizers.isEmpty()){
				Organizer o = new Organizer();
				o.setOwner(currentUser);
				organizers.add(o);
			}

			Organizer toAddTo = organizers.get(0);
			toAddTo.addTodo(t);
			organizerRepository.save(toAddTo);
			userRepository.save(currentUser);



//			OrganizerApp.organizer.addTodo(t);
			return "redirect:/list";
		}
	}

	@RequestMapping(value = "create", params = "cancel", method = RequestMethod.POST)
	public String cancelNewTodo() {
		return "redirect:/list";
	}

	@RequestMapping(value = "delete", params = "id", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam("id") int id) {
		OrganizerApp.organizer.deleteTodo(id);
		return "redirect:/list";
	}
}
