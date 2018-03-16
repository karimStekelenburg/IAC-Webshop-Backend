package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long>, RoleRepoCustom {
}
