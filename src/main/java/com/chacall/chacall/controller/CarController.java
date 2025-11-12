package com.chacall.chacall.controller;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.dto.request.CarUpdateRequest;
import com.chacall.chacall.dto.request.ContactRegisterRequest;
import com.chacall.chacall.dto.response.CarInfoResponse;
import com.chacall.chacall.dto.response.CarUpdateResponse;
import com.chacall.chacall.dto.response.ContactResponse;
import com.chacall.chacall.service.CarService;
import com.chacall.chacall.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/cars")
public class CarController {

    private final CarService carService;
    private final ContactService contactService;

    /* 차량 정보 조회 */
    @GetMapping("/{carId}")
    public ResponseEntity<CarInfoResponse> readCarInfo(@PathVariable Long carId) {
        Car car = carService.findCar(carId);
        return ResponseEntity.ok(CarInfoResponse.from(car));
    }

    /* 차량 정보 수정 */
    @PatchMapping("/{carId}")
    public ResponseEntity<CarUpdateResponse> updateCar(@PathVariable Long carId, @RequestBody @Valid CarUpdateRequest request) {
        Car car = carService.updateCarInfo(carId, request.getNickname(), request.getMessage());
        return ResponseEntity.ok(CarUpdateResponse.from(car));
    }

    /* 연락처 목록 조회 */
    @GetMapping("/{carId}/contacts")
    public ResponseEntity<List<ContactResponse>> readContacts(@PathVariable Long carId) {
        List<ContactResponse> response = contactService.findContactsByCarId(carId).stream()
                .map(ContactResponse::from)
                .toList();

        return ResponseEntity.ok(response);
    }

    /* 연락처 등록 */
    @PostMapping("/{carId}/contacts")
    public ResponseEntity<Void> registerContact(@PathVariable Long carId, @RequestBody @Valid ContactRegisterRequest request) {
        contactService.registerContact(carId, request.getPhoneNumber(), request.getName());

        URI targetLocation = URI.create("/api/cars/" + carId + "/contacts");
        return ResponseEntity.created(targetLocation).build();
    }
}
