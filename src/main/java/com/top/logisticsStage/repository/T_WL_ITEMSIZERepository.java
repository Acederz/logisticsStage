package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_WL_ITEMSIZE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_WL_ITEMSIZERepository extends JpaRepository<T_WL_ITEMSIZE, String >, JpaSpecificationExecutor<T_WL_ITEMSIZE> {

}
