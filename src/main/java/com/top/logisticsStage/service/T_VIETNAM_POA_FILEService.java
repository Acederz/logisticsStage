package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_VIETNAM_POA_FILE;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.repository.T_VIETNAM_POA_FILERepository;
import com.top.logisticsStage.service.dto.T_VIETNAM_POA_FILEDTO;
import com.top.logisticsStage.service.mapper.T_VIETNAM_POA_FILEMapper;
import com.top.logisticsStage.web.rest.vm.T_VIETNAM_POA_FILEQueryVM;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class T_VIETNAM_POA_FILEService {

    private final Logger log = LoggerFactory.getLogger(T_VIETNAM_POA_FILEService.class);

    private final T_VIETNAM_POA_FILERepository t_VIETNAM_POA_FILERepository;

    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;

    private final T_VIETNAM_POA_FILEMapper t_VIETNAM_POA_FILEMapper;

    public T_VIETNAM_POA_FILEService(T_VIETNAM_POA_FILERepository t_VIETNAM_POA_FILERepository, T_MANUAL_EST_ECRepository t_manual_est_ecRepository, T_VIETNAM_POA_FILEMapper t_VIETNAM_POA_FILEMapper) {
        this.t_VIETNAM_POA_FILERepository = t_VIETNAM_POA_FILERepository;
        t_MANUAL_EST_ECRepository = t_manual_est_ecRepository;
        this.t_VIETNAM_POA_FILEMapper = t_VIETNAM_POA_FILEMapper;
    }

    public T_VIETNAM_POA_FILEDTO create(T_VIETNAM_POA_FILEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_VIETNAM_POA_FILE entity = t_VIETNAM_POA_FILEMapper.toEntity(dto);
        if(t_VIETNAM_POA_FILERepository.existsById(dto.getBarCode())) {
            throw new ServiceException("数据已存在！");
        }
        entity = t_VIETNAM_POA_FILERepository.save(entity);
        return t_VIETNAM_POA_FILEMapper.toDto(entity);
    }

    public T_VIETNAM_POA_FILEDTO update(T_VIETNAM_POA_FILEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_VIETNAM_POA_FILERepository.getOne(dto.getBarCode()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_VIETNAM_POA_FILE entity = t_VIETNAM_POA_FILEMapper.toEntity(dto);
        entity = t_VIETNAM_POA_FILERepository.save(entity);
        return t_VIETNAM_POA_FILEMapper.toDto(entity);
    }

    public Page<T_VIETNAM_POA_FILEDTO> findList(T_VIETNAM_POA_FILEQueryVM queryVM, Pageable pageable) {
        Page<T_VIETNAM_POA_FILE> list = t_VIETNAM_POA_FILERepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_VIETNAM_POA_FILEDTO> page = list.map(t_VIETNAM_POA_FILEMapper::toDto);
        return page;
    }

    private Specification<T_VIETNAM_POA_FILE> buildMultConditional(T_VIETNAM_POA_FILEQueryVM queryVM) {
        return new Specification<T_VIETNAM_POA_FILE>() {
            @Override
            public Predicate toPredicate(Root<T_VIETNAM_POA_FILE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getBarCode())) {
                    Predicate p= cb.like(root.<String>get("barCode"), "%" + queryVM.getBarCode() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getItemName())) {
                    Predicate p= cb.like(root.<String>get("itemName"), "%" + queryVM.getItemName() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getEnString())) {
                    Predicate p= cb.like(root.<String>get("enString"), "%" + queryVM.getEnString() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getVnString())) {
                    Predicate p= cb.like(root.<String>get("vnString"), "%" + queryVM.getVnString() + "%");
                    predicates.add(p);
                }
                if (!predicates.isEmpty()) {
                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));
                } else {
                    return null;
                }
            }
        };
    }
}
