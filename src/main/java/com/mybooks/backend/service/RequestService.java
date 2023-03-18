package com.mybooks.backend.service;

import com.mybooks.backend.exception.RequestAlreadyInProgressException;
import com.mybooks.backend.exception.UserNotFoundException;
import com.mybooks.backend.model.Request;
import com.mybooks.backend.model.User;
import com.mybooks.backend.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestService {

    private final RequestRepository requestRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Request addRequest(User user) {
        List<Request> dbRequest =  requestRepository.findRequestByCreatedBy(user.getUserId());
        if (Boolean.TRUE.equals(dbRequest.isEmpty())) {
            Request newRequest = new Request();
            newRequest.setRequestId(UUID.randomUUID().toString());
            newRequest.setCreatedBy(user.getUserId());
            newRequest.setCreatedOn(new Date());
            newRequest.setCurrentTaskId("Review Sales");
            //newRequest.setAssignedTo(getAvailableExpert());
            return requestRepository.save(newRequest);
        }
        else {
            throw new RequestAlreadyInProgressException("User " + user.getUserId() + "already has request("
                    + dbRequest.get(0).getRequestId()+") in progress");
        }
    }

    public List<Request> getRequests(User user) {
        if (Boolean.TRUE.equals(user.isCustomer())) {
            return requestRepository.findRequestByCreatedBy(user.getUserId());
        } else if (Boolean.TRUE.equals(user.isExpert())) {
            return requestRepository.findRequestByAssignedTo(user.getUserId());
        }
        return null;
    }

}
