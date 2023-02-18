<#function camelToDashed(s)>
    <#return s
    ?replace('([a-z])([A-Z])', '$1_$2', 'r')
    ?replace('([A-Z])([A-Z][a-z])', '$1_$2', 'r')
    ?lower_case
    >
</#function>

package ${pkg}.entity;

<#list  imports as impt>
import ${impt};
</#list>
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "${tableName}")
@Getter
@Setter
@com.baomidou.mybatisplus.annotation.TableName("${tableName}")
public class ${entityName} {


<#list  fields as field>
    /**
     * ${field.fieldDesc}
     **/
    @Column(name = "${camelToDashed("${field.fieldName}")}")
    @com.baomidou.mybatisplus.annotation.TableField(value = "${camelToDashed("${field.fieldName}")}")
    private ${field.fieldType}  ${field.fieldName};
</#list>



    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @com.baomidou.mybatisplus.annotation.TableId(type = com.baomidou.mybatisplus.annotation.IdType.AUTO)
    private Long id;
    @com.baomidou.mybatisplus.annotation.TableLogic
    @Column(name = "deleted")
    @com.baomidou.mybatisplus.annotation.TableField(value = "deleted")
    private Integer deleted;
    @Column(name = "customer")
    @com.baomidou.mybatisplus.annotation.TableField(value = "customer")
    private Boolean customer;
    @Column(name = "version")
    @com.baomidou.mybatisplus.annotation.TableField(value = "version")
    @Version
    @com.baomidou.mybatisplus.annotation.Version
    private Long version = 0L;
    @com.baomidou.mybatisplus.annotation.TableField(value = "create_time",fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @com.baomidou.mybatisplus.annotation.TableField(value = "update_time",fill = com.baomidou.mybatisplus.annotation.FieldFill.UPDATE)
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "create_user_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "create_user_id",fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private String createUserId;
    @Column(name = "update_user_id")
    @com.baomidou.mybatisplus.annotation.TableField(value = "update_user_id",fill =com.baomidou.mybatisplus.annotation.FieldFill.UPDATE)
    private String updateUserId;

}