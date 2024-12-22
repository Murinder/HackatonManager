package com.example.kotllegacy.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "hackatons")
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Hackaton {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hackaton_id")
    private Long hackatonId;

    @Column(columnDefinition = "jsonb")
    private String tags;

    @Column(name = "type_code")
    private Long typeCode;

    @Column(name = "type_description")
    private String typeDescription;

    @Column(nullable = false)
    private String title;

    private String description;

    private String address;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "registration_deadline")
    private LocalDateTime registrationDeadline;

    @Column(name = "end_date ", nullable = false)
    private LocalDateTime endDate;

    private String url;
}
