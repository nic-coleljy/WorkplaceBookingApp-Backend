package com.collab.g5.demo.security;

import com.collab.g5.demo.exceptions.users.UserNotFoundException;
import com.collab.g5.demo.users.User;
import com.collab.g5.demo.users.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsService userDetailsService;
    private UserServiceImpl userServiceImpl;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, JwtUserDetailsService userDetailsService, UserServiceImpl userServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        User user = new User();
        user.setEmail(authenticationRequest.getUsername());


        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    /**
     * when a user forget password
     *
     * @param email
     * @return the newly added user
     */
    @PostMapping("/authenticate/forget/{email}")
    public void forgetPassword(@PathVariable String email) throws UserNotFoundException {
        User userExists = userServiceImpl.getUserByEmail(email);
        if (userExists == null) {
            throw new UserNotFoundException();
        }
        userServiceImpl.forgetPassword(email);
    }

    @PostMapping("/authenticate/forget/new/{password}")
    public void SetForgetPassword(@PathVariable String password, @Valid @RequestBody User user) throws UserNotFoundException {

        try {
            User userExists = userServiceImpl.getUserByEmail(user.getEmail());
            userServiceImpl.setForgetPassword(user.getEmail(), password);

        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException();
        }

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}