package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_VIETNAM_POA_FILE;
import com.top.logisticsStage.service.dto.T_VIETNAM_POA_FILEDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_VIETNAM_POA_FILEMapper extends EntityMapper<T_VIETNAM_POA_FILEDTO, T_VIETNAM_POA_FILE> {

    T_VIETNAM_POA_FILEDTO toDto(T_VIETNAM_POA_FILE t_VIETNAM_POA_FILE);

    T_VIETNAM_POA_FILE toEntity(T_VIETNAM_POA_FILEDTO t_VIETNAM_POA_FILEDTO);

    default T_VIETNAM_POA_FILE fromId(String id) {
        if (id == null) {
            return null;
        }
        T_VIETNAM_POA_FILE t_VIETNAM_POA_FILE = new T_VIETNAM_POA_FILE();
        t_VIETNAM_POA_FILE.setBarCode(id);
        return t_VIETNAM_POA_FILE;
    }
}
