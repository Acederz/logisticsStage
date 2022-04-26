package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_LAZADA_COST;
import com.top.logisticsStage.service.dto.T_LAZADA_COSTDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_LAZADA_COSTMapper extends EntityMapper<T_LAZADA_COSTDTO, T_LAZADA_COST> {

    T_LAZADA_COSTDTO toDto(T_LAZADA_COST t_LAZADA_COST);

    T_LAZADA_COST toEntity(T_LAZADA_COSTDTO t_LAZADA_COSTDTO);

    default T_LAZADA_COST fromId(String id) {
        if (id == null) {
            return null;
        }
        T_LAZADA_COST t_LAZADA_COST = new T_LAZADA_COST();
        t_LAZADA_COST.setCt001(id);
        return t_LAZADA_COST;
    }
}
