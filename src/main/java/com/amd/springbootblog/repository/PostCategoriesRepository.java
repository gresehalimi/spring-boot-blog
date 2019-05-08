package com.amd.springbootblog.repository;

import com.amd.springbootblog.model.PostCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoriesRepository extends JpaRepository<PostCategories,Long> {

}
