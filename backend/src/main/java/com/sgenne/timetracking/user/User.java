package com.sgenne.timetracking.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = User.TABLE_NAME)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String TABLE_NAME = "USERS";

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String username;
    private String password;
    private String email;
}
