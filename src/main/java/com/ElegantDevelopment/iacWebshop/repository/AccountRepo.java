package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
}
