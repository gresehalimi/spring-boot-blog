package com.amd.springbootblog.service;

import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.dto.CategoryData;
import com.amd.springbootblog.dto.PostData;
import com.amd.springbootblog.model.Post;
import com.amd.springbootblog.model.PostCategories;
import com.amd.springbootblog.model.User;
import com.amd.springbootblog.repository.PostRepository;
import com.amd.springbootblog.repository.UserRepository;
import com.amd.springbootblog.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    // domain of photo server.
    @Value("${image.url}")
    String url;

    public PagingResultObject profileByUsername(String username, int pageNumber) {
        PagingResultObject pagingResultObject = new PagingResultObject();
        pagingResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        pagingResultObject.setStatus(409);
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        try {
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (!optionalUser.isPresent()) {
                pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
                pagingResultObject.setStatus(204);
                System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
                return pagingResultObject;
            }
            User user = optionalUser.get();
            Page<Post> postPage = postRepository.findByUserOrderByCreatedTime(user, PageRequest.of(pageNumber, 3));
            pagingResultObject.setStatus(200);
            pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            pagingResultObject.setDataList(getPosts(postPage));
        } catch (Exception e) {
            pagingResultObject.setStatus(204);
            pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return pagingResultObject;
    }

    public PagingResultObject home(int pageNumber, UserPrincipal currentUser) {
        PagingResultObject pagingResultObject = new PagingResultObject();
        pagingResultObject.setResponseStatus(ResponseStatus.CONFLICT);
        pagingResultObject.setStatus(409);
        try {
            Page<Post> postPage = postRepository.findAllByOrderByCreatedTime(PageRequest.of(pageNumber - 1, 3, Sort.Direction.ASC, "id"));
            pagingResultObject.setStatus(200);
            pagingResultObject.setResponseStatus(ResponseStatus.SUCCESS);
            pagingResultObject.setDataList(getPosts(postPage));
        } catch (Exception e) {
            pagingResultObject.setStatus(204);
            pagingResultObject.setResponseStatus(ResponseStatus.NO_DATA);
        }
        return pagingResultObject;
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


