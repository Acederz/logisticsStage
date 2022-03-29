package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String > {
    @Query(value = "select new com.top.logistics.model.Authority(A.AUTHORITY,A.URL,A.DESCRIBE) " +
            "from T_AUTHIORITY A LEFT JOIN T_ROLE_AUTHORITY RA ON A.AUTHORITY=RA.AUTHORITY " +
            "LEFT JOIN T_ROLE R ON R.ROLE=RA.ROLE " +
            "LEFT JOIN T_USER_ROLE UR ON R.ROLE=UR.ROLE " +
            " LEFT JOIN T_USER U ON U.ACCOUNT=UR.ACCOUNT  where U.ACCOUNT=?1", nativeQuery = true)
    List<Authority> findAuthoritysByAccount(String account);

    List<Authority> findAuthoritiesByParentIdOrderById(Long id);
}
