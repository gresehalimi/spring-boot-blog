package com.amd.springbootblog.repository;

import com.amd.springbootblog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //Filter Posts By Category Name
    @Query("SELECT p.id FROM Post p INNER JOIN PostCategories pc INNER JOIN Category c ON p.id=pc.post where pc.category=c.id and c.categoryName = :categoryName")
    Page<Post> findPostByCategory(@Param("categoryName") String categoryName, Pageable pageable);

    //Filter Posts By Content
    @Query("SELECT p FROM Post p WHERE p.content = :content")
    Page<Post> findByContent(@Param("content") String content, Pageable pageable);

    //Filter Posts by Content and by Category Name
    @Query("SELECT p.id FROM Post p INNER JOIN PostCategories pc INNER JOIN Category c ON p.id=pc.post where pc.category=c.id and c.categoryName = :categoryName and p.content=:content")
    Page<Post> findPostByCategoryAndContent(@Param("categoryName") String categoryName, @Param("content") String content, Pageable pageable);
}

