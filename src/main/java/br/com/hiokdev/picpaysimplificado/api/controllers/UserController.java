package br.com.hiokdev.picpaysimplificado.api.controllers;

import br.com.hiokdev.picpaysimplificado.api.dtos.UserRequestDTO;
import br.com.hiokdev.picpaysimplificado.api.dtos.UserResponseDTO;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO inputDTO) {
    User newUser = UserRequestDTO.toDomainEntity(inputDTO);
    User savedUser = userService.save(newUser);
    return ResponseEntity.status(201).body(UserResponseDTO.toRepresentationModel(savedUser));
  }

  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> list() {
    List<User> users = userService.getAll();
    return ResponseEntity.ok(users.stream().map(UserResponseDTO::toRepresentationModel).toList());
  }

}
