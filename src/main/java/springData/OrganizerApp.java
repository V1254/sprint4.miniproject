package springData;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springData.domain.Organizer;

@SpringBootApplication
public class OrganizerApp implements CommandLineRunner  { 
	/**
	 * An organizer object for everyone to use.
	 */
	public static Organizer organizer = new Organizer();
	
	public static void main(String[] args) {
        SpringApplication.run(OrganizerApp.class, args);
        
    }

	@Override
	public void run(String... args) throws Exception {
		
		// TODO Task 

	}
}
