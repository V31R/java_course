package kalchenko.external;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Set;

public class ExternalUser implements UserDetails {

    private Long id;


    private String username;

    private String password;

    private String role;

    private transient Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){

        if(authorities==null){

            authorities= Set.of(new SimpleGrantedAuthority("ROLE_"+role));

        }

        return  authorities;
    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    @Override
    public String getUsername() {

        return username;

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(String name) {

        this.username = name;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getRole() {

        return role;

    }

    public void setRole(String role) {

        this.role = role;

    }

}
