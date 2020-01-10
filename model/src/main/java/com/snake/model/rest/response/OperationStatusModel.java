package com.snake.model.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationStatusModel {

	private String operationResult;
	private String operationName;

}
