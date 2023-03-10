package com.github.huifer.user.persistence.impl;

import org.apache.commons.lang3.StringUtils;
import java.util.Collections;
import java.util.ArrayList;
import com.youcon.bp.cg.CreateValidate;
import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import com.youcon.bp.cg.SortRequest;
import com.youcon.bp.cg.UpdateValidate;
import com.github.huifer.user.entity.CompanyEntity;
import com.github.huifer.user.servlet.CompanyEntityCreateRequest;
import com.github.huifer.user.persistence.CompanyEntityPersistenceService;
import com.github.huifer.user.servlet.CompanyEntityQueryRequest;
import com.github.huifer.user.repository.CompanyEntityRepository;
import com.github.huifer.user.servlet.CompanyEntityResponse;
import com.github.huifer.user.servlet.CompanyEntityUpdateRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class CompanyEntityPersistenceServiceImpl implements CompanyEntityPersistenceService {

  protected CompanyEntityCreateValidate craeteValidate = new CompanyEntityCreateValidate();
  protected CompanyEntityUpdateValidate updateValidate = new CompanyEntityUpdateValidate();
  @Autowired
  private CompanyEntityRepository companyEntityRepository;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long save(CompanyEntityCreateRequest request) {
    craeteValidate.validate(request);
    CompanyEntity target = new CompanyEntity();
    BeanUtils.copyProperties(request, target);
    return companyEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long update(CompanyEntityUpdateRequest request) {
    updateValidate.validate(request);
    CompanyEntity target = new CompanyEntity();
    BeanUtils.copyProperties(request, target);
    return companyEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void delete(Long id) {
    Optional<CompanyEntity> byId = companyEntityRepository.findById(id);
    if (byId.isPresent()) {
      CompanyEntity companyEntity = byId.get();
      companyEntity.setDeleted(1);
      companyEntityRepository.saveAndFlush(companyEntity);
    }
  }

  @Override
  public CompanyEntityResponse byId(Long id) {
    Optional<CompanyEntity> byId = companyEntityRepository.findById(id);
    if (byId.isPresent()) {
      CompanyEntity companyEntity = byId.get();
      CompanyEntityResponse target = new CompanyEntityResponse();
      BeanUtils.copyProperties(companyEntity, target);
      return target;
    }
    return null;
  }

  @Override
  public List<CompanyEntityResponse> byIds(List<Long> ids) {
    List<CompanyEntity> allById = companyEntityRepository.findAllById(ids);
    return allById.stream().map(s -> {
        CompanyEntityResponse target = new CompanyEntityResponse();
       BeanUtils.copyProperties(s, target);
       return target;
    }).collect(Collectors.toList());
  }




  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deletes(List<Long> ids) {
    List<CompanyEntity> allById = companyEntityRepository.findAllById(ids);
    for (CompanyEntity companyEntity : allById) {
      companyEntity.setDeleted(1);
    }
    companyEntityRepository.saveAllAndFlush(allById);
  }

  @Override
  public List<CompanyEntityResponse> list(CompanyEntityQueryRequest request) {
    List<CompanyEntity> all = this.companyEntityRepository.findAll(new Specification<CompanyEntity>() {
      @Override
      public Predicate toPredicate(Root<CompanyEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          if (StringUtils.isNotBlank(request.getName())) {
            predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
          }
          if (request.getPid() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("pid"), request.getPid()));
          }
          if (StringUtils.isNotBlank(request.getLogo())) {
            predicatesList.add(criteriaBuilder.like(root.get("logo"), "%"+request.getLogo()+"%"));
          }
          if (StringUtils.isNotBlank(request.getAddress())) {
            predicatesList.add(criteriaBuilder.like(root.get("address"), "%"+request.getAddress()+"%"));
          }
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });

    return all.stream().map(s -> {
      CompanyEntityResponse target = new CompanyEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }


  @Override
  public PageResponse<CompanyEntityResponse> page(CompanyEntityQueryRequest request,
      PageAndSortRequest page) {

    Pageable of;
    SortRequest sort = page.getSort();
    if (sort != null) {
      Sort by = Sort.by(sort.isDesc() ? Direction.DESC : Direction.ASC, sort.getSortKey());
      of = PageRequest.of(page.getPage(), page.getSize(), by);

    } else {
      Sort by = Sort.by(Direction.DESC,
          "createTime");
      of = PageRequest.of(page.getPage(), page.getSize(), by);
    }

    Page<CompanyEntity> all = this.companyEntityRepository.findAll(new Specification<CompanyEntity>() {
      @Override
      public Predicate toPredicate(Root<CompanyEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getName())){
          predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
        }
        if (request.getPid() != null){
          predicatesList.add(criteriaBuilder.equal(root.get("pid"), request.getPid()));
        }
        if (StringUtils.isNotBlank(request.getLogo())){
          predicatesList.add(criteriaBuilder.like(root.get("logo"), "%"+request.getLogo()+"%"));
        }
        if (StringUtils.isNotBlank(request.getAddress())){
          predicatesList.add(criteriaBuilder.like(root.get("address"), "%"+request.getAddress()+"%"));
        }
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    }, of);
    List<CompanyEntity> content = all.getContent();

    List<CompanyEntityResponse> collect = content.stream().map(s -> {
      CompanyEntityResponse target = new CompanyEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

    return new PageResponse<>(all.getTotalElements(), all.getPageable().getPageNumber(),
        all.getPageable().getPageSize(), collect);
  }



  /**
   * ????????????
   */
  protected class CompanyEntityCreateValidate implements CreateValidate<CompanyEntityCreateRequest> {

    @Override
    public void createValidate(CompanyEntityCreateRequest request)
        throws IllegalArgumentException {

    }
  }

  /**
   * ????????????
   */
  protected class CompanyEntityUpdateValidate implements UpdateValidate<CompanyEntityUpdateRequest> {

    @Override
    public void updateValidate(CompanyEntityUpdateRequest request)
        throws IllegalArgumentException {
      Long id = request.getId();
      if (id == null) {
        throw new IllegalArgumentException("id??????");
      }
    }
  }


}