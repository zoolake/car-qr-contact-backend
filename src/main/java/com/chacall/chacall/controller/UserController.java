package com.chacall.chacall.controller;

import com.chacall.chacall.dto.request.CarRegisterRequest;
import com.chacall.chacall.dto.request.UserSignupRequest;
import com.chacall.chacall.dto.response.CarResponse;
import com.chacall.chacall.service.CarService;
import com.chacall.chacall.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;
    private final CarService carService;

    /* 임시 로그인 */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody @Valid Map<String, String> request) {
        request.forEach((key, value) -> System.out.println(key + ":" + value));
        return ResponseEntity.ok().build();
    }

    /* 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequest request) {
        userService.join(request.getPhoneNumber(), request.getPassword());

        URI targetLocation = URI.create("/");
        return ResponseEntity.created(targetLocation).build();
    }

    /* 차량 목록 조회 */
    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<CarResponse>> readCars(@PathVariable Long userId) {
        List<CarResponse> response = carService.findCarsByUserId(userId).stream()
                .map(CarResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    /* 차량 등록 */
    @PostMapping("/{userId}/cars")
    public ResponseEntity<Map<String, Long>> registerCar(@PathVariable Long userId, @RequestBody @Valid CarRegisterRequest request) {
        Long carId = carService.registerCar(userId, request.getNickname(), request.getMessage());
        URI targetLocation = URI.create("/api/cars/" + carId);

        return ResponseEntity.created(targetLocation)
                .body(Map.of("carId", carId));
    }
}
