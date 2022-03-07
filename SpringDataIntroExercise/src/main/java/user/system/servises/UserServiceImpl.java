package user.system.servises;

import org.springframework.stereotype.Service;
import user.system.models.User;
import user.system.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> findAllUsernamesAndEmailsByEmailProvider(String provider) {
        return userRepository.findAllByEmailEndingWith(provider)
                .stream().map(user -> String.format("%s %s", user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer removeAllInActiveUsers(LocalDate localDate) {
        return userRepository.deleteAllByLastTimeLoggedInAfter(localDate);
    }
}
