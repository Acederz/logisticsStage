package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_NEW_RETAIL_DYGMV;
import com.top.logisticsStage.repository.T_NEW_RETAIL_DYGMVRepository;
import com.top.logisticsStage.service.dto.T_NEW_RETAIL_DYGMVDTO;
import com.top.logisticsStage.service.mapper.T_NEW_RETAIL_DYGMVMapper;
import com.top.logisticsStage.web.rest.vm.T_NEW_RETAIL_DYGMVQueryVM;
import com.vdurmont.emoji.EmojiParser;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class T_NEW_RETAIL_DYGMVService {

    private final Logger log = LoggerFactory.getLogger(T_NEW_RETAIL_DYGMVService.class);

    private final T_NEW_RETAIL_DYGMVRepository t_NEW_RETAIL_DYGMVRepository;

    private final T_NEW_RETAIL_DYGMVMapper t_NEW_RETAIL_DYGMVMapper;

    public T_NEW_RETAIL_DYGMVService(T_NEW_RETAIL_DYGMVRepository t_NEW_RETAIL_DYGMVRepository, T_NEW_RETAIL_DYGMVMapper t_NEW_RETAIL_DYGMVMapper) {
        this.t_NEW_RETAIL_DYGMVRepository = t_NEW_RETAIL_DYGMVRepository;
        this.t_NEW_RETAIL_DYGMVMapper = t_NEW_RETAIL_DYGMVMapper;
    }

    public Page<T_NEW_RETAIL_DYGMVDTO> findList(T_NEW_RETAIL_DYGMVQueryVM queryVM, Pageable pageable) {
        Page<T_NEW_RETAIL_DYGMV> list = t_NEW_RETAIL_DYGMVRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_NEW_RETAIL_DYGMVDTO> page = list.map(t_NEW_RETAIL_DYGMVMapper::toDto);
        page.getContent().forEach(e->{
            e.setAccountName(EmojiParser.parseToUnicode(e.getAccountName()));
        });
        return page;
    }

    private Specification<T_NEW_RETAIL_DYGMV> buildMultConditional(T_NEW_RETAIL_DYGMVQueryVM queryVM) {
        return new Specification<T_NEW_RETAIL_DYGMV>() {
            @Override
            public Predicate toPredicate(Root<T_NEW_RETAIL_DYGMV> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getAccountName())) {
                    Predicate p= cb.like(root.<String>get("accountName"), "%" + queryVM.getAccountName() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getAccountType())) {
                    Predicate p= cb.equal(root.<BigDecimal>get("accountType"), queryVM.getAccountType());
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getCoopMode())) {
                    Predicate p= cb.equal(root.<BigDecimal>get("coopMode"), queryVM.getCoopMode());
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getStoreName())) {
                    Predicate p= cb.like(root.<String>get("storeName"), "%" + queryVM.getStoreName() + "%");
                    predicates.add(p);
                }
                if (queryVM.getStartDate()!=null) {
                    Predicate p = cb.greaterThanOrEqualTo(root.get("date"),queryVM.getStartDate());
                    predicates.add(p);
                }
                if (queryVM.getEndDate()!=null) {
                    Predicate p = cb.lessThanOrEqualTo(root.get("date"),queryVM.getEndDate());
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
