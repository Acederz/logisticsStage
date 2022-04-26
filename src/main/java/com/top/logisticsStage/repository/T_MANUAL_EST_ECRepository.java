package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_MANUAL_EST_EC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface T_MANUAL_EST_ECRepository extends JpaRepository<T_MANUAL_EST_EC, Long >, JpaSpecificationExecutor<T_MANUAL_EST_EC> {

    Boolean existsAllByItemCodeAndYearAndMonth(String itemCode, BigDecimal year, BigDecimal month);

    List<T_MANUAL_EST_EC> findAllByItemCode(String itemCode);
}
