package com.snake.model.db.service.impl;

import com.snake.model.db.AddressModel;
import com.snake.model.db.io.entity.AddressEntity;
import com.snake.model.db.io.entity.UserEntity;
import com.snake.model.db.io.repository.AddressRepository;
import com.snake.model.db.io.repository.UserRepository;
import com.snake.model.db.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    @Autowired
    public void setUserRepository (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<AddressModel> getAddresses(String userId) {
        List<AddressModel> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressModel.class));
        }

        return returnValue;
    }

    @Override
    public AddressModel getAddress(String addressId) {
        AddressModel addressModel = null;

        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);

        if (addressEntity != null) {
            addressModel = new ModelMapper().map(addressEntity, AddressModel.class);
        }

        return addressModel;
    }

}
