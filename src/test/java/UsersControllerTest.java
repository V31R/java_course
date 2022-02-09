import kalchenko.security.UserRepository;
import kalchenko.security.Users;
import kalchenko.security.UsersController;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.Suite;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Suite
public class UsersControllerTest {

    @Test
    public void testGet(){

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);

        List<Users> data= new ArrayList<Users>();
        data.add(new Users());
        Mockito.when(userRepositoryMock.findAll()).thenReturn(data);

        PasswordEncoder passwordEncoderMock= Mockito.mock(PasswordEncoder.class);

        UsersController usersController = new UsersController(userRepositoryMock,passwordEncoderMock);

        assertEquals(data,usersController.getUsers());

    }

    @Test
    public void testPut(){

        Users users =new Users();
        Users usersSpy = Mockito.spy(users);

        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);

        Mockito.when(userRepositoryMock.save(usersSpy)).thenReturn(usersSpy);

        PasswordEncoder passwordEncoderMock= Mockito.mock(PasswordEncoder.class);
        Mockito.when(passwordEncoderMock.encode("")).thenReturn("hash");


        UsersController usersController = new UsersController(userRepositoryMock,passwordEncoderMock);

        assertNotEquals(usersSpy.getPassword(),usersController.createUser(usersSpy).getPassword());
        assertEquals(usersSpy.getUserID(),usersController.createUser(usersSpy).getUserID());

    }


}
