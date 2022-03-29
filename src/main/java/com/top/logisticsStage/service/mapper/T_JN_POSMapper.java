package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_JN_POS;
import com.top.logisticsStage.service.dto.T_JN_POSDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_JN_POSMapper extends EntityMapper<T_JN_POSDTO, T_JN_POS> {

    T_JN_POSDTO toDto(T_JN_POS t_JN_POSx);

    T_JN_POS toEntity(T_JN_POSDTO t_JN_POSDTO);

    default T_JN_POS fromId(String id) {
        if (id == null) {
            return null;
        }
        T_JN_POS t_JN_POS = new T_JN_POS();
        t_JN_POS.setBarCode(id);
        return t_JN_POS;
    }
}