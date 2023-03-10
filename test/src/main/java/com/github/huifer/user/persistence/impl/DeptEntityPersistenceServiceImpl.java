package com.github.huifer.user.persistence.impl;

import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import org.apache.commons.lang3.StringUtils;
import java.util.Collections;
import java.util.ArrayList;
import com.youcon.bp.cg.CreateValidate;
import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import com.youcon.bp.cg.SortRequest;
import com.youcon.bp.cg.UpdateValidate;
import com.github.huifer.user.entity.DeptEntity;
import com.github.huifer.user.servlet.DeptEntityCreateRequest;
import com.github.huifer.user.persistence.DeptEntityPersistenceService;
import com.github.huifer.user.servlet.DeptEntityQueryRequest;
import com.github.huifer.user.repository.DeptEntityRepository;
import com.github.huifer.user.servlet.DeptEntityResponse;
import com.github.huifer.user.servlet.DeptEntityUpdateRequest;
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
public class DeptEntityPersistenceServiceImpl implements DeptEntityPersistenceService {

  protected DeptEntityCreateValidate craeteValidate = new DeptEntityCreateValidate();
  protected DeptEntityUpdateValidate updateValidate = new DeptEntityUpdateValidate();
  @Autowired
  private DeptEntityRepository deptEntityRepository;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long save(DeptEntityCreateRequest request) {
    craeteValidate.validate(request);
    DeptEntity target = new DeptEntity();
    BeanUtils.copyProperties(request, target);
    return deptEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long update(DeptEntityUpdateRequest request) {
    updateValidate.validate(request);
    DeptEntity target = new DeptEntity();
    BeanUtils.copyProperties(request, target);
    return deptEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void delete(Long id) {
    Optional<DeptEntity> byId = deptEntityRepository.findById(id);
    if (byId.isPresent()) {
      DeptEntity deptEntity = byId.get();
      deptEntity.setDeleted(1);
      deptEntityRepository.saveAndFlush(deptEntity);
    }
  }

  @Override
  public DeptEntityResponse byId(Long id) {
    Optional<DeptEntity> byId = deptEntityRepository.findById(id);
    if (byId.isPresent()) {
      DeptEntity deptEntity = byId.get();
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(deptEntity, target);
      return target;
    }
    return null;
  }

  @Override
  public List<DeptEntityResponse> byIds(List<Long> ids) {
    List<DeptEntity> allById = deptEntityRepository.findAllById(ids);
    return allById.stream().map(s -> {
        DeptEntityResponse target = new DeptEntityResponse();
       BeanUtils.copyProperties(s, target);
       return target;
    }).collect(Collectors.toList());
  }

  @Override
  public List<Tree<Object>> byIdsTree(List<Long> ids){
    List<DeptEntity> allById = deptEntityRepository.findAllById(ids);
    return tree(allById);
  }



  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deletes(List<Long> ids) {
    List<DeptEntity> allById = deptEntityRepository.findAllById(ids);
    for (DeptEntity deptEntity : allById) {
      deptEntity.setDeleted(1);
    }
    deptEntityRepository.saveAllAndFlush(allById);
  }

  @Override
  public List<DeptEntityResponse> list(DeptEntityQueryRequest request) {
    List<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          if (StringUtils.isNotBlank(request.getName())) {
            predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
          }
          if (request.getCompanyId() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("companyId"), request.getCompanyId()));
          }
          if (request.getPid() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("pid"), request.getPid()));
          }
          if (request.getLeader() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("leader"), request.getLeader()));
          }
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });

    return all.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public List<Tree<Object>> listTree(DeptEntityQueryRequest request){
    List<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          if (StringUtils.isNotBlank(request.getName())){
            predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
          }
          if (request.getCompanyId() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("companyId"), request.getCompanyId()));
          }
          if (request.getPid() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("pid"), request.getPid()));
          }
          if (request.getLeader() != null){
            predicatesList.add(criteriaBuilder.equal(root.get("leader"), request.getLeader()));
          }
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });
    return tree(all);
  }

  @Override
  public PageResponse<DeptEntityResponse> page(DeptEntityQueryRequest request,
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

    Page<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (StringUtils.isNotBlank(request.getName())){
          predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
        }
        if (request.getCompanyId() != null){
          predicatesList.add(criteriaBuilder.equal(root.get("companyId"), request.getCompanyId()));
        }
        if (request.getPid() != null){
          predicatesList.add(criteriaBuilder.equal(root.get("pid"), request.getPid()));
        }
        if (request.getLeader() != null){
          predicatesList.add(criteriaBuilder.equal(root.get("leader"), request.getLeader()));
        }
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    }, of);
    List<DeptEntity> content = all.getContent();

    List<DeptEntityResponse> collect = content.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

    return new PageResponse<>(all.getTotalElements(), all.getPageable().getPageNumber(),
        all.getPageable().getPageSize(), collect);
  }

  @Override
  public List<DeptEntityResponse> findByCompanyId(Long companyId){
    List<DeptEntity> all = this.deptEntityRepository.findAll(
    new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("companyId"), companyId));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });

    return all.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

  }

  @Override
  public List<Tree<Object>> findByCompanyIdTree(Long companyId){
    List<DeptEntity> all = this.deptEntityRepository.findAll(
    new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("companyId"), companyId));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return tree(all);
  }

  @Override
  public List<DeptEntityResponse> findByCompanyIds(List<Long> companyIds){
    List<DeptEntity> all = this.deptEntityRepository.findAll(
    new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("companyId")).value(companyIds));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });

    return all.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }
  
  @Override
  public List<Tree<Object>> findByCompanyIdsTree(List<Long> companyIds){
    List<DeptEntity> all = this.deptEntityRepository.findAll(
    new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("companyId")).value(companyIds));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return tree(all);
  }


  /**
  * ?????????
  */
  @Override
  public List<Tree<Object>> tree(){
    List<DeptEntity> all = this.deptEntityRepository.findAll();
    return tree(all);
  }

  /**
  * ?????????
  */
  @Override
  public List<Tree<Object>> tree(List<DeptEntity> all){
    TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
    treeNodeConfig.setIdKey("id");
    treeNodeConfig.setParentIdKey("pid");
    List<Tree<Object>> build = TreeUtil.build(all, null, treeNodeConfig,
      new NodeParser<DeptEntity, Object>() {
      @Override
      public void parse(DeptEntity deptEntity, Tree<Object> tree) {
        tree.setId(deptEntity.getId());
        tree.setParentId(deptEntity.getPid());
        tree.putExtra("name", deptEntity.getName());
        tree.putExtra("companyId", deptEntity.getCompanyId());
        tree.putExtra("pid", deptEntity.getPid());
        tree.putExtra("leader", deptEntity.getLeader());
        }
      });
    return build;
  }

  @Override
  public List<DeptEntityResponse> subId(Long pid){
    if (pid ==null){
      return Collections.emptyList(); 
    }
    List<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("pid"), pid));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });
    return all.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  public List<DeptEntityResponse> subIds(List<Long> pids){
    if (CollectionUtils.isEmpty(pids)) {
      return Collections.emptyList(); 
    }
    List<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("pid")).value(pids));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });
    return all.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }


  @Override
  public List<DeptEntityResponse> subIdAll(Long pid){
    if (pid == null){
      return Collections.emptyList();
    }
    List<Long> ids = new ArrayList<>();
    ids.add(pid);
    List<DeptEntity> child = child(ids);

    return child.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public List<DeptEntityResponse> subIdsAll(List<Long> pids){
    if (CollectionUtils.isEmpty(pids)) {
      return Collections.emptyList(); 
    }
    List<DeptEntity> child = child(pids);
    return child.stream().map(s -> {
      DeptEntityResponse target = new DeptEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }


  public List<DeptEntity> child(List<Long> pids){
    List<DeptEntity> result = new ArrayList<>();
    selectChild(result, pids);
    return result;
  }

  public void selectChild(List<DeptEntity> result, List<Long> pids) {

    List<Long> temp = new ArrayList<>();
    List<DeptEntity> sa = new ArrayList<>();
    for (Long pid : pids) {
      List<DeptEntity> all = this.deptEntityRepository.findAll(new Specification<DeptEntity>() {
        @Override
        public Predicate toPredicate(Root<DeptEntity> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          predicatesList.add(criteriaBuilder.in(root.get("pid")).value(pid));
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
        }
      });
      sa = all;
      for (DeptEntity s : sa) {
        temp.add(s.getId());
        result.add(s);
      }
    }
    if (temp.size() != 0 && temp != null) {
      selectChild(result, temp);
    }
  }

  /**
   * ????????????
   */
  protected class DeptEntityCreateValidate implements CreateValidate<DeptEntityCreateRequest> {

    @Override
    public void createValidate(DeptEntityCreateRequest request)
        throws IllegalArgumentException {

    }
  }

  /**
   * ????????????
   */
  protected class DeptEntityUpdateValidate implements UpdateValidate<DeptEntityUpdateRequest> {

    @Override
    public void updateValidate(DeptEntityUpdateRequest request)
        throws IllegalArgumentException {
      Long id = request.getId();
      if (id == null) {
        throw new IllegalArgumentException("id??????");
      }
    }
  }


}