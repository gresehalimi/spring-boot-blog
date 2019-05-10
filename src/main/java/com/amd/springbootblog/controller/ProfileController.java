package com.amd.springbootblog.controller;


import com.amd.springbootblog.data.PagingResultObject;
import com.amd.springbootblog.data.ResponseStatus;
import com.amd.springbootblog.security.CurrentUser;
import com.amd.springbootblog.security.UserPrincipal;
import com.amd.springbootblog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @GetMapping(value = "get/{pageNumber}/username/{username}")
    public ResponseEntity<?> filterPostsByCategoryAndOrContent(@PathVariable("username") String username, @PathVariable("pageNumber") int pageNumber) {
        ResponseEntity responseEntity;

        PagingResultObject pagingResultObject = profileService.profileByUsername(username, pageNumber);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }
        return responseEntity;
    }

    @GetMapping("get/{pageNumber}/home")
    public ResponseEntity<?> home(@PathVariable("pageNumber") int pageNumber, @CurrentUser UserPrincipal currentUser) {
        ResponseEntity responseEntity;
        PagingResultObject pagingResultObject = profileService.home(pageNumber,currentUser);
        if (pagingResultObject.getResponseStatus() == ResponseStatus.NO_DATA) {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).body("No content");
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.OK).body(pagingResultObject);
        }
        return responseEntity;
    }

}
