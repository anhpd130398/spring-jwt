package demo.repository;

import demo.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
    @Query(value = "select r from Role r where r.name = ?1")
    Role findRoleByCode(String name);
}
