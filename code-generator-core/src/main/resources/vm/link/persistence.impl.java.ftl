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
package ${pkg}.persistence.impl;

import ${lfEntPKG}.servlet.${leftAndRight.rightEntityName?cap_first}EntityResponse;
import ${lfEntPKG}.servlet.${leftAndRight.leftEntityName?cap_first}EntityResponse;
import ${lfEntPKG}.persistence.${leftAndRight.leftEntityName?cap_first}EntityPersistenceService;
import ${lfEntPKG}.persistence.${leftAndRight.rightEntityName?cap_first}EntityPersistenceService;
import ${pkg}.entity.${entityName};
import ${pkg}.servlet.${entityName}CreateRequest;
<#--import ${pkg}.${entityName}Mapper;-->
import ${pkg}.persistence.${entityName}PersistenceService;
import ${pkg}.repository.${entityName}Repository;
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
public class ${entityName}PersistenceServiceImpl implements ${entityName}PersistenceService {

<#--  @Autowired-->
<#--  private ${entityName}Mapper ${entityName?uncap_first}Mapper;-->
  @Autowired
  private ${entityName}Repository ${entityName?uncap_first}Repository;

  @Autowired
  private ${leftAndRight.leftEntityName?cap_first}EntityPersistenceService ${leftAndRight.leftEntityName}EntityPersistenceService;
  @Autowired
  private ${leftAndRight.rightEntityName?cap_first}EntityPersistenceService ${leftAndRight.rightEntityName}EntityPersistenceService;


  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void bind(${entityName}CreateRequest request) {
    if (request == null) {
      return;
    }


    List<${entityName}> list = new ArrayList<>();


    List<Long> ${leftAndRight.leftName}s = request.${dashedToCamel("get_${leftAndRight.leftName}")}s();
    for (Long ${leftAndRight.leftName} : ${leftAndRight.leftName}s) {
      List<Long> ${leftAndRight.rightName}s = request.${dashedToCamel("get_${leftAndRight.rightName}")}s();

      for (Long ${leftAndRight.rightName} : ${leftAndRight.rightName}s) {
        ${entityName} ${entityName} = new ${entityName}();
        ${entityName}.${dashedToCamel("set_${leftAndRight.leftName}")}(${leftAndRight.leftName});
        ${entityName}.${dashedToCamel("set_${leftAndRight.rightName}")}(${leftAndRight.rightName});
        list.add(${entityName});
      }
    }
    if (!CollectionUtils.isEmpty(list)) {
      ${entityName?uncap_first}Repository.saveAllAndFlush(list);
    }

  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void unBind(${entityName}CreateRequest request) {
    if (request == null) {
      return;
    }
    List<${entityName}> all = per(request);
    for (${entityName} ${entityName} : all) {
      ${entityName}.setDeleted(1);
    }
    ${entityName?uncap_first}Repository.saveAllAndFlush(all);
  }

  private List<${entityName}> per(${entityName}CreateRequest request){
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
    new Specification<${entityName}>() {
      @Override
      public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
          CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicatesList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.${dashedToCamel("get_${leftAndRight.leftName}")}s())) {
          predicatesList.add(
              criteriaBuilder.in(root.get("${leftAndRight.leftName}")).value(request.${dashedToCamel("get_${leftAndRight.leftName}")}s()));
        }
        if (!CollectionUtils.isEmpty(request.${dashedToCamel("get_${leftAndRight.rightName}")}s())) {
          predicatesList.add(
            criteriaBuilder.in(root.get("${leftAndRight.rightName}")).value(request.${dashedToCamel("get_${leftAndRight.rightName}")}s()));
        }

        Predicate[] predicates = new Predicate[predicatesList.size()];
        return criteriaBuilder.and(predicatesList.toArray(predicates));

      }
    });
    return all;
  }

  @Override
  public List<${leftAndRight.rightEntityName?cap_first}EntityResponse> findByLeftIds(${entityName}CreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> ${leftAndRight.leftName}s = request.${dashedToCamel("get_${leftAndRight.leftName}")}s();
    if (CollectionUtils.isEmpty(${leftAndRight.leftName}s)) {
      return Collections.emptyList();
    }
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
        new Specification<${entityName}>() {
          @Override
          public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicatesList = new ArrayList<>();
              if (!CollectionUtils.isEmpty(request.${dashedToCamel("get_${leftAndRight.leftName}")}s())) {
                predicatesList.add(
                  criteriaBuilder.in(root.get("${leftAndRight.leftName}")).value(request.${dashedToCamel("get_${leftAndRight.leftName}")}s()));
              }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));

            }
        });
    List<Long> queryIds = all.stream().map(${entityName}::${dashedToCamel("get_${leftAndRight.rightName}")})
              .toList();
    return this.${leftAndRight.rightEntityName}EntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<${leftAndRight.leftEntityName?cap_first}EntityResponse> findByRightIds(${entityName}CreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<Long> ${leftAndRight.rightName}s = request.${dashedToCamel("get_${leftAndRight.rightName}")}s();
    if (CollectionUtils.isEmpty(${leftAndRight.rightName}s)) {
      return Collections.emptyList();
    }
    List<${entityName}> all = this.${entityName?uncap_first}Repository.findAll(
        new Specification<${entityName}>() {
          @Override
          public Predicate toPredicate(Root<${entityName}> root, CriteriaQuery<?> query,
              CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicatesList = new ArrayList<>();
              if (!CollectionUtils.isEmpty(request.${dashedToCamel("get_${leftAndRight.rightName}")}s())) {
                predicatesList.add(
                  criteriaBuilder.in(root.get("${leftAndRight.rightName}")).value(request.${dashedToCamel("get_${leftAndRight.rightName}")}s()));
              }
            Predicate[] predicates = new Predicate[predicatesList.size()];
            return criteriaBuilder.and(predicatesList.toArray(predicates));

            }
        });
    List<Long> queryIds = all.stream().map(${entityName}::${dashedToCamel("get_${leftAndRight.leftName}")})
              .toList();
    return this.${leftAndRight.leftEntityName}EntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<${leftAndRight.leftEntityName?cap_first}EntityResponse> findLeftAndRightWith${leftAndRight.leftEntityName?cap_first}Entity(${entityName}CreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<${entityName}> all = per(request);
    List<Long> queryIds = all.stream().map(${entityName}::${dashedToCamel("get_${leftAndRight.leftName}")})
          .toList();
    return this.${leftAndRight.leftEntityName}EntityPersistenceService.byIds(queryIds);
  }

  @Override
  public List<${leftAndRight.rightEntityName?cap_first}EntityResponse> findLeftAndRightWith${leftAndRight.rightEntityName?cap_first}Entity(${entityName}CreateRequest request) {
    if (request == null) {
      return Collections.emptyList();
    }
    List<${entityName}> all = per(request);
    List<Long> queryIds = all.stream().map(${entityName}::${dashedToCamel("get_${leftAndRight.rightName}")})
              .toList();
    return this.${leftAndRight.rightEntityName}EntityPersistenceService.byIds(queryIds);
  }
}
