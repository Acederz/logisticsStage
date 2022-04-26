package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_MANUAL_NEWS_EC_LIST_STATE;
import com.top.logisticsStage.service.dto.T_MANUAL_NEWS_EC_LIST_STATEDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_MANUAL_NEWS_EC_LIST_STATEMapper extends EntityMapper<T_MANUAL_NEWS_EC_LIST_STATEDTO, T_MANUAL_NEWS_EC_LIST_STATE> {

    T_MANUAL_NEWS_EC_LIST_STATEDTO toDto(T_MANUAL_NEWS_EC_LIST_STATE t_MANUAL_NEWS_EC_LIST_STATE);

    T_MANUAL_NEWS_EC_LIST_STATE toEntity(T_MANUAL_NEWS_EC_LIST_STATEDTO t_MANUAL_NEWS_EC_LIST_STATEDTO);

    default T_MANUAL_NEWS_EC_LIST_STATE fromId(String id) {
        if (id == null) {
            return null;
        }
        T_MANUAL_NEWS_EC_LIST_STATE t_MANUAL_NEWS_EC_LIST_STATE = new T_MANUAL_NEWS_EC_LIST_STATE();
        t_MANUAL_NEWS_EC_LIST_STATE.setItemCode(id);
        return t_MANUAL_NEWS_EC_LIST_STATE;
    }
}
