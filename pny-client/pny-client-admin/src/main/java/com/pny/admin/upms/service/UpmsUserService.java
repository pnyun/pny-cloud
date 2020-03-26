package com.pny.admin.upms.service;

import cn.hutool.json.JSONUtil;
import com.pny.admin.upms.provider.UpmsUserClient;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author pmyun
 */
@Service
public class UpmsUserService {

    @Autowired
    private UpmsUserClient upmsUserClient;

    @Autowired
    private RedisTemplate redisTemplate;

    public SystemUserDto loadUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }

        String key = "sysUser:" + username;

        Object value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return JSONUtil.toBean(value.toString(), SystemUserDto.class);
        }

        synchronized (key) {
            value = redisTemplate.opsForValue().get(key);

            if (value != null) {
                return JSONUtil.toBean(value.toString(), SystemUserDto.class);
            }

            SystemUserDto upmsUser = upmsUserClient.loadUserByUsername(username).getContent();

            if (upmsUser != null) {
                redisTemplate.opsForValue()
                    .set(key, JSONUtil.toJsonStr(upmsUser), 5, TimeUnit.MINUTES);
            }

            return upmsUser;
        }
    }

}
