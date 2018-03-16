package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.User;

public interface UserRepoCustom {
    boolean checkUserNameExistance(String username);
    User getUserByUsername(String username);
}
