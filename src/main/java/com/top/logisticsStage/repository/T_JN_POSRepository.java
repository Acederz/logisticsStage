package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_JN_POS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_JN_POSRepository extends JpaRepository<T_JN_POS, String >, JpaSpecificationExecutor<T_JN_POS> {

}
