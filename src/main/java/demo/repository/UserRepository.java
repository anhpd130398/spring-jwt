package demo.repository;

import demo.entities.UserBO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserBO, Integer> {

    @Query(value = "select u from UserBO u where u.username = ?1 ")
    UserBO findByUserName(String username);

    @Query(value = "select u from UserBO u where u.refreshToken = ?1 ")
    UserBO findByRefreshToken(String refreshToken);
}
