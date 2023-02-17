package com.github.huifer.gn.core;

import com.github.huifer.gn.core.TableInfo.FieldInfo;
import com.google.common.base.CaseFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class MasterMainTest {

  static String packageName = "com.youcon.bp.cg.go";
  static String commonPackage = "com.youcon.bp.cg";
//  static String rootPath = "D:\\git_repo\\youcon\\code-generator\\test\\src\\main\\java";
  static String rootPath = "/Users/zhangsan/git_repo/code-generator/test/src/main/java";
//  static String templatePath = "D:\\git_repo\\youcon\\code-generator\\code-generator-core\\src\\main\\resources\\vm";
  static String templatePath = "/Users/zhangsan/git_repo/code-generator/code-generator-core/src/main/resources/vm";

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
    FieldInfo password = new FieldInfo();
    password.setFieldName("password");
    password.setFieldDesc("密码");
    password.setType(FieldType.Varchar);
    fieldInfos.add(password);
    FieldInfo email = new FieldInfo();
    email.setFieldDesc("邮箱");
    email.setFieldName("email");
    email.setType(FieldType.Varchar);
    fieldInfos.add(email);
    FieldInfo phone = new FieldInfo();
    phone.setFieldName("phone");
    phone.setFieldDesc("手机");
    email.setType(FieldType.Varchar);
    tableInfo.setFieldInfos(fieldInfos);
    return tableInfo;
  }


  public static TableInfo companyInfo() {
    TableInfo tableInfo = new TableInfo();
    tableInfo.setTableName("company");
    tableInfo.setTableDesc("单位");
    tableInfo.setMid(false);
    ArrayList<FieldInfo> fieldInfos = new ArrayList<>();

    FieldInfo name = new FieldInfo();
    name.setType(FieldType.Varchar);
    name.setFieldDesc("单位名称");
    name.setFieldName("name");
    fieldInfos.add(name);
    FieldInfo pid = new FieldInfo();
    pid.setFieldName("pid");
    pid.setFieldDesc("父id");
    pid.setType(FieldType.Long);
    fieldInfos.add(pid);

    FieldInfo logo = new FieldInfo();
    logo.setType(FieldType.Varchar);
    logo.setFieldDesc("图标");
    logo.setFieldName("logo");
    fieldInfos.add(logo);

    FieldInfo address = new FieldInfo();
    address.setType(FieldType.Varchar);
    address.setFieldDesc("地址");
    address.setFieldName("address");
    fieldInfos.add(address);
    tableInfo.setFieldInfos(fieldInfos);

    return tableInfo;
  }

  public static TableInfo postInfo() {
    TableInfo tableInfo = new TableInfo();
    tableInfo.setTableName("post");
    tableInfo.setTableDesc("岗位");
    tableInfo.setMid(false);
    ArrayList<FieldInfo> fieldInfos = new ArrayList<>();
    FieldInfo name = new FieldInfo();
    name.setType(FieldType.Varchar);
    name.setFieldDesc("岗位名称");
    name.setFieldName("name");
    fieldInfos.add(name);
    tableInfo.setFieldInfos(fieldInfos);

    return tableInfo;
  }


  public static TableInfo DeptInfo() {
    TableInfo tableInfo = new TableInfo();
    tableInfo.setTableName("dept");
    tableInfo.setTableDesc("部门");
    ArrayList<FieldInfo> fieldInfos = new ArrayList<>();
    FieldInfo name = new FieldInfo();
    name.setType(FieldType.Varchar);
    name.setFieldDesc("部门名称");
    name.setFieldName("name");
    fieldInfos.add(name);
    FieldInfo companyId = new FieldInfo();
    companyId.setFieldName("companyId");
    companyId.setFieldDesc("单位id");
    companyId.setType(FieldType.Long);
    companyId.setFk(true);
    fieldInfos.add(companyId);
    FieldInfo pid = new FieldInfo();
    pid.setFieldName("pid");
    pid.setFieldDesc("父id");
    pid.setType(FieldType.Long);
    pid.setPid(true);
    fieldInfos.add(pid);
    FieldInfo leader = new FieldInfo();
    leader.setFieldName("leader");
    leader.setFieldDesc("领导人");
    leader.setType(FieldType.Long);
    fieldInfos.add(leader);
    tableInfo.setFieldInfos(fieldInfos);
    return tableInfo;
  }


  private static void extracted(TableInfo tableInfo) throws IOException, TemplateException {
    System.out.println(Jooq.extracted(tableInfo));
    // 2. 开始生产Java代码

    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        tableInfo.getTableName() + "_entity");

    JavaProperties userEntity = new JavaProperties(to, tableInfo.getTableName(),
        tableInfo.getTableDesc(), packageName,
        commonPackage, commonPackage);

    for (FieldInfo fieldInfo : tableInfo.getFieldInfos()) {
      userEntity.addField(fieldInfo.getType().getClazz(), fieldInfo.getFieldName(),
          fieldInfo.getFieldDesc(), fieldInfo.isRange(), fieldInfo.isFk(),fieldInfo.isPid(),
          fieldInfo.isEditor(), fieldInfo.isTableShow(),fieldInfo.isQuery());
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


    stepVue(userEntity, "api.ts.ftl", tableInfo.getTableName()+"Api.ts");
    stepVue(userEntity, "index.vue.ftl", "index.vue");


  }
  static String vueTemplatePath;
 static String vueExportPath = "/Users/zhangsan/git_repo/code-generator/hello-arco-pro/src/views/generator";

  static {
    ClassLoader classLoader = CoreSAm.class.getClassLoader();
    URL vm = classLoader.getResource("vm");
    URL vmVue = classLoader.getResource("vmvue");
    vueTemplatePath = vmVue.getFile();
  }

  static String stepVue(JavaProperties javaProperties, String templateName, String eg)
      throws IOException, TemplateException {

    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(vueTemplatePath ));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);

    StringWriter sw = new StringWriter();
    template.process(javaProperties, sw);
    String s = sw.toString();
    String concat = vueExportPath.concat(String.valueOf(File.separatorChar))
        .concat(javaProperties.getTableName()).concat(String.valueOf(File.separatorChar));
    File file = new File(concat);
    if (!file.exists()) {
      file.mkdirs();
    }
    String out = concat.concat(eg);

    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));

    template.process(javaProperties, outputStreamWriter);
    return s;
  }

  private static void extractedLink(String packageName, LinkTableInfo link)
      throws IOException, TemplateException {
    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        link.tableName() + "_entity");

    JavaProperties userEntity = new JavaProperties(to, link.tableName(),
        link.getDesc(), packageName,
        commonPackage, "com.youcon.bp.cg.go");

    userEntity.addField(Long.class, link.getLeft() + "Id", "", false, link.getLeft() + "Id",
        link.getRight() + "Id");
    userEntity.addField(Long.class, link.getRight() + "Id", "", false, link.getLeft() + "Id",
        link.getRight() + "Id");

    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/entity.java.ftl", "", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/mapper.java.ftl", "Mapper", false,
        userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/repository.java.ftl",
        "Repository",
        false, userEntity);

    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/create.java.ftl", "CreateRequest",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/persistence.java.ftl",
        "Persistence",
        false, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/persistence.impl.java.ftl",
        "PersistenceImpl",
        true, userEntity);
    FreeMarket.autoCodingJavaEntity(rootPath, templatePath, "link/controller.java.ftl",
        "Controller",
        false, userEntity);
  }

  @Test
  void linkTest() throws TemplateException, IOException {
    String packageName = "com.youcon.bp.cg.go.link";
    LinkTableInfo userBindDept = new LinkTableInfo("user", "dept");
    extractedLink(packageName, userBindDept);
    LinkTableInfo deptBindPost = new LinkTableInfo("dept", "post");
    extractedLink(packageName, deptBindPost);
    LinkTableInfo userBindPost = new LinkTableInfo("user", "post");
    extractedLink(packageName, userBindPost);
  }

  @Test
  void singlet() throws TemplateException, IOException {
    extracted(UserInfo());
    extracted(DeptInfo());
    extracted(companyInfo());
    extracted(postInfo());
  }

}