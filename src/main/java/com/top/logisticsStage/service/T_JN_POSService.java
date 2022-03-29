package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.T_JN_POS;
import com.top.logisticsStage.repository.T_JN_POSRepository;
import com.top.logisticsStage.service.dto.T_JN_POSDTO;
import com.top.logisticsStage.service.mapper.T_JN_POSMapper;
import com.top.logisticsStage.web.rest.vm.T_JN_POSQueryVM;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class T_JN_POSService {

    private final T_JN_POSRepository t_JN_POSRepository;

    private final T_JN_POSMapper t_JN_POSMapper;

    public T_JN_POSService(T_JN_POSRepository t_JN_POSRepository, T_JN_POSMapper t_JN_POSMapper) {
        this.t_JN_POSRepository = t_JN_POSRepository;
        this.t_JN_POSMapper = t_JN_POSMapper;
    }

    public T_JN_POSDTO create(T_JN_POSDTO dto) {
        T_JN_POS entity = t_JN_POSMapper.toEntity(dto);
        T_JN_POS t = t_JN_POSRepository.findById(entity.getBarCode()).get();
        if(t!=null) {
            throw new ServiceException("条码已重复！");
        }
        entity = t_JN_POSRepository.save(entity);
        return t_JN_POSMapper.toDto(entity);
    }

    public T_JN_POSDTO update(T_JN_POSDTO dto) {
        T_JN_POS entity = t_JN_POSMapper.toEntity(dto);
        if(!t_JN_POSRepository.existsById(entity.getBarCode())) {
            throw new ServiceException("条码不存在，请转新增！");
        }
        entity = t_JN_POSRepository.save(entity);
        return t_JN_POSMapper.toDto(entity);
    }



}
