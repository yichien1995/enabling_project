package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.repository.account.UsersRepository;
import tw.appworks.school.yichien.enabling.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(Users user) {
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(newUser);
    }

    public Map<String, Object> emailValidationError(String email) {
        Map<String, Object> result = new HashMap<>();
        // check email
        boolean checkEmailExists = usersRepository.existsByEmail(email);
        boolean checkEmailValidation = checkEmailRegex(email);

        if (!checkEmailValidation) {
            result.put("error", "信箱格式錯誤");
            return result;
        }

        if (checkEmailExists) {
            result.put("error", "該信箱已註冊過");
            return result;
        }
        return null;
    }

    @Override
    public Map<String, Object> loginError(Users user) {
        boolean checkEmailExists = usersRepository.existsByEmail(user.getEmail());
        boolean permission = checkPassword(user.getEmail(), user.getPassword());
        if (!permission || !checkEmailExists) {
            Map<String, Object> result = new HashMap<>();
            result.put("error", "信箱或密碼錯誤");
            return result;
        }
        return null;
    }

    private boolean checkEmailRegex(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean checkPassword(String email, String password) {
        Users user = usersRepository.findUsersByEmail(email);
        if (user == null) {
            return false;
        }
        String encodePassword = user.getPassword();
        return passwordEncoder.matches(password, encodePassword);
    }
}
