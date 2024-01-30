package com.shopme.admin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserRestController {
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/check_email")
    public String checkDublicateEmail(@RequestParam(name = "email") String email, @RequestParam(name = "id", required = false) Integer id){
        return userService.isEmailUnique(id,email)? "Ok": "Duplicated";
    }
}
