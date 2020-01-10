package com.snake.model.db.io.repository;

import com.snake.model.db.io.entity.AddressEntity;
import com.snake.model.db.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    AddressEntity findByAddressId(String addressId);
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);
}
