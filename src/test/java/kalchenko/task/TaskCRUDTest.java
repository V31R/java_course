package kalchenko.task;


import kalchenko.security.UserRepository;
import kalchenko.security.Users;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(statements = "insert into users(name, password, role) values('user','password','USER');")
public class TaskCRUDTest {

    @Autowired
    TaskCRUD taskCRUD;

    @Autowired
    UserRepository userRepository;

    private Users user;
    private Users admin;

    private String userName="user";

    @BeforeEach
    public void setUsers(){

        //if this tests suit runs after UserRepositoryTest
        // users table contains more than 1 user "user"
        if(userRepository.findAll().size() - 1 > 1){

            Users user  = new Users();
            user.setRole("USER");
            user.setPassword("password");
            userName="user"+userRepository.findAll().size();
            user.setName(userName);
            userRepository.save(user);

        }

        user = userRepository.findByName(userName).get();
        admin = userRepository.findByName("admin").get();

        taskCRUD.deleteAll();

    }

    @Test
    public void testFindAllByUserId(){

        Task task = new Task();
        task.setUser(user);
        task.setDescription("description");
        taskCRUD.save(task);
        Task adminTask = new Task();
        adminTask.setUser(admin);
        adminTask.setDescription("description");
        taskCRUD.save(adminTask);

        var usersTask = taskCRUD.findAllByUserId(user.getUserID()).stream().toList();
        var adminsTask = taskCRUD.findAllByUserId(admin.getUserID()).stream().toList();

        assertEquals(1, usersTask.size());
        assertEquals(1, adminsTask.size());

    }

    @Test
    public void testFindByUserId(){

        Task task = new Task();
        task.setUser(user);
        task.setDescription("description");

        //some tasks with different known id
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskCRUD.save(task));
        tasks.add(taskCRUD.save(task));

        for(var knownTask : tasks){

            var testTask = taskCRUD.findByUserId(knownTask.getId(), user.getUserID()).get();

            assertEquals(knownTask.getId(), testTask.getId());
            assertEquals(knownTask.getUser().getUserID(), testTask.getUser().getUserID());

        }

    }

}
