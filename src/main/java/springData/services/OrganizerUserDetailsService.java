package springData.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import springData.repository.UserRepository;
import springData.domain.OrganizerUser;

@Service
public class OrganizerUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;	

	public UserDetails loadUserByUsername(String login)
			throws UsernameNotFoundException {
		
		OrganizerUser domainUser = userRepo.findByLogin(login);
		
		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + domainUser.getRole().getRole()));
		
		return new User(
				domainUser.getLogin(), 
				domainUser.getPassword(), 
				enabled, 
				accountNonExpired, 
				credentialsNonExpired, 
				accountNonLocked,
				authorities
		);
	}
	
}
