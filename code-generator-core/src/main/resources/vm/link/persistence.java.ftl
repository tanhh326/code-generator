package ${pkg};
import ${pkg}.${entityName};


public interface MidUserRoleEntityPersistence {

    /**
    * 建立关系
    */
    void bind(${entityName}CreateRequest request);

    /**
    * 删除关系
    */
    void unBind(${entityName}CreateRequest request);

}
