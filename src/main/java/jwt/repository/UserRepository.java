package jwt.repository;

import jwt.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
    value = "select *from test.user where test.user.username =:username ")
    User findUserByName(@Param("username") String username);
}
