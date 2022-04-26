package com.top.logisticsStage.service;

import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_TIKI_PRODUCT;
import com.top.logisticsStage.repository.T_MANUAL_EST_ECRepository;
import com.top.logisticsStage.repository.T_TIKI_PRODUCTRepository;
import com.top.logisticsStage.service.dto.T_TIKI_PRODUCTDTO;
import com.top.logisticsStage.service.mapper.T_TIKI_PRODUCTMapper;
import com.top.logisticsStage.web.rest.vm.T_TIKI_PRODUCTQueryVM;
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
public class T_TIKI_PRODUCTService {

    private final Logger log = LoggerFactory.getLogger(T_TIKI_PRODUCTService.class);

    private final T_TIKI_PRODUCTRepository t_TIKI_PRODUCTRepository;

    private final T_MANUAL_EST_ECRepository t_MANUAL_EST_ECRepository;

    private final T_TIKI_PRODUCTMapper t_TIKI_PRODUCTMapper;

    public T_TIKI_PRODUCTService(T_TIKI_PRODUCTRepository t_TIKI_PRODUCTRepository, T_MANUAL_EST_ECRepository t_manual_est_ecRepository, T_TIKI_PRODUCTMapper t_TIKI_PRODUCTMapper) {
        this.t_TIKI_PRODUCTRepository = t_TIKI_PRODUCTRepository;
        t_MANUAL_EST_ECRepository = t_manual_est_ecRepository;
        this.t_TIKI_PRODUCTMapper = t_TIKI_PRODUCTMapper;
    }

    public T_TIKI_PRODUCTDTO create(T_TIKI_PRODUCTDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_TIKI_PRODUCT entity = t_TIKI_PRODUCTMapper.toEntity(dto);
        if(t_TIKI_PRODUCTRepository.existsById(dto.getSku())) {
            throw new ServiceException("数据已存在！");
        }
        entity = t_TIKI_PRODUCTRepository.save(entity);
        return t_TIKI_PRODUCTMapper.toDto(entity);
    }

    public T_TIKI_PRODUCTDTO update(T_TIKI_PRODUCTDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_TIKI_PRODUCTRepository.getOne(dto.getSku()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_TIKI_PRODUCT entity = t_TIKI_PRODUCTMapper.toEntity(dto);
        entity = t_TIKI_PRODUCTRepository.save(entity);
        return t_TIKI_PRODUCTMapper.toDto(entity);
    }

    public Page<T_TIKI_PRODUCTDTO> findList(T_TIKI_PRODUCTQueryVM queryVM, Pageable pageable) {
        Page<T_TIKI_PRODUCT> list = t_TIKI_PRODUCTRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_TIKI_PRODUCTDTO> page = list.map(t_TIKI_PRODUCTMapper::toDto);
        return page;
    }

    private Specification<T_TIKI_PRODUCT> buildMultConditional(T_TIKI_PRODUCTQueryVM queryVM) {
        return new Specification<T_TIKI_PRODUCT>() {
            @Override
            public Predicate toPredicate(Root<T_TIKI_PRODUCT> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getSku())) {
                    Predicate p= cb.like(root.<String>get("sku"), "%" + queryVM.getSku() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getProduct())) {
                    Predicate p= cb.like(root.<String>get("product"), "%" + queryVM.getProduct() + "%");
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
