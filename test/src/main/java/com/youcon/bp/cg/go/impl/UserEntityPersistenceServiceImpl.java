package com.youcon.bp.cg.go.impl;
import java.math.BigDecimal;

import java.util.ArrayList;
import com.youcon.bp.cg.CreateValidate;
import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import com.youcon.bp.cg.SortRequest;
import com.youcon.bp.cg.UpdateValidate;
import com.youcon.bp.cg.go.UserEntity;
import com.youcon.bp.cg.go.UserEntityCreateRequest;
import com.youcon.bp.cg.go.UserEntityMapper;
import com.youcon.bp.cg.go.UserEntityPersistenceService;
import com.youcon.bp.cg.go.UserEntityQueryRequest;
import com.youcon.bp.cg.go.UserEntityRepository;
import com.youcon.bp.cg.go.UserEntityResponse;
import com.youcon.bp.cg.go.UserEntityUpdateRequest;
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
public class UserEntityPersistenceServiceImpl implements UserEntityPersistenceService {

  protected UserEntityCreateValidate craeteValidate = new UserEntityCreateValidate();
  protected UserEntityUpdateValidate updateValidate = new UserEntityUpdateValidate();
  @Autowired
  private UserEntityMapper userEntityMapper;
  @Autowired
  private UserEntityRepository userEntityRepository;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long save(UserEntityCreateRequest request) {
    craeteValidate.validate(request);
    UserEntity target = new UserEntity();
    BeanUtils.copyProperties(request, target);
    return userEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long update(UserEntityUpdateRequest request) {
    updateValidate.validate(request);
    UserEntity target = new UserEntity();
    BeanUtils.copyProperties(request, target);
    return userEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void delete(Long id) {
    Optional<UserEntity> byId = userEntityRepository.findById(id);
    if (byId.isPresent()) {
      UserEntity userEntity = byId.get();
      userEntity.setDeleted(1);
      userEntityRepository.saveAndFlush(userEntity);
    }
  }

  @Override
  public UserEntityResponse byId(Long id) {
    Optional<UserEntity> byId = userEntityRepository.findById(id);
    if (byId.isPresent()) {
      UserEntity userEntity = byId.get();
      UserEntityResponse target = new UserEntityResponse();
      BeanUtils.copyProperties(userEntity, target);
      return target;
    }
    return null;
  }

  @Override
  public List<UserEntityResponse> byIds(List<Long> ids) {
    List<UserEntity> allById = userEntityRepository.findAllById(ids);
    return allById.stream().map(s -> {
        UserEntityResponse target = new UserEntityResponse();
       BeanUtils.copyProperties(s, target);
       return target;
    }).collect(Collectors.toList());
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deletes(List<Long> ids) {
    List<UserEntity> allById = userEntityRepository.findAllById(ids);
    for (UserEntity userEntity : allById) {
      userEntity.setDeleted(1);
    }
    userEntityRepository.saveAllAndFlush(allById);
  }

  @Override
  public List<UserEntityResponse> list(UserEntityQueryRequest request) {
    List<UserEntity> all = this.userEntityRepository.findAll(new Specification<UserEntity>() {
      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          predicatesList.add(criteriaBuilder.like(root.get("username"), "%"+request.getUsername()+"%"));
          List<BigDecimal> ages = request.getAges();
          if (!CollectionUtils.isEmpty(ages)) {
            predicatesList.add(criteriaBuilder.between(root.get("age"), ages.get(0), ages.get(1)));
          }
          predicatesList.add(criteriaBuilder.like(root.get("password"), "%"+request.getPassword()+"%"));
          predicatesList.add(criteriaBuilder.like(root.get("email"), "%"+request.getEmail()+"%"));
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });

    return all.stream().map(s -> {
      UserEntityResponse target = new UserEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public PageResponse<UserEntityResponse> page(UserEntityQueryRequest request,
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

    Page<UserEntity> all = this.userEntityRepository.findAll(new Specification<UserEntity>() {
      @Override
      public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.like(root.get("username"), "%"+request.getUsername()+"%"));
        List<BigDecimal> ages = request.getAges();
        if (!CollectionUtils.isEmpty(ages)) {
          predicatesList.add(criteriaBuilder.between(root.get("age"), ages.get(0), ages.get(1)));
        }
        predicatesList.add(criteriaBuilder.like(root.get("password"), "%"+request.getPassword()+"%"));
        predicatesList.add(criteriaBuilder.like(root.get("email"), "%"+request.getEmail()+"%"));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    }, of);
    List<UserEntity> content = all.getContent();

    List<UserEntityResponse> collect = content.stream().map(s -> {
      UserEntityResponse target = new UserEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

    return new PageResponse<>(all.getTotalElements(), all.getPageable().getPageNumber(),
        all.getPageable().getPageSize(), collect);
  }


  /**
   * 创建校验
   */
  protected class UserEntityCreateValidate implements CreateValidate<UserEntityCreateRequest> {

    @Override
    public void createValidate(UserEntityCreateRequest request)
        throws IllegalArgumentException {

    }
  }

  /**
   * 更新校验
   */
  protected class UserEntityUpdateValidate implements UpdateValidate<UserEntityUpdateRequest> {

    @Override
    public void updateValidate(UserEntityUpdateRequest request)
        throws IllegalArgumentException {
      Long id = request.getId();
      if (id == null) {
        throw new IllegalArgumentException("id必填");
      }
    }
  }


}