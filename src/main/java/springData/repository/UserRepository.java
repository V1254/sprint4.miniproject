package springData.repository;

import org.springframework.data.repository.CrudRepository;

import springData.domain.OrganizerUser;

public interface UserRepository extends CrudRepository<OrganizerUser, Integer> {
	OrganizerUser findById(int id);
    OrganizerUser findByLogin(String login);
}