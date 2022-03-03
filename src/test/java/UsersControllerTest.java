import kalchenko.SecurityConfiguration;
import kalchenko.security.UserRepository;
import kalchenko.security.Users;
import kalchenko.security.UsersController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Suite
public class UsersControllerTest {

    private final PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
    private Users user;
    private UserRepository userRepositoryMock;
    private UsersController usersController;

    @BeforeEach

    public void setUp(){
        user = getUser();
        userRepositoryMock = Mockito.mock(UserRepository.class);

        Mockito.when(userRepositoryMock.save(Mockito.eq(user))).thenReturn(getUser());

        usersController= new UsersController(userRepositoryMock,passwordEncoder);

    }

   @Test
   public void testPostUsers_setUserRole(){

        usersController.createUser(user);

        assertEquals(SecurityConfiguration.USER_ROLE,user.getRole());

   }

    @Test
    public void testPostUsers_setEncodePassword(){

        usersController.createUser(user);

        assertEquals(passwordEncoder.encode(getUser().getPassword()),user.getPassword());

    }

    @Test
    public void testPostUsers_saveUser(){

        usersController.createUser(user);

        Mockito.verify(userRepositoryMock).save(Mockito.any());

    }

    @Test
    public void testGetUsers_getAll(){

        usersController.getUsers();

        Mockito.verify(userRepositoryMock).findAll();

    }

    private static Users getUser(){

        Users user = new Users();
        user.setPassword("password");
        user.setName("User");

        return user;

    }


}
