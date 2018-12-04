package springData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springData.domain.Organizer;
import springData.domain.OrganizerUser;
import springData.domain.Role;
import springData.repository.RoleRepository;
import springData.repository.UserRepository;

import java.util.Arrays;

@SpringBootApplication
public class OrganizerApp implements CommandLineRunner  { 
	/**
	 * An organizer object for everyone to use.
	 */

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;
	
	public static void main(String[] args) {
        SpringApplication.run(OrganizerApp.class, args);
        
    }

	@Override
	public void run(String... args) throws Exception {
		
		// TODO Task
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		Role adminRole = new Role(0,"ADMIN");
		Role managerRole = new Role(1,"MANAGER");
		Role assistantRole = new Role(2,"ASSISTANT");

		roleRepository.save(adminRole);
		roleRepository.save(managerRole);
		roleRepository.save(assistantRole);

		OrganizerUser adminUser = new OrganizerUser();
		adminUser.setLogin("admin");
		adminUser.setPassword(pe.encode("admin"));
		adminUser.setRole(adminRole);

		// saves admin role
		userRepository.save(adminUser);

	}
}
