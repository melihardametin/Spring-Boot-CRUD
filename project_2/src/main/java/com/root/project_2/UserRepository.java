

package com.root.project_2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.root.project_2.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByName(String name);

  User findFirstByUsername(String username);

  User findFirstByToken(String token);

  @Modifying
  @Query("delete from User u where u.username = ?1")
  void deleteByUsername(String username);
}