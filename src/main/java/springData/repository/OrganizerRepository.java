package springData.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import springData.domain.Organizer;
import springData.domain.OrganizerUser;

public interface OrganizerRepository extends CrudRepository<Organizer, Integer> {
	List<Organizer> findByOwner(OrganizerUser o);
}