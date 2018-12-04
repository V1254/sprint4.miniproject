package springData.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springData.domain.OrganizerUser;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model, String roleName) {
		model.addAttribute("orgUser", new OrganizerUser());
		return "admin/CreateUser";
	}

	@RequestMapping(value = "/create", params = "add", method = RequestMethod.POST)
	public String addNewUser(@RequestParam("roleName") String roleName, @ModelAttribute("orgUser") OrganizerUser u, BindingResult result, Model model) {
		return "admin/CreateUser";
	}

	@RequestMapping(value = "create", params = "cancel", method = RequestMethod.POST)
	public String cancelNewTodo() {
		return "admin/done";
	}
}
