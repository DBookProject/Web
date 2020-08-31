package kr.hs.dgsw.dbook.web.Repository;

import kr.hs.dgsw.dbook.web.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByuEmail(String email);

}
