package top.weidaboy.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(value = "Student" , description = "学生实体类")
public class Student implements Serializable {
    @ApiModelProperty("学生ID")
    private Integer id;
    @ApiModelProperty("学生姓名")
    private String sname;
    @ApiModelProperty("学生年龄")
    private Integer sage;
    @ApiModelProperty("学生密码")
    private String password;
    @ApiModelProperty("学生权限")
    private String perms;

}
