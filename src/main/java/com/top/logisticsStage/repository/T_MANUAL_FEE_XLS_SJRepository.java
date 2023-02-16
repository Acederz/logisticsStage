package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_SJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_MANUAL_FEE_XLS_SJRepository extends JpaRepository<T_MANUAL_FEE_XLS_SJ, Long >, JpaSpecificationExecutor<T_MANUAL_FEE_XLS_SJ> {

}
