package ${pkg}.repository;


import ${pkg}.entity.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;




@Repository
public interface ${entityName}Repository  extends JpaRepository<${entityName}, Long>, JpaSpecificationExecutor<${entityName}> {

}
