package com.amd.springbootblog.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "categoryName", length = 45, nullable = false)
    private String categoryName;

    @OneToMany(targetEntity = PostCategories.class, mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostCategories> postCategories;

}
