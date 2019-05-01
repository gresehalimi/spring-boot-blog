package com.amd.springbootblog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullName", length = 45, nullable = false, unique = false)
    private String fullName;

    @Column(name = "username", length = 45, nullable = false, unique = false)
    private String username;

    @Column(name = "email", length = 45, nullable = false, unique = false)
    private String email;

    @Column(name = "password", length = 1024, nullable = false)
    private String password;

    @Column(name = "created_time", length = 45, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @OneToMany(targetEntity = Post.class, fetch = FetchType.LAZY)
    private List<Post> post;

    public User(String name, String username, String email) {
    }
}