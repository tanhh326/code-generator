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

    String rootPath = "/Users/zhangsan/git_repo/sample/basic-project/code-generator/src/main/java/com/youcon/bp/cg";
    String vueExportPath = "/Users/zhangsan/git_repo/code-generator/hello-arco-pro/src/views/generator";
    String packageName = "com.github.huifer";
    String module = "user";
    String commonPackage = "com.youcon.bp.cg";

    TableInfo tableInfo = UserInfo();
    CoreSAm sAm = new CoreSAm(
        rootPath, packageName, module, commonPackage, vueExportPath
    );
//    sAm.singlet(tableInfo);
    sAm.generatorVue(tableInfo);

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
    email.setType(FieldType.Varchar);
    tableInfo.setFieldInfos(fieldInfos);
    return tableInfo;
  }

  public void singlet(
      TableInfo tableInfo
  ) throws IOException, TemplateException {

    step1();
    JavaProperties javaProperties = step2(tableInfo);

    step3(javaProperties, "entity.java.ftl", false, "");
    step3(javaProperties, "mapper.java.ftl", false, "Mapper");
    step3(javaProperties, "repository.java.ftl", false, "Repository");
    step3(javaProperties, "create.java.ftl", false, "CreateRequest");
    step3(javaProperties, "update.java.ftl", false, "UpdateRequest");
    step3(javaProperties, "response.java.ftl", false, "Response");
    step3(javaProperties, "query.java.ftl", false, "QueryRequest");
    step3(javaProperties, "persistenceservice.java.ftl", false, "PersistenceService");
    step3(javaProperties, "persistenceserviceimpl.java.ftl", true, "PersistenceServiceImpl");
    step3(javaProperties, "controller.java.ftl", false, "Controller");
  }


  public void generatorVue(TableInfo tableInfo) throws TemplateException, IOException {
    step1();
    JavaProperties javaProperties = step2(tableInfo);

    stepVue(javaProperties, "api.ts.ftl", tableInfo.getTableName()+"Api.ts");
    stepVue(javaProperties, "index.vue.ftl", "index.vue");
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
    String out = vueExportPath.concat(String.valueOf(File.separatorChar)).concat(eg);
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));

    template.process(javaProperties, outputStreamWriter);
    return s;
  }

  public JavaProperties step2(TableInfo tableInfo
  ) {

    String to = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,
        tableInfo.getTableName() + "_entity");
    JavaProperties po = new JavaProperties(to, tableInfo.getTableName(),
        tableInfo.getTableDesc(), packageName,
        commonPackage, commonPackage);
    for (FieldInfo fieldInfo : tableInfo.getFieldInfos()) {
      po.addField(fieldInfo.getType().getClazz(), fieldInfo.getFieldName(),
          fieldInfo.getFieldDesc(), fieldInfo.isRange(), fieldInfo.isFk(), fieldInfo.isPid(),
          fieldInfo.isEditor(), fieldInfo.isTableShow(), fieldInfo.isQuery());
    }
    return po;
  }

  public String step3(JavaProperties javaProperties,
      String templateName, boolean impl, String eg) throws IOException, TemplateException {
    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(javaTemplatePath));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);
    String pg = javaProperties.getPkg() + "." + module;
    if (impl) {
      pg = pg + ".impl";
    }
    String javaName = javaProperties.getEntityName().concat(eg).concat(ext);

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
    generator(rootPath, packageToPath(packageName) + "." + commonPackage);
    generator(rootPath, packageToPath(packageName) + "." + module);
    generator(rootPath, packageToPath(packageName) + "." + module + ".impl");
    generator(rootPath, packageToPath(packageName) + "." + module + ".link");
    generator(rootPath, packageToPath(packageName) + "." + module + ".link.impl");
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
