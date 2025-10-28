package com.lpatros.ecommerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "phone_numbers",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"countryCode", "areaCode", "number"}
        )
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String countryCode;

    @Column(nullable = false)
    private String areaCode;

    @Column(nullable = false)
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
