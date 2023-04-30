package com.school.project.ecommercebackend.api.controller.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.project.ecommercebackend.api.model.PasswordResetBody;
import com.school.project.ecommercebackend.exception.EmailFailureException;
import com.school.project.ecommercebackend.exception.EmailNotFoundException;
import com.school.project.ecommercebackend.exception.UserAlreadyExistsException;
import com.school.project.ecommercebackend.exception.UserNotVerifiedException;
import com.school.project.ecommercebackend.api.model.LoginBody;
import com.school.project.ecommercebackend.api.model.LoginResponse;
import com.school.project.ecommercebackend.api.model.RegistrationBody;
import com.school.project.ecommercebackend.model.LocalUser;
import com.school.project.ecommercebackend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    /** The user service. */
    private UserService userService;

    /**
     * Spring injected constructor.
     * @param userService
     */
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Post mapping to verify the email of an account using the emailed token.
     * @param token The token emailed for verification. This is not the same as a
     *              authentication JWT.
     * @return 200 if successful. 409 if failure.
     */
    @PostMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam String token) {
        System.out.println(token);
        if (userService.verifyUser(token)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Post Mapping to handle registering users.
     * @param registrationBody The registration information.
     * @return Response to front end.
     */
    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    /**
     * Post Mapping to handle user logins to provide authentication token.
     * @param loginBody The login information.
     * @return The authentication token if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String jwt = null;
        try {
            jwt = userService.loginUser(loginBody);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println("loginBody = " + mapper.writeValueAsString(loginBody));
        } catch (UserNotVerifiedException ex) {
            LoginResponse response = new LoginResponse();
            response.setSuccess(false);
            String reason = "USER_NOT_VERIFIED";
            if (ex.isNewEmailSent()) {
                reason += "_EMAIL_RESENT";
            }
            response.setFailureReason(reason);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (EmailFailureException | JsonProcessingException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (jwt == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            LoginResponse response = new LoginResponse();
            response.setJwt(jwt);
            response.setSuccess(true);
            return ResponseEntity.ok(response);
        }
    }

    /**
     * Gets the profile of the currently logged-in user and returns it.
     * @param user The authentication principal object.
     * @return The user profile.
     */
    @GetMapping("/me")
    public LocalUser getLoggedInUserProfile(@AuthenticationPrincipal LocalUser user) throws JsonProcessingException {
        return user;
    }

    /**
     * Sends an email to the user with a link to reset their password.
     * @param email The email to reset.
     * @return Ok if sent, bad request if email not found.
     */
    @PostMapping("/forgot")
    public ResponseEntity forgotPassword(@RequestParam String email) {
        try {
            userService.forgotPassword(email);
            return ResponseEntity.ok().build();
        } catch (EmailNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (EmailFailureException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Resets the users password with the given token and password.
     * @param body The information for the password reset.
     * @return Okay if password was set.
     */
    @PostMapping("/reset")
    public ResponseEntity resetPassword(@Valid @RequestBody PasswordResetBody body) {
        userService.resetPassword(body);
        return ResponseEntity.ok().build();
    }

}