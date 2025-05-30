package br.com.store.packing.service;

import br.com.store.packing.controller.dto.LoginDTO;
import br.com.store.packing.controller.dto.RecoveryTokenDTO;
import br.com.store.packing.domain.repository.UserRepository;
import br.com.store.packing.config.SecurityConfiguration;
import br.com.store.packing.security.UserDetailsImpl;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;

    public RecoveryTokenDTO authenticateUser(LoginDTO loginUserDto) {
        val user = userRepository.findByEmail(loginUserDto.email()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!securityConfiguration.passwordEncoder().matches(loginUserDto.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        val usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        val authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        val userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryTokenDTO(jwtTokenService.generateToken(userDetails));
    }
}
