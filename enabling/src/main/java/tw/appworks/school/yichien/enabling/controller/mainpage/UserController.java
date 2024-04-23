package tw.appworks.school.yichien.enabling.controller.mainpage;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.service.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/1.0/user")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/register")
	public ResponseEntity<?> register(@ModelAttribute Users user) {
		Map<String, Object> emailValidationError = userService.emailValidationError(user.getEmail());
		if (emailValidationError != null) {
			return ResponseEntity.badRequest().body(emailValidationError);
		}
		userService.saveUser(user);
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Register successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> login(@ModelAttribute Users user) {
		Map<String, Object> loginErrorMsg = userService.loginError(user);
		if (loginErrorMsg != null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(loginErrorMsg);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("success", "Login successfully.");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
