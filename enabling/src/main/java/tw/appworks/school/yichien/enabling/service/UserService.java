package tw.appworks.school.yichien.enabling.service;

import tw.appworks.school.yichien.enabling.model.account.Users;

public interface UserService {

    void saveUser(Users users);

    String emailValidationError(String email);

    String loginError(Users user);
}
