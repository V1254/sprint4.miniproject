package springData.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springData.domain.OrganizerUser;
import springData.repository.UserRepository;

public class OrganizerUserValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return OrganizerUser.class.equals(clazz);
	}
	
	private UserRepository userRepo;
	
	public OrganizerUserValidator(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public void validate(Object target, Errors errors) {
		OrganizerUser u = (OrganizerUser) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "", "Field cannot be empty.");
		
		if (userRepo.findByLogin(u.getLogin()) != null) {
			errors.rejectValue("login", "", "User with that username already exists.");
		}



	}

}
