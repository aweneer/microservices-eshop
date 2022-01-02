package swa.eshop.user.model;

import lombok.*;
import javax.validation.constraints.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    @Size(min = 2, max = 30)
    private String firstName;

    @Column
    @NotNull
    @Size(min = 2, max = 60)
    private String lastName;

    @Column
    @NotNull
    @Size(min = 2, max = 30)
    private String email;

    @Column
    @NotNull
    @Size(min = 1, max = 120)
    private String address;

    public User() {}

    public User(String username, String firstName, String lastName, String email, String address) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }

    @Override
    public String toString() {
        return "USER:\n"+ username + "\n" + firstName + "\n"+ lastName + "\n"+ email + "\n"+ address + "\n";
    }
}
