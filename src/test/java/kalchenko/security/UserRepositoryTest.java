package kalchenko.security;

import kalchenko.SecurityConfiguration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql(statements = "delete from users where name in('user','owner');")
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void haveAdmin_Always(){

        var admin = userRepository.findByName("admin").stream().toList();

        assertEquals(1, admin.size());

    }

    @Test
    public void getUserByName(){

        Users user = getTestUser();

        userRepository.save(user);

        var users = userRepository.findByName(user.getUsername()).stream().toList();

        assertEquals(1, users.size());

    }


    private static  Users getTestUser(){

        Users user = new Users();
        user.setName("user");
        user.setPassword("password");
        user.setRole(SecurityConfiguration.USER_ROLE);

        return user;

    }

}
