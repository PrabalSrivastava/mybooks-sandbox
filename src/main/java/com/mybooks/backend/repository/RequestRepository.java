package com.mybooks.backend.repository;

import com.mybooks.backend.model.Request;
import com.mybooks.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findRequestByCreatedBy(String userId);

    List<Request> findRequestByAssignedTo(String userId);
}
