package com.snake.model.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressesRest extends RepresentationModel {

	private String addressId;
	private String city;
	private String country;
	private String streetName;
	private String postalCode;
	private String type;

}
