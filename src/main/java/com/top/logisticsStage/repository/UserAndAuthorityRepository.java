package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.UserAndAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UserAndAuthorityRepository extends JpaRepository<UserAndAuthority, String > {
    List<UserAndAuthority> findAllByUserAccount(String account);
}
