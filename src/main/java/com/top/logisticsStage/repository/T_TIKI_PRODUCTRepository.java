package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_TIKI_PRODUCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_TIKI_PRODUCTRepository extends JpaRepository<T_TIKI_PRODUCT, String >, JpaSpecificationExecutor<T_TIKI_PRODUCT> {
}

