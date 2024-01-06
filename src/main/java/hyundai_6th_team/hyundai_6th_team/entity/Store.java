package hyundai_6th_team.hyundai_6th_team.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "restarea_id")
    @ManyToOne
    private RestArea restArea;


    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(updatable = true, nullable = false)
    private LocalDateTime createdAt;


    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;



}
