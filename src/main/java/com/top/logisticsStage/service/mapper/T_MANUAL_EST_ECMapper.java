package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_MANUAL_EST_EC;
import com.top.logisticsStage.service.dto.T_MANUAL_EST_ECDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_MANUAL_EST_ECMapper extends EntityMapper<T_MANUAL_EST_ECDTO, T_MANUAL_EST_EC> {

    T_MANUAL_EST_ECDTO toDto(T_MANUAL_EST_EC t_manual_est_ec);

    T_MANUAL_EST_EC toEntity(T_MANUAL_EST_ECDTO t_manual_est_ecDTO);

    default T_MANUAL_EST_EC fromId(Long id) {
        if (id == null) {
            return null;
        }
        T_MANUAL_EST_EC t_manual_est_ec = new T_MANUAL_EST_EC();
        t_manual_est_ec.setId(id);
        return t_manual_est_ec;
    }
}
