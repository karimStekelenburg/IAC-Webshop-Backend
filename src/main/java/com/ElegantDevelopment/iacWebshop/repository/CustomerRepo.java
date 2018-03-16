package com.ElegantDevelopment.iacWebshop.repository;

import com.ElegantDevelopment.iacWebshop.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
