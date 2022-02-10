package kalchenko.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TaskCRUD  extends CrudRepository<Task, Long>, TaskRepository {

    @Query("from Task t where t.user.id=?1")
    Optional<Task> findAllByUserId(long id);

    @Query("from Task t where t.id=?1 and t.user.id=?2")
    Optional<Task> findByUserId(long t_id, long u_id);

}
