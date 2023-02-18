package com.github.huifer.gn.core;

public enum TemplateEnums {
  CONTROLLER("controller.java.ftl", "Controller.java", "controller"),
  ENTITY("entity.java.ftl", ".java", "entity"),
  CreateRequest("create.java.ftl", "CreateRequest.Java", "servlet"),
  UpdateRequest("update.java.ftl", "UpdateRequest.Java", "servlet"),
  Response("response.java.ftl", "Response.Java", "servlet"),
  QueryRequest("query.java.ftl", "QueryRequest.Java", "servlet"),
  persistence("persistenceservice.java.ftl", "PersistenceService.java", "persistence"),
  persistence_impl("persistenceserviceimpl.java.ftl", "PersistenceServiceImpl.java", "persistence.impl"),

  REPOSITORY("repository.java.ftl", "Repository.java", "repository"),
  MAPPER("mapper.java.ftl", "Mapper.java", "mapper");
  private final String templateName;
  private String fileSuffix;

  private String packageName;

  TemplateEnums(String templateName, String fileSuffix, String packageName) {
    this.templateName = templateName;
    this.fileSuffix = fileSuffix;
    this.packageName = packageName;
  }

  public String getFileSuffix() {
    return fileSuffix;
  }

  public void setFileSuffix(String fileSuffix) {
    this.fileSuffix = fileSuffix;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }
}
