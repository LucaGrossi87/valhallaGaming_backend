package it.epicode.valhallagaming.controller;

import it.epicode.valhallagaming.dto.userDTO.UserCreateRequest;
import it.epicode.valhallagaming.dto.userDTO.UserDeleteResponse;
import it.epicode.valhallagaming.dto.userDTO.UserEditRequest;
import it.epicode.valhallagaming.dto.userDTO.UserResponse;
import it.epicode.valhallagaming.entity.User;
import it.epicode.valhallagaming.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping ("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        Optional<User> user = userService.findById(id);
        return user.map(this::convertToDTO).orElse(null);
    }

    @PostMapping
    public UserResponse createUser (@RequestBody UserCreateRequest userCreateRequest){
        User user = convertToEntity(userCreateRequest);
        User savedUser = userService.save(user);
        return convertToDTO(savedUser);
    }

    @PutMapping ("/{id}")
    public UserResponse editUser (@PathVariable Long id, @RequestBody UserEditRequest userEditRequest){
        User user = convertToEntity(userEditRequest);
        user.setId(id);
        User updatedUser = userService.save(user);
        return convertToDTO(updatedUser);
    }

    @DeleteMapping ("/{id}")
    public UserDeleteResponse deleteUser (@PathVariable Long id){
        userService.deleteById(id);
        UserDeleteResponse response = new UserDeleteResponse();
        response.setId(id);
        response.setMessage("User eliminato");
        return response;
    }

    private UserResponse convertToDTO (User user){
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setBookingList(user.getBookingList());
        return dto;
    }

    private User convertToEntity (UserCreateRequest dto){
        return new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBookingList());
    }

    private User convertToEntity (UserEditRequest dto){
        return new User(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getBookingList());
    }
}
