package com.top.logisticsStage.repository;

import com.top.logisticsStage.domain.T_VIETNAM_POA_FILE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface T_VIETNAM_POA_FILERepository extends JpaRepository<T_VIETNAM_POA_FILE, String >, JpaSpecificationExecutor<T_VIETNAM_POA_FILE> {
}

