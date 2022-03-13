package auto.mapping.exercise.service;

import auto.mapping.exercise.model.dto.UserLoginDTO;
import auto.mapping.exercise.model.dto.UserRegisterDto;

public interface UserService {
    void registerUser(UserRegisterDto userRegisterDto);

    void loginUser(UserLoginDTO userLoginDTO);

    void logout();

    boolean hasLoggedInUser();

    boolean isAdministrator();
}
