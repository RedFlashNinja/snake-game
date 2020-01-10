package com.snake.model.db.service;

import com.snake.model.db.AddressModel;

import java.util.List;

public interface AddressService {
	List<AddressModel> getAddresses(String userId);
    AddressModel getAddress(String addressId);
}
