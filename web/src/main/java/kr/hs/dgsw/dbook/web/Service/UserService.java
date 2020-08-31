package kr.hs.dgsw.dbook.web.Service;

import kr.hs.dgsw.dbook.web.Domain.User;
import kr.hs.dgsw.dbook.web.json.Response;

public interface UserService {

    Response signup(User user);

    Response checkUser(String email);

    User findUser(String email);

}
