package com.valueit.userdocument.repository;

import com.valueit.userdocument.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoUser extends JpaRepository<User, Long> {

}
