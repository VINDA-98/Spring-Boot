package top.weidaboy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 5809782578272943999L;
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String major;
    private Integer team;
}
