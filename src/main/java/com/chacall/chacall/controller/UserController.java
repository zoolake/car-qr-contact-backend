package com.chacall.chacall.controller;

import com.chacall.chacall.auth.SessionUser;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.dto.request.CarRegisterRequest;
import com.chacall.chacall.dto.request.UserLoginRequest;
import com.chacall.chacall.dto.request.UserSignupRequest;
import com.chacall.chacall.dto.response.CarResponse;
import com.chacall.chacall.dto.response.UserLoginResponse;
import com.chacall.chacall.service.CarService;
import com.chacall.chacall.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest request, HttpServletRequest servletRequest) {
        User user = userService.login(request.getPhoneNumber(), request.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        HttpSession session = servletRequest.getSession();
        session.setAttribute("loginUser", SessionUser.from(user));

        return ResponseEntity.ok(UserLoginResponse.from(user));
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
