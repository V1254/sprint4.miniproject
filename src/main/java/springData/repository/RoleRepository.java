package springData.repository;

import org.springframework.data.repository.CrudRepository;

import springData.domain.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
	Role findById(int id);
	Role findByRole(String roleName);
}