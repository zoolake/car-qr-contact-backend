package com.chacall.chacall.repository;

import com.chacall.chacall.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactJpaRepository extends JpaRepository<Contact, Long> {
    List<Contact> findContactsByCarId(Long carId);
}
