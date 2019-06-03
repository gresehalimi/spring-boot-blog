package com.amd.springbootblog.repository;

import com.amd.springbootblog.model.Post;
import com.amd.springbootblog.model.PostCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCategoriesRepository extends JpaRepository<PostCategories,Long> {
    List<PostCategories> findPostCategoriesByPost(Post post);

}
