package com.snake.model.db.io.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "snake_addresses")
public class AddressEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 50, unique = true)
    private String nickname;

    @Column(length = 30, nullable = false)
    private String addressId;

    @Column(length = 15, nullable = false)
    private String city;

    @Column(length = 15, nullable = false)
    private String country;

    @Column(length = 100, nullable = false)
    private String streetName;

    @Column(length = 7, nullable = false)
    private String postalCode;

    @ManyToOne
    @JoinColumn(name="snake_users")
    private UserEntity userDetails;

}
