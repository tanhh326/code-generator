package com.github.huifer.gn.core;

import static org.junit.jupiter.api.Assertions.*;


import com.github.huifer.gn.core.TableInfo.FieldInfo;
import com.google.common.base.CaseFormat;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class MasterMainTest {

  static String packageName = "com.youcon.bp.cg.go";
  static String commonPackage = "com.youcon.bp.cg";
  static String rootPath = "D:\\git_repo\\youcon\\code-generator\\test\\src\\main\\java";
  static String templatePath = "D:\\git_repo\\youcon\\code-generator\\code-generator-core\\src\\main\\resources\\vm";

  public static TableInfo UserInfo() {
    TableInfo tableInfo = new TableInfo();
    tableInfo.setTableName("user");
    tableInfo.setTableDesc("用户");
    ArrayList<FieldInfo> fieldInfos = new ArrayList<>();
    FieldInfo username = new FieldInfo();
    username.setType(FieldType.Varchar);
    username.setFieldDesc("用户名");
    username.setFieldName("username");
    fieldInfos.add(username);
    FieldInfo age = new FieldInfo();
    age.setFieldName("age");
    age.setFieldDesc("年龄");
    age.setRange(true);
    // 必填
    age.setType(FieldType.BigDecimal);
    fieldInfos.add(age);
    tableInfo.setFieldInfos(fieldInfos);
    return tableInfo;
  }

  public static TableInfo DeptInfo() {
    TableInfo tableInfo = new TableInfo();
    tableInfo.setTableName("dept");
    tableInfo.setTableDesc("部门");
    ArrayList<FieldInfo> fieldInfos = new ArrayList<>();
    FieldInfo username = new FieldInfo();
    username.setType(FieldType.Varchar);
    username.setFieldDesc("部门名称");
    username.setFieldName("name");
    fieldInfos.add(username);
    tableInfo.setFieldInfos(fieldInfos);
    return tableInfo;
  }

  public static LinkTableInfo link() {
    LinkTableInfo linkTableInfo = new LinkTableInfo("user", "role");
    return linkTableInfo;
  }

  @Test
  void linkTest() throws TemplateException, IOException {
    String packageName = "com.youcon.bp.cg.go.link";

    LinkTableInfo link = link();
    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        link.tableName() + "_entity");

    JavaProperties userEntity = new JavaProperties(to, link.tableName(),
        link.getDesc(), packageName,
        commonPackage);

    userEntity.addField(Long.class, link.getLeft() + "Id", "", false, link.getLeft()+ "Id",link.getRight()+ "Id");
    userEntity.addField(Long.class, link.getRight() + "Id", "", false, link.getLeft()+ "Id",link.getRight()+ "Id");

    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/entity.java.ftl", "", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/mapper.java.ftl", "Mapper", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/repository.java.ftl", "Repository",
        false, userEntity);


    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/create.java.ftl", "CreateRequest",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/persistence.java.ftl", "Persistence",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/persistence.impl.java.ftl", "PersistenceImpl",
        true, userEntity);   FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/controller.java.ftl", "Controller",
        false, userEntity);
  }

  @Test
  void singlet() throws TemplateException, IOException {
    extracted(UserInfo());
    extracted(DeptInfo());
  }

  private static void extracted(TableInfo tableInfo) throws IOException, TemplateException {
    System.out.println(Jooq.extracted(tableInfo));
    // 2. 开始生产Java代码

    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        tableInfo.getTableName() + "_entity");

    JavaProperties userEntity = new JavaProperties(to, tableInfo.getTableName(),
        tableInfo.getTableDesc(), packageName,
        commonPackage);

    for (FieldInfo fieldInfo : tableInfo.getFieldInfos()) {
      userEntity.addField(fieldInfo.getType().getClazz(), fieldInfo.getFieldName(),
          fieldInfo.getFieldDesc(), fieldInfo.isRange());

    }

    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "entity.java.ftl", "", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "mapper.java.ftl", "Mapper", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "repository.java.ftl", "Repository",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "create.java.ftl", "CreateRequest",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "update.java.ftl", "UpdateRequest",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "response.java.ftl", "Response", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "query.java.ftl", "QueryRequest", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "persistenceservice.java.ftl",
        "PersistenceService", false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "persistenceserviceimpl.java.ftl",
        "PersistenceServiceImpl", true, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "controller.java.ftl", "Controller",
        false, userEntity);
  }

}