package springData.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import springData.domain.OrganizerUser;
import springData.domain.Role;
import springData.repository.RoleRepository;
import springData.repository.UserRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@InitBinder
	protected void initBinder(WebDataBinder binder){
		binder.addValidators(new OrganizerUserValidator(userRepository));
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String create(Model model, String roleName) {
		model.addAttribute("orgUser", new OrganizerUser());
		return "admin/CreateUser";
	}

	@RequestMapping(value = "/create", params = "add", method = RequestMethod.POST)
	public String addNewUser(@RequestParam("roleName") String roleName, @Valid @ModelAttribute("orgUser") OrganizerUser u, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "admin/CreateUser";
		}
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		Role role = roleRepository.findByRole(roleName);
		u.setPassword(pe.encode(u.getPassword()));
		u.setRole(role);
		userRepository.save(u);

		return "admin/done";
	}

	@RequestMapping(value = "create", params = "cancel", method = RequestMethod.POST)
	public String cancelNewTodo() {
		return "admin/done";
	}
}
