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
package ${pkg}.impl;

import ${pkg}.${entityName};
import ${pkg}.${entityName}CreateRequest;
import ${pkg}.${entityName}Mapper;
import ${pkg}.${entityName}Persistence;
import ${pkg}.${entityName}Repository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ${entityName}PersistenceImpl implements ${entityName}Persistence {

  @Autowired
  private ${entityName}Mapper ${entityName}Mapper;
  @Autowired
  private ${entityName}Repository ${entityName}Repository;

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
      ${entityName}Repository.saveAllAndFlush(list);
    }

  }

  @Transactional(rollbackFor = {Exception.class})
  @Override
  public void unBind(${entityName}CreateRequest request) {
    if (request == null) {
      return;
    }
    List<${entityName}> all = this.${entityName}Repository.findAll(
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
    for (${entityName} ${entityName} : all) {
      ${entityName}.setDeleted(1);
    }
    ${entityName}Repository.saveAllAndFlush(all);
  }
}
