package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_YS;
import com.top.logisticsStage.repository.T_MANUAL_FEE_XLS_YSRepository;
import com.top.logisticsStage.service.dto.T_MANUAL_FEE_XLS_YSDTO;
import com.top.logisticsStage.service.mapper.T_MANUAL_FEE_XLS_YSMapper;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_FEE_XLS_YSQueryVM;
import org.apache.commons.lang.StringUtils;
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
public class T_MANUAL_FEE_XLS_YSService {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_FEE_XLS_YSService.class);

    private final T_MANUAL_FEE_XLS_YSRepository t_MANUAL_FEE_XLS_YSRepository;

    private final T_MANUAL_FEE_XLS_YSMapper t_MANUAL_FEE_XLS_YSMapper;

    public T_MANUAL_FEE_XLS_YSService(T_MANUAL_FEE_XLS_YSRepository t_MANUAL_FEE_XLS_YSRepository, T_MANUAL_FEE_XLS_YSMapper t_MANUAL_FEE_XLS_YSMapper) {
        this.t_MANUAL_FEE_XLS_YSRepository = t_MANUAL_FEE_XLS_YSRepository;
        this.t_MANUAL_FEE_XLS_YSMapper = t_MANUAL_FEE_XLS_YSMapper;
    }

    public Page<T_MANUAL_FEE_XLS_YSDTO> findList(T_MANUAL_FEE_XLS_YSQueryVM queryVM, Pageable pageable) {
        Page<T_MANUAL_FEE_XLS_YS> list = t_MANUAL_FEE_XLS_YSRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_MANUAL_FEE_XLS_YSDTO> page = list.map(t_MANUAL_FEE_XLS_YSMapper::toDto);
        return page;
    }

    private Specification<T_MANUAL_FEE_XLS_YS> buildMultConditional(T_MANUAL_FEE_XLS_YSQueryVM queryVM) {
        return new Specification<T_MANUAL_FEE_XLS_YS>() {
            @Override
            public Predicate toPredicate(Root<T_MANUAL_FEE_XLS_YS> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getYearMonth())) {
                    Predicate p= cb.like(root.<String>get("yearMonth"), "%" + queryVM.getYearMonth() + "%");
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
