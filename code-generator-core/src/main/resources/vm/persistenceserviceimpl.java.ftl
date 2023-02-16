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
<#if pidField??>
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
</#if>
import java.util.Collections;
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

<#if pidField??>
  @Override
  public List<Tree<Object>> byIdsTree(List<Long> ids){
    List<${entityName}> allById = ${entityName?uncap_first}Repository.findAllById(ids);
    return tree(allById);
  }
</#if>



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

<#if pidField??>
  @Override
  public List<Tree<Object>> listTree(${entityName}QueryRequest request){
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
    return tree(all);
  }
</#if>

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

<#if hasFk>
<#list  forinKeyList as fk>
  @Override
  public List<${entityName}Response> findBy${fk.fkName?cap_first}Id(${fk.fieldType} ${fk.fieldName}){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
    new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("${fk.fieldName}"), ${fk.fieldName}));
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

<#if pidField??>
  @Override
  public List<Tree<Object>> findBy${fk.fkName?cap_first}IdTree(${fk.fieldType} ${fk.fieldName}){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
    new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("${fk.fieldName}"), ${fk.fieldName}));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return tree(all);
  }
</#if>

  @Override
  public List<${entityName}Response> findBy${fk.fkName?cap_first}Ids(List<${fk.fieldType}> ${fk.fieldName}s){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
    new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("${fk.fieldName}")).value(${fk.fieldName}s));
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
  <#if pidField??>
  
  @Override
  public List<Tree<Object>> findBy${fk.fkName?cap_first}IdsTree(List<${fk.fieldType}> ${fk.fieldName}s){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
    new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("${fk.fieldName}")).value(${fk.fieldName}s));
        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return tree(all);
  }
</#if>

</#list>
</#if>

<#if pidField??>
  /**
  * 查询树
  */
  @Override
  public List<Tree<Object>> tree(){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll();
    return tree(all);
  }

  /**
  * 构造树
  */
  @Override
  public List<Tree<Object>> tree(List<${entityName}> all){
    TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
    treeNodeConfig.setIdKey("id");
    treeNodeConfig.setParentIdKey("pid");
    List<Tree<Object>> build = TreeUtil.build(all, null, treeNodeConfig,
      new NodeParser<${entityName}, Object>() {
      @Override
      public void parse(${entityName} ${entityName?uncap_first}, Tree<Object> tree) {
        tree.setId(${entityName?uncap_first}.getId());
        tree.setParentId(${entityName?uncap_first}.get${pidField.fieldName?cap_first}());
        <#list  fields as field>
        tree.putExtra("${field.fieldName}", ${entityName?uncap_first}.get${field.fieldName?cap_first}());
        </#list>
        }
      });
    return build;
  }

  @Override
  public List<${entityName}Response> subId(${pidField.fieldType} ${pidField.fieldName}){
    if (${pidField.fieldName} ==null){
      return Collections.emptyList(); 
    }
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.equal(root.get("${pidField.fieldName}"), ${pidField.fieldName}));
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

  public List<${entityName}Response> subIds(List<${pidField.fieldType}> ${pidField.fieldName}s){
    if (CollectionUtils.isEmpty(${pidField.fieldName}s)) {
      return Collections.emptyList(); 
    }
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(new Specification<DeptEntity>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        predicatesList.add(criteriaBuilder.in(root.get("${pidField.fieldName}")).value(${pidField.fieldName}s));
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
  public List<${entityName}Response> subIdAll(${pidField.fieldType} ${pidField.fieldName}){
    if (${pidField.fieldName} == null){
      return Collections.emptyList();
    }
    List<${pidField.fieldType}> ids = new ArrayList<>();
    ids.add(${pidField.fieldName});
    List<${entityName}> child = child(ids);

    return child.stream().map(s -> {
      ${entityName}Response target = new ${entityName}Response();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }

  @Override
  public List<${entityName}Response> subIdsAll(List<${pidField.fieldType}> ${pidField.fieldName}s){
    if (CollectionUtils.isEmpty(${pidField.fieldName}s)) {
      return Collections.emptyList(); 
    }
    List<${entityName}> child = child(${pidField.fieldName}s);
    return child.stream().map(s -> {
      ${entityName}Response target = new ${entityName}Response();
      BeanUtils.copyProperties(s, target);
      return target;
    }).collect(Collectors.toList());
  }


  public List<${entityName}> child(List<${pidField.fieldType}> ${pidField.fieldName}s){
    List<${entityName}> result = new ArrayList<>();
    selectChild(result, ${pidField.fieldName}s);
    return result;
  }

  public void selectChild(List<${entityName}> result, List<${pidField.fieldType}> ${pidField.fieldName}s) {

    List<${pidField.fieldType}> temp = new ArrayList<>();
    List<${entityName}> sa = new ArrayList<>();
    for (${pidField.fieldType} ${pidField.fieldName} : ${pidField.fieldName}s) {
      List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(new Specification<DeptEntity>() {
        @Override
        public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
          List<Predicate> predicatesList = new ArrayList<>();
          predicatesList.add(criteriaBuilder.in(root.get("${pidField.fieldName}")).value(${pidField.fieldName}));
          Predicate[] predicates = new Predicate[predicatesList.size()];
          return criteriaBuilder.and(predicatesList.toArray(predicates));
        }
      });
      sa = all;
      for (${entityName} s : sa) {
        temp.add(s.getId());
        result.add(s);
      }
    }
    if (temp.size() != 0 && temp != null) {
      selectChild(result, temp);
    }
  }
</#if>

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
        throw new IllegalArgumentException("id必填");
      }
    }
  }


}