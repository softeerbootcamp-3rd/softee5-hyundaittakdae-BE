package hyundai_6th_team.hyundai_6th_team.entity;

import hyundai_6th_team.hyundai_6th_team.entity.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Amenities extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isNursingRoom;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isGasStation;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isLPG;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isElectric; //전기차충전소

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isTransfer; //버스환승가능

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isPharmacy;
}

