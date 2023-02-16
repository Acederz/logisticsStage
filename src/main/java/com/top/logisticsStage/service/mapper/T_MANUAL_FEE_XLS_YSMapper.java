package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_YS;
import com.top.logisticsStage.service.dto.T_MANUAL_FEE_XLS_YSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_MANUAL_FEE_XLS_YSMapper extends EntityMapper<T_MANUAL_FEE_XLS_YSDTO, T_MANUAL_FEE_XLS_YS> {

    T_MANUAL_FEE_XLS_YSDTO toDto(T_MANUAL_FEE_XLS_YS t_MANUAL_FEE_XLS_YSx);

    T_MANUAL_FEE_XLS_YS toEntity(T_MANUAL_FEE_XLS_YSDTO t_MANUAL_FEE_XLS_YSDTO);

    default T_MANUAL_FEE_XLS_YS fromId(Long id) {
        if (id == null) {
            return null;
        }
        T_MANUAL_FEE_XLS_YS t_MANUAL_FEE_XLS_YS = new T_MANUAL_FEE_XLS_YS();
        t_MANUAL_FEE_XLS_YS.setId(id);
        return t_MANUAL_FEE_XLS_YS;
    }
}
