package springData.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import springData.domain.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
    List<Todo> findByTask(String task);
    
    List<Todo> findByDescription(String description);
}