package br.com.store.packing.controller;

import br.com.store.packing.controller.dto.LoginDTO;
import br.com.store.packing.controller.dto.RecoveryTokenDTO;
import br.com.store.packing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryTokenDTO> authenticateUser(@RequestBody LoginDTO loginUserDto) {
        RecoveryTokenDTO token = userService.authenticateUser(loginUserDto);
        return ResponseEntity.ok().body(token);
    }
}
