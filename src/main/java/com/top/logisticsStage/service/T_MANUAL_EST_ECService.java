package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_MANUAL_EST_EC;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.service.dto.T_MANUAL_EST_ECDTO;
import com.top.logisticsStage.service.mapper.T_MANUAL_EST_ECMapper;
import com.top.logisticsStage.service.util.CustomCollectors;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_EST_ECQueryVM;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class T_MANUAL_EST_ECService {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_EST_ECService.class);

    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;

    private final T_MANUAL_EST_ECMapper t_MANUAL_EST_ECMapper;

    public T_MANUAL_EST_ECService(T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository, T_MANUAL_EST_ECMapper t_MANUAL_EST_ECMapper) {
        this.t_MANUAL_EST_ECRepository = t_MANUAL_EST_ECRepository;
        this.t_MANUAL_EST_ECMapper = t_MANUAL_EST_ECMapper;
    }

    public T_MANUAL_EST_ECDTO create(T_MANUAL_EST_ECDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_MANUAL_EST_EC entity = t_MANUAL_EST_ECMapper.toEntity(dto);
        if(t_MANUAL_EST_ECRepository.existsAllByItemCodeAndYearAndMonthAndTargetType(entity.getItemCode(), entity.getYear(), entity.getMonth(), entity.getTargetType())) {
            throw new ServiceException("数据已存在！");
        }
        entity = t_MANUAL_EST_ECRepository.save(entity);
        return t_MANUAL_EST_ECMapper.toDto(entity);
    }

    public T_MANUAL_EST_ECDTO update(T_MANUAL_EST_ECDTO dto) {
        log.info("目标导入数据修改");
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_MANUAL_EST_ECRepository.getOne(dto.getId()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_MANUAL_EST_EC entity = t_MANUAL_EST_ECMapper.toEntity(dto);
        if(!t_MANUAL_EST_ECRepository.existsById(entity.getId())) {
            throw new ServiceException("id不存在，请转新增！");
        }
        entity = t_MANUAL_EST_ECRepository.save(entity);
        return t_MANUAL_EST_ECMapper.toDto(entity);
    }

    public Page<T_MANUAL_EST_ECDTO> findList(T_MANUAL_EST_ECQueryVM queryVM, Pageable pageable) {
        Page<T_MANUAL_EST_EC> list = t_MANUAL_EST_ECRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_MANUAL_EST_ECDTO> page = list.map(t_MANUAL_EST_ECMapper::toDto);
        return page;
    }

    public Integer deleteByVm(T_MANUAL_EST_ECQueryVM queryVM) {
        log.info("目标导入删除条件："+queryVM.toString());
        List<T_MANUAL_EST_EC> list =t_MANUAL_EST_ECRepository.findAll(buildMultConditional(queryVM));
        List<Long> ids = list.stream().map(T_MANUAL_EST_EC::getId).collect(Collectors.toList());
        List<List<Long>> numberList = ids.stream().collect(CustomCollectors.groupByNumber(5));
        Integer flg = 0;
        for(int i=0;i<numberList.size();i++) {
            flg +=  t_MANUAL_EST_ECRepository.deleteByIdIn(numberList.get(i));
        }
        list.forEach(e-> {
            log.info("目标更新导入删除数据："+e.toString());
        });
        return flg;
    }

    private Specification<T_MANUAL_EST_EC> buildMultConditional(T_MANUAL_EST_ECQueryVM queryVM) {
        return new Specification<T_MANUAL_EST_EC>() {
            @Override
            public Predicate toPredicate(Root<T_MANUAL_EST_EC> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getItemCode())) {
                    Predicate p= cb.like(root.<String>get("itemCode"), "%" + queryVM.getItemCode() + "%");
                    predicates.add(p);
                }
                if (queryVM.getYear()!=null) {
                    Predicate p= cb.equal(root.<BigDecimal>get("year"), queryVM.getYear());
                    predicates.add(p);
                }
                if (queryVM.getMonth()!=null) {
                    Predicate p= cb.equal(root.<BigDecimal>get("month"), queryVM.getMonth());
                    predicates.add(p);
                }
                if (queryVM.getTargetType()!=null) {
                    Predicate p = cb.equal(root.get("targetType"),queryVM.getTargetType());
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
