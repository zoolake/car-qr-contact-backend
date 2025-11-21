package com.chacall.chacall.repository.car;

import com.chacall.chacall.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarJpaRepository extends JpaRepository<Car, Long> {
    List<Car> findCarsByUserId(Long userId);

    Optional<Car> findCarByUserIdAndNickname(Long userId, String nickname);
}
