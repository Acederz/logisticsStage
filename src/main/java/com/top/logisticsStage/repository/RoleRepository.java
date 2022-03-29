package com.top.logisticsStage.repository;


import com.top.logisticsStage.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface RoleRepository extends JpaRepository<Role, String > {
    @Query(value = "select new com.top.logistics.model.Role(R.ROLE,R.DESCRIBE) from T_USER_ROLE U LEFT JOIN T_ROLE R ON U.ROLE=R.ROLE  where U.ACCOUNT=?1", nativeQuery = true)
    List<Role> findRolesByAccount(String account);
}
