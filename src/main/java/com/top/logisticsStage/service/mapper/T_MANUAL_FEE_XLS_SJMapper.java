package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_SJ;
import com.top.logisticsStage.service.dto.T_MANUAL_FEE_XLS_SJDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_MANUAL_FEE_XLS_SJMapper extends EntityMapper<T_MANUAL_FEE_XLS_SJDTO, T_MANUAL_FEE_XLS_SJ> {

    T_MANUAL_FEE_XLS_SJDTO toDto(T_MANUAL_FEE_XLS_SJ t_MANUAL_FEE_XLS_SJx);

    T_MANUAL_FEE_XLS_SJ toEntity(T_MANUAL_FEE_XLS_SJDTO t_MANUAL_FEE_XLS_SJDTO);

    default T_MANUAL_FEE_XLS_SJ fromId(Long id) {
        if (id == null) {
            return null;
        }
        T_MANUAL_FEE_XLS_SJ t_MANUAL_FEE_XLS_SJ = new T_MANUAL_FEE_XLS_SJ();
        t_MANUAL_FEE_XLS_SJ.setId(id);
        return t_MANUAL_FEE_XLS_SJ;
    }
}
