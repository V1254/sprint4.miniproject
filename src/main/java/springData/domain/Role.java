package springData.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Role {
	@Id
	private int id;
	
	@Column(nullable=false)
	private String role;

	@OneToMany(mappedBy="role")
	private Set<OrganizerUser> users;
	
	// HIBERNATE CONSTRAINT:
	// when a constructor different from default is present
	// the default constructor needs to be declared
	public Role() { }
	
	public Role(int id, String role) {
		this.id = id; 
		this.role = role; 
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<OrganizerUser> getUsers() {
		return users;
	}

	public void setUsers(Set<OrganizerUser> users) {
		this.users = users;
	}
	
	
}
