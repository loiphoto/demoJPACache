package com.example.demojpacache.Entity;

import com.example.demojpacache.listener.AuditTrailListener;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "User")
@EntityListeners(AuditTrailListener.class)
public class User implements Serializable {

    //implement serializable để có thể save xuống redis  (kha nang do convert ra byte tranh bị exception)
    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String phone;

    @Column
    private String email;

    @ManyToOne
    private Role role;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User() {

    }
}
