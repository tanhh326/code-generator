package ${pkg}.impl;
<#function dashedToCamel(s)>
    <#return s
    ?replace('(^_+)|(_+$)', '', 'r')
    ?replace('\\_+(\\w)?', ' $1', 'r')
    ?replace('([A-Z])', ' $1', 'r')
    ?capitalize
    ?replace(' ' , '')
    ?uncap_first
    >
</#function>
<#list  imports as impt>
import ${impt};

</#list>
import java.util.ArrayList;
import ${commomPKG}.CreateValidate;
import ${commomPKG}.PageAndSortRequest;
import ${commomPKG}.PageResponse;
import ${commomPKG}.SortRequest;
import ${commomPKG}.UpdateValidate;
import ${pkg}.${entityName};
import ${pkg}.${entityName}CreateRequest;
import ${pkg}.${entityName}Mapper;
import ${pkg}.${entityName}PersistenceService;
import ${pkg}.${entityName}QueryRequest;
import ${pkg}.${entityName}Repository;
import ${pkg}.${entityName}Response;
import ${pkg}.${entityName}UpdateRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class ${entityName}PersistenceServiceImpl implements ${entityName}PersistenceService {

  protected ${entityName}CreateValidate craeteValidate = new ${entityName}CreateValidate();
  protected ${entityName}UpdateValidate updateValidate = new ${entityName}UpdateValidate();
  @Autowired
  private ${entityName}Mapper ${entityName?uncap_first}Mapper;
  @Autowired
  private ${entityName}Repository ${entityName?uncap_first}Repository;

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long save(${entityName}CreateRequest request) {
    craeteValidate.validate(request);
    ${entityName} target = new ${entityName}();
    BeanUtils.copyProperties(request, target);
    return ${entityName?uncap_first}Repository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public Long update(${entityName}UpdateRequest request) {
    updateValidate.validate(request);
    ${entityName} target = new ${entityName}();
    BeanUtils.copyProperties(request, target);
    return ${entityName?uncap_first}Repository.saveAndFlush(target).getId();
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void delete(Long id) {
    Optional<${entityName}> byId = ${entityName?uncap_first}Repository.findById(id);
    if (byId.isPresent()) {
      ${entityName} ${entityName?uncap_first} = byId.get();
      ${entityName?uncap_first}.setDeleted(1);
      ${entityName?uncap_first}Repository.saveAndFlush(${entityName?uncap_first});
    }
  }

  @Override
  public ${entityName}Response byId(Long id) {
    Optional<${entityName}> byId = ${entityName?uncap_first}Repository.findById(id);
    if (byId.isPresent()) {
      ${entityName} ${entityName?uncap_first} = byId.get();
      ${entityName}Response target = new ${entityName}Response();
      BeanUtils.copyProperties(${entityName?uncap_first}, target);
      return target;
    }
    return null;
  }

  @Override
  public List<${entityName}Response> byIds(List<Long> ids) {
    List<${entityName}> allById = ${entityName?uncap_first}Repository.findAllById(ids);
    return allById.stream().map(s -> {
        ${entityName}Response target = new ${entityName}Response();
       BeanUtils.copyProperties(s, target);
       return target;
    }).collect(Collectors.toList());
  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void deletes(List<Long> ids) {
    List<${entityName}> allById = ${entityName?uncap_first}Repository.findAllById(ids);
    for (${entityName} ${entityName?uncap_first} : allById) {
      ${entityName?uncap_first}.setDeleted(1);
    }
    ${entityName?uncap_first}Repository.saveAllAndFlush(allById);
  }

  @Override
  public List<${entityName}Response> list(${entityName}QueryRequest request) {
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
      <#list fields as field>
        <#if field.fieldType  == "String">
          predicatesList.add(criteriaBuilder.like(root.get("${field.fieldName}"), "%"+request.${dashedToCamel("get_${field.fieldName}")}()+"%"));
        <#elseif field.range>
          List<${field.fieldType}> ${field.fieldName}s = request.${dashedToCamel("get_${field.fieldName}")}s();
          if (!CollectionUtils.isEmpty(${field.fieldName}s)) {
            predicatesList.add(criteriaBuilder.between(root.get("${field.fieldName}"), ${field.fieldName}s.get(0), ${field.fieldName}s.get(1)));
          }
        <#else >
          predicatesList.add(criteriaBuilder.equal(root.get("${field.fieldName}"), request.${dashedToCamel("get_${field.fieldName}")}()));
        </#if>
      </#list>
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    });

    return all.stream().map(s -> {
      ${entityName}Response target = new ${entityName}Response();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public PageResponse<${entityName}Response> page(${entityName}QueryRequest request,
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

    Page<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
  <#list fields as field>
      <#if field.fieldType  == "String">
        predicatesList.add(criteriaBuilder.like(root.get("${field.fieldName}"), "%"+request.${dashedToCamel("get_${field.fieldName}")}()+"%"));
      <#elseif field.range>
        List<${field.fieldType}> ${field.fieldName}s = request.${dashedToCamel("get_${field.fieldName}")}s();
        if (!CollectionUtils.isEmpty(${field.fieldName}s)) {
          predicatesList.add(criteriaBuilder.between(root.get("${field.fieldName}"), ${field.fieldName}s.get(0), ${field.fieldName}s.get(1)));
        }
      <#else >
        predicatesList.add(criteriaBuilder.equal(root.get("${field.fieldName}"), request.${dashedToCamel("get_${field.fieldName}")}()));
      </#if>
  </#list>
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
      }
    }, of);
    List<${entityName}> content = all.getContent();

    List<${entityName}Response> collect = content.stream().map(s -> {
      ${entityName}Response target = new ${entityName}Response();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());

    return new PageResponse<>(all.getTotalElements(), all.getPageable().getPageNumber(),
        all.getPageable().getPageSize(), collect);
  }

  /**
   * 创建校验
   */
  protected class ${entityName}CreateValidate implements CreateValidate<${entityName}CreateRequest> {

    @Override
    public void createValidate(${entityName}CreateRequest request)
        throws IllegalArgumentException {

    }
  }

  /**
   * 更新校验
   */
  protected class ${entityName}UpdateValidate implements UpdateValidate<${entityName}UpdateRequest> {

    @Override
    public void updateValidate(${entityName}UpdateRequest request)
        throws IllegalArgumentException {
      Long id = request.getId();
      if (id == null) {
        throw new IllegalArgumentException("idb必填");
      }
    }
  }
}