package com.chacall.chacall.facade;

import com.chacall.chacall.domain.Car;
import com.chacall.chacall.domain.Contact;
import com.chacall.chacall.domain.ContactStatus;
import com.chacall.chacall.domain.User;
import com.chacall.chacall.dto.response.ContactResponse;
import com.chacall.chacall.dto.response.QRContactsResponse;
import com.chacall.chacall.fake.repository.FakeCarRepository;
import com.chacall.chacall.fake.repository.FakeContactRepository;
import com.chacall.chacall.fake.repository.FakeQRRepository;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import com.chacall.chacall.service.CarService;
import com.chacall.chacall.service.ContactService;
import com.chacall.chacall.service.QRService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class QRScanFacadeTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();

    private final ContactService contactService = new ContactService(contactRepository, carRepository);
    private final QRService qrService = new QRService(qrRepository);

    private final QRScanFacade qrScanFacade = new QRScanFacade(qrService, contactService);

    // 연락처 상태가 unavailable 일 때, 사용자 계정이 아닌 다른 곳에서는 못보게끔 하는 테스트도 필요할듯.

    @Test
    @DisplayName("QR 코드의 시리얼 번호를 통해 차량에 등록된 연락 가능한 연락처 목록을 조회한다.")
    void getAvailableContactsByQRSerialNo() {
        User user = createTestUser();
        Car car = createTestCar(user);

        int count = 5;
        for (int i = 0; i < count; i++) {
            String phoneNumber = "0101234567" + i;
            String name = "testName" + i;
            contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);
        }

        String serialNo = car.getQr().getSerialNo();
        QRContactsResponse response = qrScanFacade.findContactsBySerialNo(serialNo);

        List<ContactResponse> contacts = response.getContacts();

        assertThat(contacts.size()).isEqualTo(count + 1);
        for (ContactResponse contact : contacts) {
            assertThat(contact.getStatus()).isEqualTo(ContactStatus.AVAILABLE);
        }
    }

    private User createTestUser() {
        User user = new User("01012121212", "pwd1234!");
        return userRepository.save(user);
    }

    private Car createTestCar(User user) {
        CarService carService = new CarService(carRepository, userRepository, qrService, contactService);
        Long carId = carService.registerCar(user.getId(), "carNickname", "carMessage");
        return carService.findCar(carId);
    }
}
