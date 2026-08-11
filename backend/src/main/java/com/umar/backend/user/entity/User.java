package com.umar.backend.user.entity;

import com.umar.backend.meeting.entity.Meeting;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String username;

    private String password;

    @OneToMany(mappedBy = "createdBy")
    private List<Meeting> createdMeetings;
    
    @Column(nullable = false, unique = true)
    private String email;

}
