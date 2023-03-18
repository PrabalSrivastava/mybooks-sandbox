package com.mybooks.backend;

import com.mybooks.backend.model.Request;
import com.mybooks.backend.model.User;
import com.mybooks.backend.service.RequestService;
import com.mybooks.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestResource {

    private final RequestService requestService;

    private final UserService userService;

    public RequestResource(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Request> addRequest(@RequestBody User user) {
        Request request = requestService.addRequest(user);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<Request>> getRequests(@RequestBody User user) {
        List<Request> requests = requestService.getRequests(userService.findUserByUserId(user.getUserId()));
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

}
