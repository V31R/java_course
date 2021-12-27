package kalchenko.security;

import kalchenko.SecurityConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UsersController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @PostMapping("/user")
    public Users createUser(@RequestBody Users user){

        user.setRole(SecurityConfiguration.USER_ROLE);
        user.setPassword(passwordEncoder.encode((user.getPassword())));
        Users newUser=userRepository.save(user);
        newUser.setPassword("<hidden>");
        return newUser;

    }

    @GetMapping("/user")
    public List<Users> getUsers(){

        return userRepository.findAll();

    }



}
