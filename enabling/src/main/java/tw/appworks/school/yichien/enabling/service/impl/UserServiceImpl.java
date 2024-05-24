package tw.appworks.school.yichien.enabling.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tw.appworks.school.yichien.enabling.model.account.Users;
import tw.appworks.school.yichien.enabling.repository.account.UsersRepository;
import tw.appworks.school.yichien.enabling.service.UserService;

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

    @Override
    public void saveUser(Users user) {
        Users newUser = new Users();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(newUser);
    }

    @Override
    public String emailValidationError(String email) {
        // check email
        boolean checkEmailExists = usersRepository.existsByEmail(email);
        boolean checkEmailValidation = checkEmailRegex(email);

        if (!checkEmailValidation) {
            return "信箱格式錯誤";
        }

        if (checkEmailExists) {
            return "該信箱已註冊過";
        }
        return null;
    }

    @Override
    public String loginError(Users user) {
        boolean checkEmailExists = usersRepository.existsByEmail(user.getEmail());
        boolean permission = checkPassword(user.getEmail(), user.getPassword());
        if (!permission || !checkEmailExists) {
            return "信箱或密碼錯誤";
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
