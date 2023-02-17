package com.youcon.bp.cg.go.link.impl;

import com.youcon.bp.cg.go.module.servlet.DeptEntityResponse;
import com.youcon.bp.cg.go.module.servlet.UserEntityResponse;
import com.youcon.bp.cg.go.persistence.UserEntityPersistenceService;
import com.youcon.bp.cg.go.persistence.DeptEntityPersistenceService;
import com.youcon.bp.cg.go.link.MidUserDeptEntity;
import com.youcon.bp.cg.go.link.MidUserDeptEntityCreateRequest;
import com.youcon.bp.cg.go.link.MidUserDeptEntityMapper;
import com.youcon.bp.cg.go.link.MidUserDeptEntityPersistence;
import com.youcon.bp.cg.go.link.MidUserDeptEntityRepository;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.Collections;

@Service
public class MidUserDeptEntityPersistenceImpl implements MidUserDeptEntityPersistence {

  @Autowired
  private MidUserDeptEntityMapper midUserDeptEntityMapper;
  @Autowired
  private MidUserDeptEntityRepository midUserDeptEntityRepository;

  @Autowired
  private UserEntityPersistenceService userEntityPersistenceService;
  @Autowired
  private DeptEntityPersistenceService deptEntityPersistenceService;


  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void bind(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return;
    }


    List<MidUserDeptEntity> list = new ArrayList<>();


    List<Long> userIds = request.getUserIds();
    for (Long userId : userIds) {
      List<Long> deptIds = request.getDeptIds();

      for (Long deptId : deptIds) {
        MidUserDeptEntity MidUserDeptEntity = new MidUserDeptEntity();
        MidUserDeptEntity.setUserId(userId);
        MidUserDeptEntity.setDeptId(deptId);
        list.add(MidUserDeptEntity);
      }
    }
    if (!CollectionUtils.isEmpty(list)) {
      midUserDeptEntityRepository.saveAllAndFlush(list);
    }

  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void unBind(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return;
    }
    List<MidUserDeptEntity> all = per(request);
    for (MidUserDeptEntity MidUserDeptEntity : all) {
      MidUserDeptEntity.setDeleted(1);
    }
    midUserDeptEntityRepository.saveAllAndFlush(all);
  }

  private List<MidUserDeptEntity> per(MidUserDeptEntityCreateRequest request){
    List<MidUserDeptEntity> all = this.midUserDeptEntityRepository.findAll(
    new Specification<MidUserDeptEntity>() {
      @Override
      public Predicate toPredicate(Root<MidUserDeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUserIds())) {
          predicatesList.add(
              criteriaBuilder.in(root.get("userId")).value(request.getUserIds()));
        }
        if (!CollectionUtils.isEmpty(request.getDeptIds())) {
          predicatesList.add(
            criteriaBuilder.in(root.get("deptId")).value(request.getDeptIds()));
        }

        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return all;
  }

  @Override
  public List<DeptEntityResponse> findByLeftIds(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> userIds = request.getUserIds();
    if (CollectionUtils.isEmpty(userIds)) {
      return Collections.emptyList();
    }
    List<MidUserDeptEntity> all = this.midUserDeptEntityRepository.findAll(
        new Specification<MidUserDeptEntity>() {
          @Override
          public Predicate toPredicate(Root<MidUserDeptEntity> root, CriteriaQuery<?> query,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicatesList = new ArrayList<>();
              if (!CollectionUtils.isEmpty(request.getUserIds())) {
                predicatesList.add(
                  criteriaBuilder.in(root.get("userId")).value(request.getUserIds()));
              }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));

            }
        });
    List<Long> queryIds = all.stream().map(MidUserDeptEntity::getDeptId)
              .toList();
    return this.deptEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<UserEntityResponse> findByRightIds(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> deptIds = request.getDeptIds();
    if (CollectionUtils.isEmpty(deptIds)) {
      return Collections.emptyList();
    }
    List<MidUserDeptEntity> all = this.midUserDeptEntityRepository.findAll(
        new Specification<MidUserDeptEntity>() {
          @Override
          public Predicate toPredicate(Root<MidUserDeptEntity> root, CriteriaQuery<?> query,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicatesList = new ArrayList<>();
              if (!CollectionUtils.isEmpty(request.getDeptIds())) {
                predicatesList.add(
                  criteriaBuilder.in(root.get("deptId")).value(request.getDeptIds()));
              }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));

            }
        });
    List<Long> queryIds = all.stream().map(MidUserDeptEntity::getUserId)
              .toList();
    return this.userEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<UserEntityResponse> findLeftAndRightWithUserEntity(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidUserDeptEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidUserDeptEntity::getUserId)
          .toList();
    return this.userEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<DeptEntityResponse> findLeftAndRightWithDeptEntity(MidUserDeptEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidUserDeptEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidUserDeptEntity::getDeptId)
              .toList();
    return this.deptEntityPersistenceService.byIds(queryIds);
  }
}
