package com.top.logisticsStage.service.mapper;

import com.top.logisticsStage.domain.T_TIKI_PRODUCT;
import com.top.logisticsStage.service.dto.T_TIKI_PRODUCTDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface T_TIKI_PRODUCTMapper extends EntityMapper<T_TIKI_PRODUCTDTO, T_TIKI_PRODUCT> {

    T_TIKI_PRODUCTDTO toDto(T_TIKI_PRODUCT t_TIKI_PRODUCT);

    T_TIKI_PRODUCT toEntity(T_TIKI_PRODUCTDTO t_TIKI_PRODUCTDTO);

    default T_TIKI_PRODUCT fromId(String id) {
        if (id == null) {
            return null;
        }
        T_TIKI_PRODUCT t_TIKI_PRODUCT = new T_TIKI_PRODUCT();
        t_TIKI_PRODUCT.setSku(id);
        return t_TIKI_PRODUCT;
    }
}
