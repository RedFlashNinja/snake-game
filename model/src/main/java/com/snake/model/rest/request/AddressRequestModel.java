package com.snake.model.rest.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestModel {

	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;

}
