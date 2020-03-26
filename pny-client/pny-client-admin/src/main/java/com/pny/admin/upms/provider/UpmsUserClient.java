package com.pny.admin.upms.provider;

import com.pny.core.entity.PageResult;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.server.upms.entity.ex.SystemUserQuery;
import com.pny.util.YunHttpResponse;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author pmyun
 * @date 2019/4/26
 */
@FeignClient(name = "UPMS-SERVER", fallback = UpmsUserClient.HystrixClientFallback.class,
    path = "/upms/systemUser")
public interface UpmsUserClient {


    @GetMapping("findByPage")
    YunHttpResponse<PageResult<SystemUserDto>> findByPage(@SpringQueryMap SystemUserQuery param);

    @GetMapping("loadUserByUsername")
    YunHttpResponse<SystemUserDto> loadUserByUsername(@RequestParam("username") String username);

    static class HystrixClientFallback implements
        UpmsUserClient {


        @Override
        public YunHttpResponse<PageResult<SystemUserDto>> findByPage(SystemUserQuery param) {
            return null;
        }

        @Override
        public YunHttpResponse<SystemUserDto> loadUserByUsername(String username) {
            return null;
        }


    }

}
