package com.pny.server.upms.controller;



import com.pny.core.base.PageController;
import com.pny.core.entity.PageResult;
import com.pny.exception.Checks;
import com.pny.exception.PnyYunException;
import com.pny.server.enums.CommonStatusEnum;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.server.upms.entity.ex.SystemUserQuery;
import com.pny.server.upms.service.ISystemUserService;
import com.pny.util.MD5;
import com.pny.util.YunHttpResponse;
import java.util.Date;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> cms用户表 前端控制器 </p>
 *
 * @author pmyun
 * @since 2019-04-25
 */
@RestController
@RequestMapping("upms/systemUser")
public class SystemUserController extends
    PageController<ISystemUserService, SystemUser> {

    /**
     * 根据账户获取当前用户
     * @param username
     * @return
     */
    @GetMapping("loadUserByUsername")
    public YunHttpResponse<SystemUserDto> loadUserByUsername(String username) {
        Checks.checkParamNotNull("账号",username);
        return YunHttpResponse.success(baseService.loadUserByUsername(username));
    }


    @Override
    protected SystemUser beforeSave(SystemUser entity){
        Checks.checkParamNotNull("账号",entity.getAccountId());
        SystemUser systemUser = baseService.findByAccountId(entity.getAccountId());
        if(systemUser!=null){
            throw new PnyYunException("账号已存在");
        }
        Date currentTime = new Date();
        entity.setCreateTime(currentTime);
        entity.setUpdateTime(currentTime);
        String currentPassword = "";
        entity.setPassword(currentPassword);
        if (entity.getStatus() == null) {
            entity.setStatus(CommonStatusEnum.ENABLED.getValue());
        }
        return entity;
    }

    /**
     * 带其他实体的分页查询
     * @param query
     * @return
     */
    @GetMapping("findByPage")
    public YunHttpResponse<PageResult<SystemUserDto>> findByPage(SystemUserQuery query){
        return YunHttpResponse.success(baseService.findByPage(query)) ;
    }
}

