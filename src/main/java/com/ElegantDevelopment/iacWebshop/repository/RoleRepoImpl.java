package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.Role;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleRepoImpl implements RoleRepoCustom{

    @Autowired
    RoleRepo roleRepo;


    @Override
    public Role getRoleByName(String roleName) {
        List<Role> allRoles = roleRepo.findAll();

        for (Role role: allRoles){
            if (role.getRoleName().equals(roleName)){
                return role;
            }
        }
        return null;
    }
}
