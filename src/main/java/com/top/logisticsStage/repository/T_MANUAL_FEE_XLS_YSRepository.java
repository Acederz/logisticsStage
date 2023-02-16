package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_YS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_MANUAL_FEE_XLS_YSRepository extends JpaRepository<T_MANUAL_FEE_XLS_YS, Long >, JpaSpecificationExecutor<T_MANUAL_FEE_XLS_YS> {

}
