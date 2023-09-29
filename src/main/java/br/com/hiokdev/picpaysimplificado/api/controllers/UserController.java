package br.com.hiokdev.picpaysimplificado.api.controllers;

import br.com.hiokdev.picpaysimplificado.api.dtos.ExceptionDTO;
import br.com.hiokdev.picpaysimplificado.api.dtos.UserRequestDTO;
import br.com.hiokdev.picpaysimplificado.api.dtos.UserResponseDTO;
import br.com.hiokdev.picpaysimplificado.domain.models.User;
import br.com.hiokdev.picpaysimplificado.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Users")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @Operation(summary = "Register new users", responses = {
    @ApiResponse(responseCode = "201"),
    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ExceptionDTO.class))),
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionDTO.class)))
  })
  @PostMapping
  public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO inputDTO) {
    User newUser = UserRequestDTO.toDomainEntity(inputDTO);
    User savedUser = userService.create(newUser);
    return ResponseEntity.status(201).body(UserResponseDTO.toRepresentationModel(savedUser));
  }

  @Operation(summary = "Lista all users", responses = {
    @ApiResponse(responseCode = "200"),
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ExceptionDTO.class)))
  })
  @GetMapping
  public ResponseEntity<List<UserResponseDTO>> list() {
    List<User> users = userService.getAll();
    return ResponseEntity.ok(users.stream().map(UserResponseDTO::toRepresentationModel).toList());
  }

}
