package com.top.logisticsStage.service;


import com.top.logisticsStage.config.SecurityUtils;
import com.top.logisticsStage.domain.T_WL_ITEMSIZE;
import com.top.logisticsStage.repository.T_WL_ITEMSIZERepository;
import com.top.logisticsStage.service.dto.T_WL_ITEMSIZEDTO;
import com.top.logisticsStage.service.mapper.T_WL_ITEMSIZEMapper;
import com.top.logisticsStage.web.rest.UserResource;
import com.top.logisticsStage.web.rest.vm.T_WL_ITEMSIZEQueryVM;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class T_WL_ITEMSIZEService {

    private final Logger log = LoggerFactory.getLogger(T_WL_ITEMSIZEService.class);

    private final T_WL_ITEMSIZERepository t_wl_itemsizeRepository;

    private final T_WL_ITEMSIZEMapper t_wl_itemsizeMapper;

    public T_WL_ITEMSIZEService(T_WL_ITEMSIZERepository t_wl_itemsizeRepository, T_WL_ITEMSIZEMapper t_wl_itemsizeMapper) {
        this.t_wl_itemsizeRepository = t_wl_itemsizeRepository;
        this.t_wl_itemsizeMapper = t_wl_itemsizeMapper;
    }

    public T_WL_ITEMSIZEDTO create(T_WL_ITEMSIZEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"新增："+dto.toString());
        T_WL_ITEMSIZE entity = t_wl_itemsizeMapper.toEntity(dto);
        T_WL_ITEMSIZE t = t_wl_itemsizeRepository.findById(entity.getBarCode()).get();
        if(t!=null) {
            throw new ServiceException("条码已重复！");
        }
        entity = t_wl_itemsizeRepository.save(entity);
        return t_wl_itemsizeMapper.toDto(entity);
    }

    public T_WL_ITEMSIZEDTO update(T_WL_ITEMSIZEDTO dto) {
        log.info(SecurityUtils.getCurrentUserLogin() +"修改原数据："+t_wl_itemsizeRepository.getOne(dto.getBarCode()).toString());
        log.info(SecurityUtils.getCurrentUserLogin() +"修改新数据："+dto.toString());
        T_WL_ITEMSIZE entity = t_wl_itemsizeMapper.toEntity(dto);
        if(!t_wl_itemsizeRepository.existsById(entity.getBarCode())) {
            throw new ServiceException("条码不存在，请转新增！");
        }
        entity = t_wl_itemsizeRepository.save(entity);
        return t_wl_itemsizeMapper.toDto(entity);
    }

    public Page<T_WL_ITEMSIZEDTO> findList(T_WL_ITEMSIZEQueryVM queryVM, Pageable pageable) {
        Page<T_WL_ITEMSIZE> list = t_wl_itemsizeRepository.findAll(buildMultConditional(queryVM), pageable);
        Page<T_WL_ITEMSIZEDTO> page = list.map(t_wl_itemsizeMapper::toDto);
        return page;
    }

    private Specification<T_WL_ITEMSIZE> buildMultConditional(T_WL_ITEMSIZEQueryVM queryVM) {
        return new Specification<T_WL_ITEMSIZE>() {
            @Override
            public Predicate toPredicate(Root<T_WL_ITEMSIZE> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>(7);
                if (StringUtils.isNotBlank(queryVM.getBarCode())) {
                    Predicate p= cb.like(root.<String>get("barCode"), "%" + queryVM.getBarCode() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getItemCode())) {
                    Predicate p= cb.like(root.<String>get("itemCode"), "%" + queryVM.getItemCode() + "%");
                    predicates.add(p);
                }
                if (StringUtils.isNotBlank(queryVM.getProductName())) {
                    Predicate p= cb.like(root.<String>get("productName"), "%" + queryVM.getProductName() + "%");
                    predicates.add(p);
                }
                if (queryVM.getUpdateTimeStart()!=null) {
                    Predicate p = cb.greaterThanOrEqualTo(root.get("updateTime"),queryVM.getUpdateTimeStart());
                    predicates.add(p);
                }
                if (queryVM.getUpdateTimeEnd()!=null) {
                    Predicate p = cb.lessThanOrEqualTo(root.get("updateTime"),queryVM.getUpdateTimeEnd());
                    predicates.add(p);
                }
                if (queryVM.getLaunchTimeStart()!=null) {
                    Predicate p = cb.greaterThanOrEqualTo(root.get("launchTime"),queryVM.getLaunchTimeStart());
                    predicates.add(p);
                }
                if (queryVM.getLaunchTimeEnd()!=null) {
                    Predicate p = cb.lessThanOrEqualTo(root.get("launchTime"),queryVM.getLaunchTimeEnd());
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
