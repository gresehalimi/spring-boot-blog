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
@Table(name = "tbl_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 45, nullable = false)
    private String title;

    @Column(name = "content", length = 600, nullable = false)
    private String content;

    @Column(name = "created_time", length = 45, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @OneToOne(targetEntity = File.class, mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private File file;

    @OneToMany(targetEntity = PostComment.class, mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostComment> comments;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToMany(targetEntity = PostCategories.class, mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostCategories> postCategories;


    public void addComment(PostComment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(PostComment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
}


