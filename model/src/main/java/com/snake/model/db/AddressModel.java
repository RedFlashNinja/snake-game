package com.snake.model.db;

import com.snake.model.db.io.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressModel {

    private long id;
    private String userId;
    private String nickname;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private UserEntity userDetails;

}
