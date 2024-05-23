package tw.appworks.school.yichien.enabling.controller.mainpage.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.response.ErrorResponse;
import tw.appworks.school.yichien.enabling.response.SuccessResponse;
import tw.appworks.school.yichien.enabling.service.UserService;
import tw.appworks.school.yichien.enabling.service.impl.SessionServiceImpl;

@RestController
@RequestMapping("api/1.0/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private SessionServiceImpl sessionService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@ModelAttribute Users user,
                                      HttpServletResponse response) throws JsonProcessingException {
        String emailValidationError = userService.emailValidationError(user.getEmail());
        if (emailValidationError != null) {
            return ResponseEntity.badRequest().body(new ErrorResponse(emailValidationError));
        }
        userService.saveUser(user);
        sessionService.setCookieAndStoreSession(user.getEmail(), response);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Register successfully."));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@ModelAttribute Users user,
                                   HttpServletResponse response) throws JsonProcessingException {

        String loginErrorMsg = userService.loginError(user);
        if (loginErrorMsg != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(loginErrorMsg));
        }
        //set cookie after successful login
        sessionService.setCookieAndStoreSession(user.getEmail(), response);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Login successfully."));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("enabling", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse("Logout successfully."));
    }
}
