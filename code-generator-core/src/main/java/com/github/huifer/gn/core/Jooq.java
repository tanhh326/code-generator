package com.github.huifer.gn.core;

import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.BIT;
import static org.jooq.impl.SQLDataType.LOCALDATETIME;
import static org.jooq.impl.SQLDataType.VARCHAR;

import com.github.huifer.gn.core.TableInfo.FieldInfo;
import java.util.List;
import org.jooq.CreateTableElementListStep;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;

public class Jooq {


  public static String extracted(TableInfo tableInfo) {
    DSLContext context = new DefaultDSLContext(SQLDialect.MYSQL);
    String tableName = tableInfo.getTableName();
    CreateTableElementListStep table = context.createTable(tableName);
    List<FieldInfo> fieldInfos = tableInfo.getFieldInfos();
    for (FieldInfo fieldInfo : fieldInfos) {
      CreateTableElementListStep column = table.column(fieldInfo.getFieldName(),
          fieldInfo.getType().getDt().nullable(!fieldInfo.isNul()));
    }
    setCommonField(table);
    table.comment(tableInfo.getTableDesc());
    return table.getSQL();
  }


  public static void setCommonField(CreateTableElementListStep u) {
    u.column("create_time", LOCALDATETIME);
    u.column("update_time", LOCALDATETIME);
    u.column("create_user_id", VARCHAR.length(255));
    u.column("update_user_id", VARCHAR.length(255));
    u.column("version", BIGINT.length(10));
    u.column("deleted", BIT);
    u.column("id", BIGINT).primaryKey("id");

  }



}
