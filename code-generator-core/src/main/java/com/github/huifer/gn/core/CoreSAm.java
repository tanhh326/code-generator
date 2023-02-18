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
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoreSAm {

  public static final String BASE = "src.main.java";
  public static final String ext = ".java";
  static String javaTemplatePath;
  static String vueTemplatePath;

  static {
    ClassLoader classLoader = CoreSAm.class.getClassLoader();
    URL vm = classLoader.getResource("vm");
    javaTemplatePath = vm.getFile();
    URL vmVue = classLoader.getResource("vmvue");
    vueTemplatePath = vmVue.getFile();
  }

  public final String vueExportPath;
  private final String rootPath;
  private final String packageName;
  private final String module;
  private final String commonPackage;

  public CoreSAm(String rootPath, String packageName, String module, String commonPackage,
      String vueExportPath) {
    this.rootPath = rootPath;
    this.packageName = packageName;
    this.module = module;
    this.commonPackage = commonPackage;
    this.vueExportPath = vueExportPath;
  }

  public static void main(String[] args) throws IOException, TemplateException {
//    System.out.println(packageToPath("com.youkong.c"));

    String rootPath = "D:\\git_repo\\youcon\\code-generator\\test\\";
    String vueExportPath = "D:\\git_repo\\youcon\\code-generator\\hello-arco-pro\\src\\views\\generator";
    String packageName = "com.github.huifer";
    String module = "user";
    String commonPackage = "com.youcon.bp.cg";
    LinkTableInfo userBindDept = new LinkTableInfo("user", "dept");
    LinkTableInfo deptBindPost = new LinkTableInfo("dept", "post");
    LinkTableInfo userBindPost = new LinkTableInfo("user", "post");

    CoreSAm sAm = new CoreSAm(
        rootPath, packageName, module, commonPackage, vueExportPath
    );
    sAm.singlet(UserInfo());
    sAm.singlet(postInfo());
    sAm.singlet(DeptInfo());
    sAm.singlet(companyInfo());
    sAm.link(userBindDept);
    sAm.link(deptBindPost);
    sAm.link(userBindPost);


    sAm.generatorVue(UserInfo());
    sAm.generatorVue(postInfo());
    sAm.generatorVue(DeptInfo());
    sAm.generatorVue(companyInfo());

  }

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
    password.setTableShow(false);
    password.setQuery(false);
    fieldInfos.add(password);
    FieldInfo email = new FieldInfo();
    email.setFieldDesc("邮箱");
    email.setFieldName("email");
    email.setType(FieldType.Varchar);
    fieldInfos.add(email);
    FieldInfo phone = new FieldInfo();
    phone.setFieldName("phone");
    phone.setFieldDesc("手机");
    phone.setType(FieldType.Varchar);
    fieldInfos.add(phone);

    FieldInfo day = new FieldInfo();
    day.setType(FieldType.LocalDateTime);
    day.setFieldDesc("生日");
    day.setFieldName("day");
    fieldInfos.add(day);

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
    pid.setTableShow(false);
    pid.setQuery(false);
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

  public void singlet(
      TableInfo tableInfo
  ) throws IOException, TemplateException {

    step1();
    JavaProperties javaProperties = step2(tableInfo);

    step3ForSing(javaProperties, "entity.java.ftl", TemplateEnums.ENTITY);
    step3ForSing(javaProperties, "mapper.java.ftl", TemplateEnums.MAPPER);
    step3ForSing(javaProperties, "repository.java.ftl", TemplateEnums.REPOSITORY);
    step3ForSing(javaProperties, "create.java.ftl", TemplateEnums.CreateRequest);
    step3ForSing(javaProperties, "update.java.ftl", TemplateEnums.UpdateRequest);
    step3ForSing(javaProperties, "response.java.ftl", TemplateEnums.Response);
    step3ForSing(javaProperties, "query.java.ftl", TemplateEnums.QueryRequest);
    step3ForSing(javaProperties, "persistenceservice.java.ftl", TemplateEnums.persistence);
    step3ForSing(javaProperties, "persistenceserviceimpl.java.ftl", TemplateEnums.persistence_impl);
    step3ForSing(javaProperties, "controller.java.ftl", TemplateEnums.CONTROLLER);


  }

  public void link(LinkTableInfo link) throws TemplateException, IOException {
    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        link.tableName() + "_entity");

    JavaProperties userEntity = new JavaProperties(to, link.tableName(),
        link.getDesc(), packageName + "." + module + "." + "link",
        commonPackage, packageName + "." + module);

    userEntity.addField(Long.class, link.getLeft() + "Id", "", false, link.getLeft() + "Id",
        link.getRight() + "Id");
    userEntity.addField(Long.class, link.getRight() + "Id", "", false, link.getLeft() + "Id",
        link.getRight() + "Id");

    step3ForLink(userEntity, "link/entity.java.ftl", TemplateEnums.ENTITY);
    step3ForLink(userEntity, "link/mapper.java.ftl", TemplateEnums.MAPPER);
    step3ForLink(userEntity, "link/repository.java.ftl", TemplateEnums.REPOSITORY);
    step3ForLink(userEntity, "link/create.java.ftl", TemplateEnums.CreateRequest);
    step3ForLink(userEntity, "link/persistence.java.ftl", TemplateEnums.persistence);
    step3ForLink(userEntity, "link/persistence.impl.java.ftl", TemplateEnums.persistence_impl);
    step3ForLink(userEntity, "link/controller.java.ftl", TemplateEnums.CONTROLLER);
  }

  public String step3ForLink(JavaProperties javaProperties,
      String templateName, TemplateEnums templateEnums) throws IOException, TemplateException {
    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(javaTemplatePath));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);
    String pg = BASE + "." + javaProperties.getPkg() + "." + templateEnums.getPackageName();
    String javaName = javaProperties.getEntityName().concat(templateEnums.getFileSuffix());
    String out = rootPath.concat(Stream.of(pg.split("\\."))
        .collect(Collectors.joining("/", "/", "/" + javaName)));

    StringWriter sw = new StringWriter();
    template.process(javaProperties, sw);

    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));

    template.process(javaProperties, outputStreamWriter);
    return sw.toString();

  }


  public void generatorVue(TableInfo tableInfo) throws TemplateException, IOException {
    step1();
    JavaProperties javaProperties = step2(tableInfo);

    stepVue(javaProperties, "api.ts.ftl", tableInfo.getTableName() + "Api.ts");
    stepVue(javaProperties, "index.vue.ftl", "index.vue");
    stepVue(javaProperties, "add.vue.ftl", "add.vue");
  }

  public String stepVue(JavaProperties javaProperties, String templateName, String eg)
      throws IOException, TemplateException {

    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(vueTemplatePath));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);

    StringWriter sw = new StringWriter();
    template.process(javaProperties, sw);
    String s = sw.toString();
    String out = vueExportPath.concat(String.valueOf(File.separatorChar)).concat(javaProperties.getTableName()).concat(String.valueOf(File.separatorChar)) .concat(eg);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));

    template.process(javaProperties, outputStreamWriter);
    return s;
  }

  public JavaProperties step2(TableInfo tableInfo
  ) {

    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        tableInfo.getTableName() + "_entity");
    JavaProperties po = new JavaProperties(
        to,
        tableInfo.getTableName(),
        tableInfo.getTableDesc(),
        packageName + "."+module ,
        commonPackage,
        commonPackage);
    for (FieldInfo fieldInfo : tableInfo.getFieldInfos()) {
      po.addField(fieldInfo.getType().getClazz(), fieldInfo.getFieldName(),
          fieldInfo.getFieldDesc(), fieldInfo.isRange(), fieldInfo.isFk(), fieldInfo.isPid(),
          fieldInfo.isEditor(), fieldInfo.isTableShow(), fieldInfo.isQuery());
    }
    return po;
  }

  public String step3ForSing(JavaProperties javaProperties,
      String templateName, TemplateEnums templateEnums) throws IOException, TemplateException {
    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(javaTemplatePath));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);
    String pg =
        BASE + "." + javaProperties.getPkg() + "."  + templateEnums.getPackageName();
    String javaName = javaProperties.getEntityName().concat(templateEnums.getFileSuffix());
    String out = rootPath.concat(Stream.of(pg.split("\\."))
        .collect(Collectors.joining("/", "/", "/" + javaName)));

    StringWriter sw = new StringWriter();
    template.process(javaProperties, sw);

    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));

    template.process(javaProperties, outputStreamWriter);
    return sw.toString();

  }

  public void step1() {
    File rootPathFile = new File(rootPath);
    if (!rootPathFile.exists()) {
      rootPathFile.mkdirs();
    }
    generator(rootPath, packageToPath(packageName)  );
    generator(rootPath, packageToPath(commonPackage) );
    generator(rootPath, packageToPath(packageName) + "." + module);
    for (TemplateEnums value : TemplateEnums.values()) {
      generator(rootPath, packageToPath(packageName) + "." + module + "." + value.getPackageName());
    }
    generator(rootPath, packageToPath(packageName) + "." + module + ".link");
    for (TemplateEnums value : TemplateEnums.values()) {
      generator(rootPath,
          packageToPath(packageName) + "." + module + ".link" + "." + value.getPackageName());
    }
  }


  public File generator(String out, String pn) {
    File file = new File(out + packageToPath(BASE) + packageToPath(pn));
    if (!file.exists()) {
      file.mkdirs();
    }
    return file;
  }

  public String packageToPath(String packageName) {

    return Arrays.stream(packageName.split("\\."))
        .collect(Collectors.joining("/", "/", "/"));

  }

}
