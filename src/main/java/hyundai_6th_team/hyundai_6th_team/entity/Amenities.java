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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restArea_id")
    private RestArea restArea;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isNursingRoom;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isGasStation;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isLPG;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isElectric;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isTransfer;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isPharmacy;
}

