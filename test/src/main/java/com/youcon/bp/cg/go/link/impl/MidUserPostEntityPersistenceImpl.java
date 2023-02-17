package com.youcon.bp.cg.go.link.impl;

import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.UserEntityResponse;
import com.youcon.bp.cg.go.UserEntityPersistenceService;
import com.youcon.bp.cg.go.PostEntityPersistenceService;
import com.youcon.bp.cg.go.link.MidUserPostEntity;
import com.youcon.bp.cg.go.link.MidUserPostEntityCreateRequest;
import com.youcon.bp.cg.go.link.MidUserPostEntityMapper;
import com.youcon.bp.cg.go.link.MidUserPostEntityPersistence;
import com.youcon.bp.cg.go.link.MidUserPostEntityRepository;
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
public class MidUserPostEntityPersistenceImpl implements MidUserPostEntityPersistence {

  @Autowired
  private MidUserPostEntityMapper midUserPostEntityMapper;
  @Autowired
  private MidUserPostEntityRepository midUserPostEntityRepository;

  @Autowired
  private UserEntityPersistenceService userEntityPersistenceService;
  @Autowired
  private PostEntityPersistenceService postEntityPersistenceService;


  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void bind(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return;
    }


    List<MidUserPostEntity> list = new ArrayList<>();


    List<Long> userIds = request.getUserIds();
    for (Long userId : userIds) {
      List<Long> postIds = request.getPostIds();

      for (Long postId : postIds) {
        MidUserPostEntity MidUserPostEntity = new MidUserPostEntity();
        MidUserPostEntity.setUserId(userId);
        MidUserPostEntity.setPostId(postId);
        list.add(MidUserPostEntity);
      }
    }
    if (!CollectionUtils.isEmpty(list)) {
      midUserPostEntityRepository.saveAllAndFlush(list);
    }

  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void unBind(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return;
    }
    List<MidUserPostEntity> all = per(request);
    for (MidUserPostEntity MidUserPostEntity : all) {
      MidUserPostEntity.setDeleted(1);
    }
    midUserPostEntityRepository.saveAllAndFlush(all);
  }

  private List<MidUserPostEntity> per(MidUserPostEntityCreateRequest request){
    List<MidUserPostEntity> all = this.midUserPostEntityRepository.findAll(
    new Specification<MidUserPostEntity>() {
      @Override
      public Predicate toPredicate(Root<MidUserPostEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getUserIds())) {
          predicatesList.add(
              criteriaBuilder.in(root.get("userId")).value(request.getUserIds()));
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
  public List<PostEntityResponse> findByLeftIds(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> userIds = request.getUserIds();
    if (CollectionUtils.isEmpty(userIds)) {
      return Collections.emptyList();
    }
    List<MidUserPostEntity> all = this.midUserPostEntityRepository.findAll(
        new Specification<MidUserPostEntity>() {
          @Override
          public Predicate toPredicate(Root<MidUserPostEntity> root, CriteriaQuery<?> query,
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
    List<Long> queryIds = all.stream().map(MidUserPostEntity::getPostId)
              .toList();
    return this.postEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<UserEntityResponse> findByRightIds(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> postIds = request.getPostIds();
    if (CollectionUtils.isEmpty(postIds)) {
      return Collections.emptyList();
    }
    List<MidUserPostEntity> all = this.midUserPostEntityRepository.findAll(
        new Specification<MidUserPostEntity>() {
          @Override
          public Predicate toPredicate(Root<MidUserPostEntity> root, CriteriaQuery<?> query,
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
    List<Long> queryIds = all.stream().map(MidUserPostEntity::getUserId)
              .toList();
    return this.userEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<UserEntityResponse> findLeftAndRightWithUserEntity(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidUserPostEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidUserPostEntity::getUserId)
          .toList();
    return this.userEntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<PostEntityResponse> findLeftAndRightWithPostEntity(MidUserPostEntityCreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<MidUserPostEntity> all = per(request);
    List<Long> queryIds = all.stream().map(MidUserPostEntity::getPostId)
              .toList();
    return this.postEntityPersistenceService.byIds(queryIds);
  }
}
