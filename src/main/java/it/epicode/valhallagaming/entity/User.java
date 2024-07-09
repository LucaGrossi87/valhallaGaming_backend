package it.epicode.valhallagaming.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String firstName;

    @NotBlank(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String lastName;

    @Email(message = "Campo obbligatorio")
    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference("user-booking")
    private List<Booking> bookingList;

    public User (String firstName, String lastName, String email, List<Booking> bookingList){
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.bookingList=bookingList;
    }
}
