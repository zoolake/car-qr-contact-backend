package com.chacall.chacall.facade;

import com.chacall.chacall.domain.*;
import com.chacall.chacall.dto.response.ContactResponse;
import com.chacall.chacall.fake.repository.FakeCarRepository;
import com.chacall.chacall.fake.repository.FakeContactRepository;
import com.chacall.chacall.fake.repository.FakeQRRepository;
import com.chacall.chacall.fake.repository.FakeUserRepository;
import com.chacall.chacall.fake.service.FakePasswordEncoder;
import com.chacall.chacall.service.CarService;
import com.chacall.chacall.service.ContactService;
import com.chacall.chacall.service.QRService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class QRScanFacadeTest {

    private final FakeContactRepository contactRepository = new FakeContactRepository();
    private final FakeCarRepository carRepository = new FakeCarRepository();
    private final FakeQRRepository qrRepository = new FakeQRRepository();
    private final FakeUserRepository userRepository = new FakeUserRepository();

    private final ContactService contactService = new ContactService(contactRepository, carRepository);
    private final QRService qrService = new QRService(qrRepository);

    private final QRScanFacade qrScanFacade = new QRScanFacade(qrService, contactService);

    @Test
    @DisplayName("QR 코드의 시리얼 번호를 통해 차량에 등록된 연락 가능한 연락처 목록을 조회한다.")
    void findAvailableContactsByQRSerialNo() {
        User user = createTestUser();
        Car car = createTestCar(user);

        int count = 5;
        for (int i = 0; i < count; i++) {
            String phoneNumber = "0101234567" + i;
            String name = "testName" + i;
            Long contactId = contactService.registerSubContact(user.getId(), car.getId(), phoneNumber, name);

            // 0,2,4 -> unavailable
            if (i % 2 == 0) {
                contactService.updateContactInfo(contactId, phoneNumber, name, ContactStatus.UNAVAILABLE);
            }
        }

        String serialNo = car.getQr().getSerialNo();
        List<ContactResponse> availableContacts = qrScanFacade.findAvailableContactsBySerialNo(serialNo).getContacts();

        assertThat(availableContacts.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("메인 연락처가 연락 불가능 상태여도 조회되어야 한다.")
    void findMainContactWhenMainContactStatusUnavailable() {
        User user = createTestUser();
        Car car = createTestCar(user);

        Contact mainContact = contactService.findContactsByCarId(car.getId()).get(0);
        mainContact.changeStatus(ContactStatus.UNAVAILABLE);

        List<ContactResponse> contacts = qrScanFacade.findAvailableContactsBySerialNo(car.getQr().getSerialNo()).getContacts();
        assertThat(contacts.get(0).getContactId()).isEqualTo(mainContact.getId());
    }

    private User createTestUser() {
        User user = new User("01012121212", Password.fromRaw("pwd1234!", new FakePasswordEncoder()));
        return userRepository.save(user);
    }

    private Car createTestCar(User user) {
        CarService carService = new CarService(carRepository, userRepository, contactRepository, qrRepository, qrService, contactService);
        Long carId = carService.registerCar(user.getId(), "carNickname", "carMessage");
        return carService.findCar(carId);
    }
}
