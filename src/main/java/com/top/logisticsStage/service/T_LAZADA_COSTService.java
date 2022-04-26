package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_LAZADA_COST;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.repository.T_LAZADA_COSTRepository;
import com.top.logisticsStage.service.dto.T_LAZADA_COSTDTO;
import com.top.logisticsStage.service.mapper.T_LAZADA_COSTMapper;
import com.top.logisticsStage.web.rest.vm.T_LAZADA_COSTQueryVM;
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
public class T_LAZADA_COSTService {

    private final Logger log = LoggerFactory.getLogger(T_LAZADA_COSTService.class);

    private final T_LAZADA_COSTRepository t_LAZADA_COSTRepository;

    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;

    private final T_LAZADA_COSTMapper t_LAZADA_COSTMapper;

    public T_LAZADA_COSTService(T_LAZADA_COSTRepository t_LAZADA_COSTRepository, T_MANUAL_EST_ECRepository t_manual_est_ecRepository, T_LAZADA_COSTMapper t_LAZADA_COSTMapper) {
        this.t_LAZADA_COSTRepository = t_LAZADA_COSTRepository;
        t_MANUAL_EST_ECRepository = t_manual_est_ecRepository;
        this.t_LAZADA_COSTMapper = t_LAZADA_COSTMapper;
    }

    public T_LAZADA_COSTDTO create(T_LAZADA_COSTDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_LAZADA_COST entity = t_LAZADA_COSTMapper.toEntity(dto);
        if(t_LAZADA_COSTRepository.existsById(dto.getCt001())) {
            throw new ServiceException("数据已存在！");
        }
        entity = t_LAZADA_COSTRepository.save(entity);
        return t_LAZADA_COSTMapper.toDto(entity);
    }

    public T_LAZADA_COSTDTO update(T_LAZADA_COSTDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_LAZADA_COSTRepository.getOne(dto.getCt001()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_LAZADA_COST entity = t_LAZADA_COSTMapper.toEntity(dto);
        entity = t_LAZADA_COSTRepository.save(entity);
        return t_LAZADA_COSTMapper.toDto(entity);
    }

    public Page<T_LAZADA_COSTDTO> findList(T_LAZADA_COSTQueryVM queryVM, Pageable pageable) {
        Page<T_LAZADA_COST> list = t_LAZADA_COSTRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_LAZADA_COSTDTO> page = list.map(t_LAZADA_COSTMapper::toDto);
        return page;
    }

    private Specification<T_LAZADA_COST> buildMultConditional(T_LAZADA_COSTQueryVM queryVM) {
        return new Specification<T_LAZADA_COST>() {
            @Override
            public Predicate toPredicate(Root<T_LAZADA_COST> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getCt001())) {
                    Predicate p= cb.like(root.<String>get("ct001"), "%" + queryVM.getCt001() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getCt002())) {
                    Predicate p= cb.like(root.<String>get("ct002"), "%" + queryVM.getCt002() + "%");
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

