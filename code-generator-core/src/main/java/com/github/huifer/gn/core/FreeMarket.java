package com.github.huifer.gn.core;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FreeMarket {

  public static void autoCodingJavaEntity(String rootPath,
      String templatePath,
      String templateName,
      String eg,
      boolean impl,
      JavaProperties javaProperties) throws IOException, TemplateException {

    // freemarker 配置
    Configuration configuration = new Configuration(
        Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

    configuration.setDefaultEncoding("UTF-8");
    // 指定模板的路径
    configuration.setDirectoryForTemplateLoading(new File(templatePath));
    // 根据模板名称获取路径下的模板
    Template template = configuration.getTemplate(templateName);
    // 处理路径问题
    final String ext = ".java";
    String javaName = javaProperties.getEntityName().concat(eg).concat(ext);
    String packageName = javaProperties.getPkg();

    if (impl) {
      packageName = packageName + ".impl";

    }


    String out = rootPath.concat(Stream.of(packageName.split("\\."))
        .collect(Collectors.joining("/", "/", "/" + javaName)));

    // 定义一个输出流来导出代码文件
    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(out));
    // freemarker 引擎将动态数据绑定的模板并导出为文件
    template.process(javaProperties, outputStreamWriter);

  }

  public static void main(String[] args) throws TemplateException, IOException {
    String rootPath = "/Users/zhangsan/git_repo/sample/basic-project/code-generator/src/main/java";
    String packageName = "com.youcon.bp.cg.go";
    String templatePath = "/Users/zhangsan/git_repo/sample/basic-project/code-generator/src/main/resources/vm";
    String commonPackage = "com.youcon.bp.cg";
    JavaProperties userEntity = new JavaProperties("UserEntity", "user", "用户", packageName,
        commonPackage);

    userEntity.addField(String.class, "username","年龄",false);
    userEntity.addField(LocalDate.class, "birthday","生日",true);
    userEntity.addField(LocalDateTime.class, "addTime","添加时间",true);
    userEntity.addField(Integer.class, "gender","年龄",true);
    userEntity.addField(Integer.class, "age","年龄",false);

    autoCodingJavaEntity(rootPath, templatePath, "entity.java.ftl","", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "mapper.java.ftl","Mapper", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "repository.java.ftl","Repository", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "create.java.ftl","CreateRequest", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "update.java.ftl","UpdateRequest", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "response.java.ftl","Response", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "query.java.ftl","QueryRequest", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "persistenceservice.java.ftl","PersistenceService", false,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "persistenceserviceimpl.java.ftl","PersistenceServiceImpl", true,userEntity);
    autoCodingJavaEntity(rootPath, templatePath, "controller.java.ftl","Controller", false,userEntity);
  }

}
