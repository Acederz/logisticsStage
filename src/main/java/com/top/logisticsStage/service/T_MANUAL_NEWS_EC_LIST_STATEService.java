package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_MANUAL_EST_EC;
import com.top.logisticsStage.domain.T_MANUAL_NEWS_EC_LIST_STATE;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.repository.T_MANUAL_NEWS_EC_LIST_STATERepository;
import com.top.logisticsStage.service.dto.T_MANUAL_NEWS_EC_LIST_STATEDTO;
import com.top.logisticsStage.service.mapper.T_MANUAL_NEWS_EC_LIST_STATEMapper;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_NEWS_EC_LIST_STATEQueryVM;
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
public class T_MANUAL_NEWS_EC_LIST_STATEService {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_NEWS_EC_LIST_STATEService.class);

    private final T_MANUAL_NEWS_EC_LIST_STATERepository t_MANUAL_NEWS_EC_LIST_STATERepository;

    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;

    private final T_MANUAL_NEWS_EC_LIST_STATEMapper t_MANUAL_NEWS_EC_LIST_STATEMapper;

    public T_MANUAL_NEWS_EC_LIST_STATEService(T_MANUAL_NEWS_EC_LIST_STATERepository t_MANUAL_NEWS_EC_LIST_STATERepository, T_MANUAL_EST_ECRepository t_manual_est_ecRepository, T_MANUAL_NEWS_EC_LIST_STATEMapper t_MANUAL_NEWS_EC_LIST_STATEMapper) {
        this.t_MANUAL_NEWS_EC_LIST_STATERepository = t_MANUAL_NEWS_EC_LIST_STATERepository;
        t_MANUAL_EST_ECRepository = t_manual_est_ecRepository;
        this.t_MANUAL_NEWS_EC_LIST_STATEMapper = t_MANUAL_NEWS_EC_LIST_STATEMapper;
    }

    public T_MANUAL_NEWS_EC_LIST_STATEDTO create(T_MANUAL_NEWS_EC_LIST_STATEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_MANUAL_NEWS_EC_LIST_STATE entity = t_MANUAL_NEWS_EC_LIST_STATEMapper.toEntity(dto);
        if(t_MANUAL_NEWS_EC_LIST_STATERepository.existsById(dto.getItemCode())) {
            throw new ServiceException("数据已存在！");
        }
        entity = t_MANUAL_NEWS_EC_LIST_STATERepository.save(entity);
        return t_MANUAL_NEWS_EC_LIST_STATEMapper.toDto(entity);
    }

    public T_MANUAL_NEWS_EC_LIST_STATEDTO update(T_MANUAL_NEWS_EC_LIST_STATEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_MANUAL_NEWS_EC_LIST_STATERepository.getOne(dto.getOldItemCode()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_MANUAL_NEWS_EC_LIST_STATE entity = t_MANUAL_NEWS_EC_LIST_STATEMapper.toEntity(dto);
        entity = t_MANUAL_NEWS_EC_LIST_STATERepository.save(entity);
        t_MANUAL_NEWS_EC_LIST_STATERepository.deleteById(dto.getOldItemCode());
        List<T_MANUAL_EST_EC> list1 = t_MANUAL_EST_ECRepository.findAllByItemCode(dto.getOldItemCode());
        list1.forEach(e->e.setItemCode(dto.getItemCode()));
        t_MANUAL_EST_ECRepository.saveAll(list1);
        return t_MANUAL_NEWS_EC_LIST_STATEMapper.toDto(entity);
    }

    public Page<T_MANUAL_NEWS_EC_LIST_STATEDTO> findList(T_MANUAL_NEWS_EC_LIST_STATEQueryVM queryVM, Pageable pageable) {
        Page<T_MANUAL_NEWS_EC_LIST_STATE> list = t_MANUAL_NEWS_EC_LIST_STATERepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_MANUAL_NEWS_EC_LIST_STATEDTO> page = list.map(t_MANUAL_NEWS_EC_LIST_STATEMapper::toDto);
        return page;
    }

    private Specification<T_MANUAL_NEWS_EC_LIST_STATE> buildMultConditional(T_MANUAL_NEWS_EC_LIST_STATEQueryVM queryVM) {
        return new Specification<T_MANUAL_NEWS_EC_LIST_STATE>() {
            @Override
            public Predicate toPredicate(Root<T_MANUAL_NEWS_EC_LIST_STATE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getItemCode())) {
                    Predicate p= cb.like(root.<String>get("itemCode"), "%" + queryVM.getItemCode() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getItemName())) {
                    Predicate p= cb.like(root.<String>get("itemName"), "%" + queryVM.getItemName() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getOnTrace())) {
                    Predicate p= cb.equal(root.<String>get("onTrace"), queryVM.getOnTrace());
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
