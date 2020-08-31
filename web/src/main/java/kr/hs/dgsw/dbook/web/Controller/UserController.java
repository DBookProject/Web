package kr.hs.dgsw.dbook.web.Controller;

import kr.hs.dgsw.dbook.web.Domain.User;
import kr.hs.dgsw.dbook.web.Service.UserService;
import kr.hs.dgsw.dbook.web.json.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Response> SignUp(@RequestBody User user) {
        return new ResponseEntity<>(userService.signup(user), HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<Response> checkUser(@PathVariable String email) {
        return new ResponseEntity<>(userService.checkUser(email), HttpStatus.OK);
    }

}
