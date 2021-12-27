package kalchenko.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //@Modifying
    @Query("from Task t where t.user.id=?#{ principal?.id }")
    Optional<Task> findByUserId(long id);

}