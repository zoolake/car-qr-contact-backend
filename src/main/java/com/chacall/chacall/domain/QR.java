package com.chacall.chacall.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QR {
    public static final int WIDTH = 200;
    public static final int HEIGHT = 200;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qr_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    private String serialNo;
    private String imagePath;

    public QR(Car car, String serialNo, String imagePath) {
        this.car = car;
        this.serialNo = serialNo;
        this.imagePath = imagePath;
        car.setQR(this);
    }

    /* 단위테스트를 위한 생성자 */
    protected QR (Long qrId, Car car, String serialNo, String imagePath) {
        this(car,serialNo, imagePath);
        this.id = qrId;
    }
}
