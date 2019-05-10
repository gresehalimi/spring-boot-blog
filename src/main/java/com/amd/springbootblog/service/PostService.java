package com.amd.springbootblog.service;

import com.amd.springbootblog.common.BaseAbstractService;
import com.amd.springbootblog.data.BooleanResultObject;
import com.amd.springbootblog.data.DataResultObject;
import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.CategoryData;
import com.amd.springbootblog.dto.PostData;
import com.amd.springbootblog.dto.PostRegister;
import com.amd.springbootblog.dto.PostUpdate;
import com.amd.springbootblog.model.Category;
import com.amd.springbootblog.model.Post;
import com.amd.springbootblog.model.PostCategories;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.CategoryRepository;
import com.amd.springbootblog.repository.PostCategoriesRepository;
import com.amd.springbootblog.repository.PostRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostService extends BaseAbstractService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    PostCategoriesRepository postCategoriesRepository;

    @Value("${file.location}")
    String fileLocation;

    // domain of photo server.
    @Value("${image.url}")
    String url;

    public BooleanResultObject createPost(PostRegister postRegister, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);

        try {
            Post post = new Post();
            post.setTitle(postRegister.getTitle());
            post.setContent(postRegister.getContent());
            post.setTemporaryImageName(writeImage(postRegister.getImage(), fileLocation));

            post.setCreatedTime(new Date());
            Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());
            if (!optionalUser.isPresent()) {
                booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                booleanResultObject.setStatus(204);
                booleanResultObject.setMessage("No user by this username");
                return booleanResultObject;
            }
            User user = optionalUser.get();
            post.setUser(user);
            List<CategoryData> categoriesList = postRegister.getCategories();

            List<PostCategories> postCategoriesList = new ArrayList<>();
            for (CategoryData categoryData : categoriesList) {
                Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryData.getCategoryName());

                if (!optionalCategory.isPresent()) {
                    booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                    booleanResultObject.setStatus(204);
                    booleanResultObject.setMessage("No category exists by this name, try another category");
                    return booleanResultObject;
                }
                Category category = optionalCategory.get();
                PostCategories postCategories = new PostCategories(post, category);

                postCategoriesList.add(postCategories);
            }
            post.setPostCategories(new ArrayList<>(postCategoriesList));

            postRepository.save(post);

            booleanResultObject.setStatus(200);
            booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            booleanResultObject.setMessage("Post successfully created");

        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
            booleanResultObject.setMessage("Something happened, Post could not be created");
        }
        return booleanResultObject;
    }

    public BooleanResultObject updatePost(Long id, PostUpdate postUpdate, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setStatus(409);
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            Optional<Post> optionalPost = postRepository.findById(id);
            if (optionalPost.isPresent()) {

                Optional<User> optionalUser = userRepository.findByUsername(currentUser.getUsername());
                if (!optionalUser.isPresent()) {
                    booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                    booleanResultObject.setStatus(204);
                    booleanResultObject.setMessage("No user by this username");
                    return booleanResultObject;
                }
                User user = optionalUser.get();

                Post post = optionalPost.get();

                if (user.getUsername().equals(post.getUser().getUsername())) {
                    post.setTitle(postUpdate.getTitle());
                    post.setContent(postUpdate.getContent());
                    post.setTemporaryImageName(writeImage(postUpdate.getImage(), fileLocation));
                    post.setCreatedTime(new Date());
                    post.setUser(user);

                    List<CategoryData> categoriesList = postUpdate.getCategories();
                    List<PostCategories> postCategoriesList = new ArrayList<>();
                    for (CategoryData categoryData : categoriesList) {
                        Optional<Category> optionalCategory = categoryRepository.findByCategoryName(categoryData.getCategoryName());

                        if (!optionalCategory.isPresent()) {
                            booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                            booleanResultObject.setStatus(204);
                            booleanResultObject.setMessage("No category exists by this name, try another category");
                            return booleanResultObject;
                        }
                        Category category = optionalCategory.get();

                        PostCategories postCategories = new PostCategories(post, category);

                        postCategoriesList.add(postCategories);
                    }
                    post.setPostCategories(postCategoriesList);

                    postRepository.save(post);
                    booleanResultObject.setStatus(200);
                    booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                    booleanResultObject.setMessage("Post updated Successfully");
                    return booleanResultObject;
                }

            } else
                booleanResultObject.setStatus(204);
            booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
            booleanResultObject.setMessage("Post Not Found by this id");
            return booleanResultObject;

        } catch (Exception e) {
            booleanResultObject.setStatus(500);
            booleanResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return booleanResultObject;
    }

    public DataResultObject getPost(Long postId, UserPrincipal currentUser) {
        DataResultObject dataResultObject = new DataResultObject();
        dataResultObject.setStatus(409);
        dataResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        try {
            PostData postData = new PostData();

            Optional<Post> optionalPost = postRepository.findById(postId);

            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                if (currentUser.getUsername().equals(post.getUser().getUsername())) {
                    postData.setTitle(post.getTitle());
                    postData.setContent(post.getContent());
                    List<PostCategories> pc = post.getPostCategories();
                    List<CategoryData> categories = new ArrayList<>();

                    for (int i = 0; i < pc.size(); i++) {
                        CategoryData categoryData = new CategoryData();
                        String categoryName = pc.get(i).getCategory().getCategoryName();
                        categoryData.setCategoryName(categoryName);
                        categories.add(categoryData);
                    }
                    postData.setCategories(categories);
                    String imageUrl = url + post.getTemporaryImageName();
                    postData.setImageUrl(imageUrl);
                    postData.setUsername(currentUser.getUsername());

                    dataResultObject.setStatus(200);
                    dataResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                    dataResultObject.setData(postData);
                    return dataResultObject;
                }
            } else
                dataResultObject.setStatus(209);
            dataResultObject.setResponseStatus(ResponseStatus.NO_DATA);
            dataResultObject.setData("You are not the owner of the selected post");
            return dataResultObject;
        } catch (Exception e) {
            dataResultObject.setStatus(500);
            dataResultObject.setResponseStatus(ResponseStatus.INTERNAL_SERVER_ERROR);
        }
        return dataResultObject;
    }

    @Secured("ROLE_ADMIN")
    public PagingResultObject getAllPosts(int pageNo) {
        PagingResultObject pagingResultObject = new PagingResultObject();
        pagingResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        pagingResultObject.setStatus(409);
        try {
            Page<Post> postPage = postRepository.findAllByOrderByCreatedTime(PageRequest.of(pageNo - 1, 7));
            pagingResultObject.setStatus(200);
            pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            pagingResultObject.setDataList(getPosts(postPage));
        } catch (Exception e) {
            pagingResultObject.setStatus(204);
            pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return pagingResultObject;
    }


    public PagingResultObject filterPostsByCategoryAndOrContent(String categoryName, String content, int pageNo) {
        PagingResultObject pagingResultObject = new PagingResultObject();
        System.err.println(categoryName + " ---> " + content + " ---> " + pageNo);
        try {

            if (content.isEmpty() && !categoryName.isEmpty()) {
                Page<Post> postPage = postRepository.findPostByCategory(categoryName, PageRequest.of(pageNo - 1, 3, Sort.Direction.ASC, "id"));
                pagingResultObject.setResponseStatus(ResponseStatus.OK);
                pagingResultObject.setDataList(getPosts(postPage));

            } else if (!content.isEmpty() && categoryName.isEmpty()) {

                Page<Post> postPage = postRepository.findByContent(content, PageRequest.of(pageNo - 1, 3, Sort.Direction.ASC, "id"));
                pagingResultObject.setResponseStatus(ResponseStatus.OK);
                pagingResultObject.setDataList(getPosts(postPage));

            } else {
                Page<Post> postPage = postRepository.findPostByCategoryAndContent(categoryName, content, PageRequest.of(pageNo - 1, 3, Sort.Direction.ASC, "id"));
                pagingResultObject.setResponseStatus(ResponseStatus.OK);
                pagingResultObject.setDataList(getPosts(postPage));
            }
        } catch (Exception e) {
            pagingResultObject.setStatus(204);
            pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return pagingResultObject;
    }


    public BooleanResultObject deletePost(Long postId, UserPrincipal currentUser) {
        BooleanResultObject booleanResultObject = new BooleanResultObject();
        booleanResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        booleanResultObject.setStatus(409);
        try {
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                if (post.getUser().getUsername().equals(currentUser.getUsername())) {

                    postRepository.delete(post);

                    booleanResultObject.setStatus(200);
                    booleanResultObject.setResponseStatus(ResponseStatus.SUCCESS);
                    booleanResultObject.setMessage("Post successfully deleted");
                } else
                    booleanResultObject.setMessage("you are not the owner of the post, you can't delete it!");
            }
        } catch (Exception e) {
            booleanResultObject.setStatus(204);
            booleanResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return booleanResultObject;
    }

    private List<PostData> getPosts(Page<Post> posts) {
        List<PostData> postDataList = new ArrayList<>();
        try {
            for (Post post : posts) {

                PostData postData = new PostData();
                postData.setTitle(post.getTitle());
                postData.setContent(post.getContent());
                String imageUrl = url + post.getTemporaryImageName();
                postData.setImageUrl(imageUrl);
                postData.setUsername(post.getUser().getUsername());
                List<CategoryData> categoryData = new ArrayList<>();
                for (PostCategories pc : post.getPostCategories()) {
                    categoryData.add(new CategoryData(pc.getCategory().getCategoryName()));
                }
                postData.setCategories(categoryData);
                postDataList.add(postData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postDataList;
    }


}








