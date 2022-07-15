package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_MANUAL_NEWS_EC_LIST_STATE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface T_MANUAL_NEWS_EC_LIST_STATERepository extends JpaRepository<T_MANUAL_NEWS_EC_LIST_STATE, String >, JpaSpecificationExecutor<T_MANUAL_NEWS_EC_LIST_STATE> {

    Integer deleteByItemCodeIn(List<String> itemcodes);

    Integer countByItemCodeIn(List<String> itemcodes);

    Boolean existsByItemCode(String itemcode);
}
