package kalchenko;

import kalchenko.security.UserRepository;
import kalchenko.security.Users;
import kalchenko.security.UsersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.awt.desktop.SystemEventListener;

@Configuration
@EnableConfigurationProperties
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final String ADMIN_ROLE="ADMIN";
    public static final String USER_ROLE="USER";

    @Lazy
    @Autowired
    UsersDetailService usersDetailService;

    //@Override
    //public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {

        //authBuilder.inMemoryAuthentication()
              //  .withUser("admin")
               // .password("1234")
               // .authorities(ADMIN_ROLE);
                //.and()
                //.withUser("user1")
                //.password("user1")
                //.authorities(USER_ROLE);
                //.userDetailsService(usersDetailService);

   // }

    //@Bean
    //public UserDetailsService userDetailsService(UserRepository userRepository){

      // return (username ->
              // userRepository.findByName(username)
                     //  .orElseThrow(()-> new UsernameNotFoundException(username)));//(u sername->userRepository.findByName(username.map(u->u.getName())

    //}

   // @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(usersDetailService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //HttpSecurity disable =
                //http.csrf().disable()
                //http.authorizeRequests().antMatchers("/**").hasRole("ADMIN").and().formLogin();
                http.authorizeRequests()
                        .regexMatchers("/tasks").hasRole(USER_ROLE)
                        .regexMatchers("/admin").hasRole(ADMIN_ROLE)
                //.mvcMatchers(HttpMethod.POST, "/account/register").permitAll()
                .anyRequest().authenticated()
                .and()
                //.userDetailsService(usersDetailService)
                .httpBasic()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        System.out.println("Work");
        //http.authorizeRequests().antMatchers("/**").hasRole("ADMIN").and().formLogin();
                //.and().httpBasic()
                //.and().sessionManagement().disable();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();

    }

}
