package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.exception.UserNotFoundException;
import com.ElegantDevelopment.iacWebshop.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRepoImpl implements UserRepoCustom {

    @Autowired
    UserRepo userRepo;

    @Override
    public boolean checkUserNameExistance(String username){
        List<User> allUsers = userRepo.findAll();
        for (User u : allUsers){
            if (username.equals(u.getUsername())) {
                return true;
            }
        } return false;
    }

    @Override
    public User getUserByUsername(String username) {
        List<User> allUsers = userRepo.findAll();
        for (User user : allUsers){
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        throw new UserNotFoundException(username);
    }
}
