package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_NEW_RETAIL_DYGMV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

public interface T_NEW_RETAIL_DYGMVRepository extends JpaRepository<T_NEW_RETAIL_DYGMV, Long >, JpaSpecificationExecutor<T_NEW_RETAIL_DYGMV> {

    T_NEW_RETAIL_DYGMV findFirstByDateAndAccountName(LocalDate date, String accountName);
}
