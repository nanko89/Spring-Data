package auto.mapping.exercise.service.impl;

import auto.mapping.exercise.model.dto.GameOwnedDTO;
import auto.mapping.exercise.model.dto.UserLoginDTO;
import auto.mapping.exercise.model.dto.UserRegisterDto;
import auto.mapping.exercise.model.entity.Game;
import auto.mapping.exercise.model.entity.User;
import auto.mapping.exercise.repository.UserRepository;
import auto.mapping.exercise.service.UserService;
import auto.mapping.exercise.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private User loggedInUser;



    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if (!userRegisterDto.getPassword().equals(userRegisterDto.getConfirmPassword())){
            System.out.println("Wrong confirm password!");
            return;
        }

        Set<ConstraintViolation<UserRegisterDto>> violations =
                validationUtil.violation(userRegisterDto);

        if (!violations.isEmpty()){
            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = modelMapper.map(userRegisterDto, User.class);

        if (userRepository.count() == 0){
            user.setAdmin(true);
        }else {
            user.setAdmin(false);
        }

        userRepository.save(user);

    }

    @Override
    public void loginUser(UserLoginDTO userLoginDTO) {
        Set<ConstraintViolation<UserLoginDTO>> violations =
                validationUtil.violation(userLoginDTO);
        if (!violations.isEmpty()){
            System.out.println("Incorrect username / password");

            violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .forEach(System.out::println);
            return;
        }

        User user = userRepository
                .findByEmailAndPassword(userLoginDTO.getEmail(), userLoginDTO.getPassword())
                .orElse(null);

        if (user == null){
            System.out.println("Incorrect username / password");
            return;
        }

        if (loggedInUser == null){
            loggedInUser = user;
            System.out.println("Successfully logged in " + user.getFullName());
        }
    }

    @Override
    public void logout() {
        if (loggedInUser == null){
            System.out.println("Cannot log out. No user was logged in.Cannot log out. No user was logged in.");
        }
        loggedInUser = null;
    }

    @Override
    public boolean hasLoggedInUser() {
        return loggedInUser != null;
    }

    @Override
    public boolean isAdministrator() {
        return loggedInUser.getAdmin();
    }

    @Override
    public void ownedGame() {

        Set<Game> games = loggedInUser.getGames();

//        List<Set<Game>> games = userRepository
//                .findAll()
//                .stream()
//                .map(User::getGames)
//                .collect(Collectors.toList());
        if (games.isEmpty()){
            System.out.println("This user have no games");
        }
        games.stream().map(g->modelMapper.map(g, GameOwnedDTO.class))
                .forEach(System.out::println);
    }
}
