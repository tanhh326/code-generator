package com.youcon.bp.cg.go.link.impl;

import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.DeptEntityResponse;
import com.youcon.bp.cg.go.DeptEntityPersistenceService;
import com.youcon.bp.cg.go.PostEntityPersistenceService;
import com.youcon.bp.cg.go.link.MidDeptPostEntity;
import com.youcon.bp.cg.go.link.MidDeptPostEntityCreateRequest;
import com.youcon.bp.cg.go.link.MidDeptPostEntityMapper;
import com.youcon.bp.cg.go.link.MidDeptPostEntityPersistence;
import com.youcon.bp.cg.go.link.MidDeptPostEntityRepository;
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
public class MidDeptPostEntityPersistenceImpl implements MidDeptPostEntityPersistence {

  @Autowired
  private MidDeptPostEntityMapper midDeptPostEntityMapper;
  @Autowired
  private MidDeptPostEntityRepository midDeptPostEntityRepository;

  @Autowired
  private DeptEntityPersistenceService deptEntityPersistenceService;
  @Autowired
  private PostEntityPersistenceService postEntityPersistenceService;


  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void bind(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return;
    }


    List<MidDeptPostEntity> list = new ArrayList<>();


    List<Long> deptIds = request.getDeptIds();
    for (Long deptId : deptIds) {
      List<Long> postIds = request.getPostIds();

      for (Long postId : postIds) {
        MidDeptPostEntity MidDeptPostEntity = new MidDeptPostEntity();
        MidDeptPostEntity.setDeptId(deptId);
        MidDeptPostEntity.setPostId(postId);
        list.add(MidDeptPostEntity);
      }
    }
    if (!CollectionUtils.isEmpty(list)) {
      midDeptPostEntityRepository.saveAllAndFlush(list);
    }

  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void unBind(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return;
    }
    List<MidDeptPostEntity> all = per(request);
    for (MidDeptPostEntity MidDeptPostEntity : all) {
      MidDeptPostEntity.setDeleted(1);
    }
    midDeptPostEntityRepository.saveAllAndFlush(all);
  }

  private List<MidDeptPostEntity> per(MidDeptPostEntityCreateRequest request){
    List<MidDeptPostEntity> all = this.midDeptPostEntityRepository.findAll(
    new Specification<MidDeptPostEntity>() {
      @Override
      public Predicate toPredicate(Root<MidDeptPostEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getDeptIds())) {
          predicatesList.add(
              criteriaBuilder.in(root.get("deptId")).value(request.getDeptIds()));
        }
        if (!CollectionUtils.isEmpty(request.getPostIds())) {
          predicatesList.add(
            criteriaBuilder.in(root.get("postId")).value(request.getPostIds()));
        }

        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return all;
  }

  @Override
  public List<PostEntityResponse> findByLeftIds(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> deptIds = request.getDeptIds();
    if (CollectionUtils.isEmpty(deptIds)) {
      return Collections.emptyList();
    }
    List<MidDeptPostEntity> all = this.midDeptPostEntityRepository.findAll(
        new Specification<MidDeptPostEntity>() {
          @Override
          public Predicate toPredicate(Root<MidDeptPostEntity> root, CriteriaQuery<?> query,
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
    List<Long> queryIds = all.stream().map(MidDeptPostEntity::getPostId)
              .toList();
    return this.postEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<DeptEntityResponse> findByRightIds(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> postIds = request.getPostIds();
    if (CollectionUtils.isEmpty(postIds)) {
      return Collections.emptyList();
    }
    List<MidDeptPostEntity> all = this.midDeptPostEntityRepository.findAll(
        new Specification<MidDeptPostEntity>() {
          @Override
          public Predicate toPredicate(Root<MidDeptPostEntity> root, CriteriaQuery<?> query,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicatesList = new ArrayList<>();
              if (!CollectionUtils.isEmpty(request.getPostIds())) {
                predicatesList.add(
                  criteriaBuilder.in(root.get("postId")).value(request.getPostIds()));
              }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));

            }
        });
    List<Long> queryIds = all.stream().map(MidDeptPostEntity::getDeptId)
              .toList();
    return this.deptEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<DeptEntityResponse> findLeftAndRightWithDeptEntity(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidDeptPostEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidDeptPostEntity::getDeptId)
          .toList();
    return this.deptEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<PostEntityResponse> findLeftAndRightWithPostEntity(MidDeptPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidDeptPostEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidDeptPostEntity::getPostId)
              .toList();
    return this.postEntityPersistenceService.byIds(queryIds);
  }
}
