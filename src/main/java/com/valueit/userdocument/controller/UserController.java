package com.valueit.userdocument.controller;

import com.valueit.userdocument.entity.User;
import com.valueit.userdocument.exception.UserNotFoundException;
import com.valueit.userdocument.repository.RepoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private RepoUser repoUser ;

    @Autowired
    UserController(RepoUser repoUser ){
        this.repoUser = repoUser ;
    }

    @GetMapping(value="/users")
    public List<User> getAllUser(){
        return repoUser.findAll();
    }

    @GetMapping(value ="/users/{id}")
    public User getUser(@PathVariable Long id){
        return repoUser.findById(id).orElseThrow(()-> new UserNotFoundException(id)) ;
    }

    @PostMapping(value="/user")
    public User addUser(@RequestBody User user){
        return repoUser.save(user) ;
    }

    @PostMapping(value="/users")
    public List<User> addUsers(@RequestBody List<User> users){
        List<User> userList =new ArrayList<>() ;
        users.forEach(user -> {
           userList.add( repoUser.save(user)) ;
        });
        return userList;
    }

    @DeleteMapping(value="/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repoUser.deleteById(id);
    }


    @PutMapping(value="/users/{id}")
    public User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repoUser.findById(id)
                .map(user -> {
                    user.setFirstName(newUser.getFirstName());
                    user.setLastName(newUser.getLastName());
                    return repoUser.save(user);
                })
                .orElseGet(() -> {
                    newUser.setUserId(id);
                    return repoUser.save(newUser);
                });
    }
}
