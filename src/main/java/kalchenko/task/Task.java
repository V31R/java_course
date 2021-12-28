package kalchenko.task;

import kalchenko.security.Users;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private boolean done;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "user_id")
    private Users user;

    public Task(){}

    public Task(String description) {

        this.description = description;

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public boolean isDone() {

        return done;

    }

    public void setDone(boolean done) {

        this.done = done;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public Users getUser() {

        return user;

    }

    public void setUser(Users user) {

        this.user = user;

    }

}