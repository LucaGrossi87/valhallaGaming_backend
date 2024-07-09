package it.epicode.valhallagaming.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admins")
@Data
@NoArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String userName;

    @Email(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean loggedin = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public Admin(String firstName, String lastName, String userName, String email, String password, boolean loggedin, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.loggedin = loggedin;
        this.role = role;
    }
}

