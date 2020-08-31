package kr.hs.dgsw.dbook.web.Service;

import kr.hs.dgsw.dbook.web.Domain.User;
import kr.hs.dgsw.dbook.web.Exception.UserException;
import kr.hs.dgsw.dbook.web.Repository.UserRepository;
import kr.hs.dgsw.dbook.web.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Response signup(User user) {
        try {
            String password = user.getPassword();
            String name = user.getName();
            String email = user.getEmail();

            if(password == null)
                throw new UserException("Requires Password");
            if(name == null)
                throw new UserException("Requires Username");
            if(email == null)
                throw new UserException("Requires Email");
            if(password.length() < 8)
                return new Response(401, "Password Must Be Longer Than 8");
            if(name.length() < 2)
                return new Response(401, "Username Must Be Longer Than 2");
            if(Pattern.matches("^[^0-9,^a-z,^A-Z]*$", password))
                return new Response(401, "Password Can't Have Special Chracters");
            if(Pattern.matches("^[0-9]*$", name))
                return new Response(401, "Username Can't Have Numbers");

            if(!findUser(email).getEmail().equals("Undefined"))
                throw new UserException("User Already Exists");

            user.setPassword(convertSHA256(password));
            userRepository.save(user);

            return new Response(200, "Success signup");
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(400, e.getMessage());
        }
    }

    @Override
    public Response checkUser(String email) {
        User user = findUser(email);

        if(user.getEmail().equals("Undefined"))
            return new Response(200, "There's No Matching User");
        return new Response(200, "User Already Exists");
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByuEmail(email).orElseGet(() -> new User("Undefined"));
    }

    private String convertSHA256(String password) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
