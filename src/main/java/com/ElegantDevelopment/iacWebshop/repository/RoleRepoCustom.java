package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.Role;

public interface RoleRepoCustom {
    Role getRoleByName(String roleName);
}
