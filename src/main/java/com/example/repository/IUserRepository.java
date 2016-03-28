package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Krystian on 2016-03-26.
 */
@Repository
public interface IUserRepository extends JpaRepository<User, Long> {


}
