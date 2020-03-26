package com.pny.server.upms.service;

import com.pny.core.entity.PageResult;
import com.pny.server.upms.entity.SystemUser;
import com.baomidou.mybatisplus.service.IService;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.server.upms.entity.ex.SystemUserQuery;

/**
 * <p>
 * cms用户表 服务类
 * </p>
 *
 * @author pmyun
 * @since 2019-04-25
 */
public interface ISystemUserService extends IService<SystemUser> {


    /**
     * 根据账户获取
     * @param accountId
     * @return
     */
    SystemUser findByAccountId(String accountId);

    /**
     * 分页查询
     * @param query
     * @return
     */
    PageResult<SystemUserDto> findByPage(SystemUserQuery query);

    /**
     * 根据账户加载用户信息
     * @param accountId
     * @return
     */
    SystemUserDto loadUserByUsername(String accountId);
}
