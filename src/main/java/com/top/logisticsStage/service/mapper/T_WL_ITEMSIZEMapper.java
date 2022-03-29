package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_WL_ITEMSIZE;
import com.top.logisticsStage.service.dto.T_WL_ITEMSIZEDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_WL_ITEMSIZEMapper extends EntityMapper<T_WL_ITEMSIZEDTO, T_WL_ITEMSIZE> {

    T_WL_ITEMSIZEDTO toDto(T_WL_ITEMSIZE t_WL_ITEMSIZEx);
    
    T_WL_ITEMSIZE toEntity(T_WL_ITEMSIZEDTO t_WL_ITEMSIZEDTO);

    default T_WL_ITEMSIZE fromId(String id) {
        if (id == null) {
            return null;
        }
        T_WL_ITEMSIZE t_WL_ITEMSIZE = new T_WL_ITEMSIZE();
        t_WL_ITEMSIZE.setBarCode(id);
        return t_WL_ITEMSIZE;
    }
}
