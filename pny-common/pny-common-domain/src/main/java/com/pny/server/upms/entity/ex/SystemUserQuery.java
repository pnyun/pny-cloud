package com.pny.server.upms.entity.ex;

import com.pny.core.entity.CommonQuery;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

/**
 * @author: pmyun
 */
@Data
@ToString
public class SystemUserQuery extends CommonQuery {

    @Size(max = 30,message = "用户名最大长度为30字符")
    private String username;

}
