package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User, String > , JpaSpecificationExecutor<User> {
    List<User> findAllByAccount(String account);

    User findFirstByAccount(String account);

    void deleteByAccount(String account);
}
