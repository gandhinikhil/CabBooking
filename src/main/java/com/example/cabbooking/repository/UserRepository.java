package com.example.cabbooking.repository;
import com.example.cabbooking.entity.User;
import com.example.cabbooking.vo.UserDetailsInVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

}
