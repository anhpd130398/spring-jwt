package jwt.repository;

import jwt.entities.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDao,Long> {
    @Query(nativeQuery = true,
    value = "select *from test.userdao where userdao.usernam = :username")
    UserDao findByUsername(String username);
}
