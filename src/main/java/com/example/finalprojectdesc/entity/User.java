package com.example.finalprojectdesc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Size(min = 3, max = 15)
    private String firstName;

    @Column(nullable = false)
    @Size(min = 3, max = 15)
    private String lastName;

    @Column(nullable = false)
    @Size(min = 6, max = 12)
    private String phone;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<Reservation> reservations;
}
