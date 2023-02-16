package com.youcon.bp.cg.go.impl;
import java.util.ArrayList;
import com.youcon.bp.cg.CreateValidate;
import com.youcon.bp.cg.PageAndSortRequest;
import com.youcon.bp.cg.PageResponse;
import com.youcon.bp.cg.SortRequest;
import com.youcon.bp.cg.UpdateValidate;
import com.youcon.bp.cg.go.PostEntity;
import com.youcon.bp.cg.go.PostEntityCreateRequest;
import com.youcon.bp.cg.go.PostEntityMapper;
import com.youcon.bp.cg.go.PostEntityPersistenceService;
import com.youcon.bp.cg.go.PostEntityQueryRequest;
import com.youcon.bp.cg.go.PostEntityRepository;
import com.youcon.bp.cg.go.PostEntityResponse;
import com.youcon.bp.cg.go.PostEntityUpdateRequest;
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
public class PostEntityPersistenceServiceImpl implements PostEntityPersistenceService {

  protected PostEntityCreateValidate craeteValidate = new PostEntityCreateValidate();
  protected PostEntityUpdateValidate updateValidate = new PostEntityUpdateValidate();
  @Autowired
  private PostEntityMapper postEntityMapper;
  @Autowired
  private PostEntityRepository postEntityRepository;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long save(PostEntityCreateRequest request) {
    craeteValidate.validate(request);
    PostEntity target = new PostEntity();
    BeanUtils.copyProperties(request, target);
    return postEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long update(PostEntityUpdateRequest request) {
    updateValidate.validate(request);
    PostEntity target = new PostEntity();
    BeanUtils.copyProperties(request, target);
    return postEntityRepository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void delete(Long id) {
    Optional<PostEntity> byId = postEntityRepository.findById(id);
    if (byId.isPresent()) {
      PostEntity postEntity = byId.get();
      postEntity.setDeleted(1);
      postEntityRepository.saveAndFlush(postEntity);
    }
  }

  @Override
  public PostEntityResponse byId(Long id) {
    Optional<PostEntity> byId = postEntityRepository.findById(id);
    if (byId.isPresent()) {
      PostEntity postEntity = byId.get();
      PostEntityResponse target = new PostEntityResponse();
      BeanUtils.copyProperties(postEntity, target);
      return target;
    }
    return null;
  }

  @Override
  public List<PostEntityResponse> byIds(List<Long> ids) {
    List<PostEntity> allById = postEntityRepository.findAllById(ids);
    return allById.stream().map(s -> {
        PostEntityResponse target = new PostEntityResponse();
       BeanUtils.copyProperties(s, target);
       return target;
    }).collect(Collectors.toList());
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deletes(List<Long> ids) {
    List<PostEntity> allById = postEntityRepository.findAllById(ids);
    for (PostEntity postEntity : allById) {
      postEntity.setDeleted(1);
    }
    postEntityRepository.saveAllAndFlush(allById);
  }

  @Override
  public List<PostEntityResponse> list(PostEntityQueryRequest request) {
    List<PostEntity> all = this.postEntityRepository.findAll(new Specification<PostEntity>() {
      @Override
      public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });

    return all.stream().map(s -> {
      PostEntityResponse target = new PostEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public PageResponse<PostEntityResponse> page(PostEntityQueryRequest request,
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

    Page<PostEntity> all = this.postEntityRepository.findAll(new Specification<PostEntity>() {
      @Override
      public Predicate toPredicate(Root<PostEntity> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.like(root.get("name"), "%"+request.getName()+"%"));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    }, of);
    List<PostEntity> content = all.getContent();

    List<PostEntityResponse> collect = content.stream().map(s -> {
      PostEntityResponse target = new PostEntityResponse();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

    return new PageResponse<>(all.getTotalElements(), all.getPageable().getPageNumber(),
        all.getPageable().getPageSize(), collect);
  }



  /**
   * 创建校验
   */
  protected class PostEntityCreateValidate implements CreateValidate<PostEntityCreateRequest> {

    @Override
    public void createValidate(PostEntityCreateRequest request)
        throws IllegalArgumentException {

    }
  }

  /**
   * 更新校验
   */
  protected class PostEntityUpdateValidate implements UpdateValidate<PostEntityUpdateRequest> {

    @Override
    public void updateValidate(PostEntityUpdateRequest request)
        throws IllegalArgumentException {
      Long id = request.getId();
      if (id == null) {
        throw new IllegalArgumentException("id必填");
      }
    }
  }


}