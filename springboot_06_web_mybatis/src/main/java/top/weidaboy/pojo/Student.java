package top.weidaboy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 5809996678272943999L;
    private Integer id;
    private String sname;
    private String password;
    private String gid;
    private String money;
}
