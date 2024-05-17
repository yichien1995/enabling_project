package tw.appworks.school.yichien.enabling.service;

import tw.appworks.school.yichien.enabling.model.account.Users;

import java.util.Map;

public interface UserService {

    void saveUser(Users users);

    Map<String, Object> emailValidationError(String email);

    Map<String, Object> loginError(Users user);
}
