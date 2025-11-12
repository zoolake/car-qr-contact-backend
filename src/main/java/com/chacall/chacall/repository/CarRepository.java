package com.chacall.chacall.repository;

import com.chacall.chacall.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByUserId(Long userId);
}
