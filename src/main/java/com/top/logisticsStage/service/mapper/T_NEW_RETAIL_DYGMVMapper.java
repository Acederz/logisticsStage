package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_NEW_RETAIL_DYGMV;
import com.top.logisticsStage.service.dto.T_NEW_RETAIL_DYGMVDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_NEW_RETAIL_DYGMVMapper extends EntityMapper<T_NEW_RETAIL_DYGMVDTO, T_NEW_RETAIL_DYGMV> {

    T_NEW_RETAIL_DYGMVDTO toDto(T_NEW_RETAIL_DYGMV t_NEW_RETAIL_DYGMV);

    T_NEW_RETAIL_DYGMV toEntity(T_NEW_RETAIL_DYGMVDTO t_NEW_RETAIL_DYGMVDTO);

    default T_NEW_RETAIL_DYGMV fromId(Long id) {
        if (id == null) {
            return null;
        }
        T_NEW_RETAIL_DYGMV t_NEW_RETAIL_DYGMV = new T_NEW_RETAIL_DYGMV();
        t_NEW_RETAIL_DYGMV.setId(id);
        return t_NEW_RETAIL_DYGMV;
    }
}