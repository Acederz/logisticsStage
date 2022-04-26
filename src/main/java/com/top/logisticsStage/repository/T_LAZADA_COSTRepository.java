package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_LAZADA_COST;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_LAZADA_COSTRepository extends JpaRepository<T_LAZADA_COST, String >, JpaSpecificationExecutor<T_LAZADA_COST> {
}
