package user.system.servises;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<String> findAllUsernamesAndEmailsByEmailProvider(String provider);

    Integer removeAllInActiveUsers(LocalDate localDate);
}
