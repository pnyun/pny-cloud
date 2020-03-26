package com.pny.admin.upms.controller;

import com.pny.admin.security.jwt.JwtUtil;
import com.pny.admin.security.jwt.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pmyun
 * @since 2020/3/26 17:12
 */
@Slf4j
@RestController
public class UpmsLoginController {

    /**
     * 模拟用户 登录
     */
    @RequestMapping("/login")
    public String login(User user) {
        if ("admin".equals(user.getUsername()) && "123456".equals(user.getPassword())) {
            log.info("登录成功！生成token！");
            user.setPassword("");
            String token = JwtUtil.createToken(user);
            return token;
        }
        return "";
    }
}
