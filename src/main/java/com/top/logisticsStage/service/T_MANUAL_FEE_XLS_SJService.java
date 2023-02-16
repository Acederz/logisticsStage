package com.top.logisticsStage.service;

import com.top.logisticsStage.domain.T_MANUAL_FEE_XLS_SJ;
import com.top.logisticsStage.repository.T_MANUAL_FEE_XLS_SJRepository;
import com.top.logisticsStage.service.dto.T_MANUAL_FEE_XLS_SJDTO;
import com.top.logisticsStage.service.mapper.T_MANUAL_FEE_XLS_SJMapper;
import com.top.logisticsStage.web.rest.vm.T_MANUAL_FEE_XLS_SJQueryVM;
import com.vdurmont.emoji.EmojiParser;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class T_MANUAL_FEE_XLS_SJService {

    private final Logger log = LoggerFactory.getLogger(T_MANUAL_FEE_XLS_SJService.class);

    private final T_MANUAL_FEE_XLS_SJRepository t_MANUAL_FEE_XLS_SJRepository;

    private final T_MANUAL_FEE_XLS_SJMapper t_MANUAL_FEE_XLS_SJMapper;

    public T_MANUAL_FEE_XLS_SJService(T_MANUAL_FEE_XLS_SJRepository t_MANUAL_FEE_XLS_SJRepository, T_MANUAL_FEE_XLS_SJMapper t_MANUAL_FEE_XLS_SJMapper) {
        this.t_MANUAL_FEE_XLS_SJRepository = t_MANUAL_FEE_XLS_SJRepository;
        this.t_MANUAL_FEE_XLS_SJMapper = t_MANUAL_FEE_XLS_SJMapper;
    }

    public Page<T_MANUAL_FEE_XLS_SJDTO> findList(T_MANUAL_FEE_XLS_SJQueryVM queryVM, Pageable pageable) {
        Page<T_MANUAL_FEE_XLS_SJ> list = t_MANUAL_FEE_XLS_SJRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_MANUAL_FEE_XLS_SJDTO> page = list.map(t_MANUAL_FEE_XLS_SJMapper::toDto);
        return page;
    }

    private Specification<T_MANUAL_FEE_XLS_SJ> buildMultConditional(T_MANUAL_FEE_XLS_SJQueryVM queryVM) {
        return new Specification<T_MANUAL_FEE_XLS_SJ>() {
            @Override
            public Predicate toPredicate(Root<T_MANUAL_FEE_XLS_SJ> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
