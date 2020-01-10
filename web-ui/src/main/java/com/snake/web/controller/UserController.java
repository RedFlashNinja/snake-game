package com.snake.web.controller;

import com.snake.model.db.AddressModel;
import com.snake.model.db.UserModel;
import com.snake.model.db.service.AddressService;
import com.snake.model.db.service.UserService;
import com.snake.model.rest.request.PasswordResetModel;
import com.snake.model.rest.request.PasswordResetRequestModel;
import com.snake.model.rest.request.UserDetailsRequestModel;
import com.snake.model.rest.response.AddressesRest;
import com.snake.model.rest.response.OperationStatusModel;
import com.snake.model.rest.response.RequestOperationStatus;
import com.snake.model.rest.response.UserRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/users") // http://localhost:8080/users
//@CrossOrigin(origins= {"http://localhost:8083", "http://localhost:8084"})
public class UserController {

    private UserService userService;
    private AddressService addressService;
    private AddressService addressesService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setAddressesService(AddressService addressesService) {
        this.addressesService = addressesService;
    }

    @ApiOperation(value = "The Get User Details Web Service Endpoint", notes = "${userController.GetUser.ApiOperation.Notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {

        UserModel userModel = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(userModel, UserRest.class);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        ModelMapper modelMapper = new ModelMapper();
        UserModel userModel = modelMapper.map(userDetails, UserModel.class);

        UserModel createdUser = userService.createUser(userModel);

        return modelMapper.map(createdUser, UserRest.class);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

        UserModel userModel = new ModelMapper().map(userDetails, UserModel.class);
        UserModel updateUser = userService.updateUser(id, userModel);

        return new ModelMapper().map(updateUser, UserRest.class);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "2") int limit) {

        List<UserModel> users = userService.getUsers(page, limit);
        Type listType = new TypeToken<List<UserRest>>() {
        }.getType();

        return new ModelMapper().map(users, listType);
    }

    // http://localhost:8080/snake_game/users/jfhdjeufhdhdj/addressses
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{id}/addresses", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
    public CollectionModel<AddressesRest> getUserAddresses(@PathVariable String id) {
        List<AddressesRest> addressesListRestModel = new ArrayList<>();

        List<AddressModel> addressModel = addressesService.getAddresses(id);

        if (addressModel != null && !addressModel.isEmpty()) {
            Type listType = new TypeToken<List<AddressesRest>>() {
            }.getType();
            addressesListRestModel = new ModelMapper().map(addressModel, listType);

            for (AddressesRest addressRest : addressesListRestModel) {
                Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(id, addressRest.getAddressId()))
                        .withSelfRel();
                addressRest.add(addressLink);

                Link userLink = linkTo(methodOn(UserController.class).getUser(id)).withRel("user");
                addressRest.add(userLink);
            }
        }

        return new CollectionModel<>(addressesListRestModel);
    }

    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}", paramType = "header")})
    @GetMapping(path = "/{userId}/addresses/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/hal+json"})
    public EntityModel<AddressesRest> getUserAddress(@PathVariable String userId, @PathVariable String addressId) {

        AddressModel addressesDto = addressService.getAddress(addressId);

        ModelMapper modelMapper = new ModelMapper();
        Link addressLink = linkTo(methodOn(UserController.class).getUserAddress(userId, addressId)).withSelfRel();
        Link userLink = linkTo(UserController.class).slash(userId).withRel("user");
        Link addressesLink = linkTo(methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");

        AddressesRest addressesRestModel = modelMapper.map(addressesDto, AddressesRest.class);

        addressesRestModel.add(addressLink);
        addressesRestModel.add(userLink);
        addressesRestModel.add(addressesLink);

        return new EntityModel<>(addressesRestModel);
    }

    /*
     * http://localhost:8080/snake_game/users/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {

        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());

        boolean isVerified = userService.verifyEmailToken(token);

        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }

        return returnValue;
    }

    /*
     * http://localhost:8080/snake_game/users/password-reset-request
     * */
    @PostMapping(path = "/password-reset-request", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();

        boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

        operationStatusModel.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return operationStatusModel;
    }


    @PostMapping(path = "/password-reset", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();

        boolean operationResult = userService.resetPassword(
                passwordResetModel.getToken(),
                passwordResetModel.getPassword());

        operationStatusModel.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        operationStatusModel.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            operationStatusModel.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return operationStatusModel;
    }

}
