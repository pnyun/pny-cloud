package com.pny.server.upms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.pny.core.entity.PageResult;
import com.pny.core.util.PageUtil;
import com.pny.server.enums.CommonStatusEnum;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.server.upms.entity.ex.SystemUserQuery;
import com.pny.server.upms.mapper.SystemUserMapper;
import com.pny.server.upms.mapperstruct.SystemUserMapperStruct;
import com.pny.server.upms.service.ISystemUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * cms用户表 服务实现类
 * </p>
 *
 * @author pmyun
 * @since 2019-04-25
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements ISystemUserService {




    @Override
    public SystemUser findByAccountId(String accountId){
        if(StringUtils.isEmpty(accountId)){
            return null;
        }
        EntityWrapper<SystemUser> entityWrapper = new EntityWrapper();
        entityWrapper.in("accountId", accountId);
        List<Integer> statusList = new ArrayList<>();
        statusList.add(CommonStatusEnum.ENABLED.getValue());
        statusList.add(CommonStatusEnum.DISABLED.getValue());
        entityWrapper.in("status", statusList);
        List<SystemUser> systemUserList = baseMapper.selectList(entityWrapper);
        if(CollectionUtils.isEmpty(systemUserList)){
            return null;
        }
        return systemUserList.get(0);
    }

    @Override
    public PageResult<SystemUserDto> findByPage(SystemUserQuery query) {
        Page<SystemUser> page = PageUtil.of(query.getPageIndex(), query.getPageSize());

        EntityWrapper entityWrapper = covertWrapper(query);
        Wrapper wrapper = SqlHelper.fillWrapper(page, entityWrapper);
        List<SystemUser> userList = baseMapper.selectPage(page, wrapper);
        page.setRecords(userList);

        PageResult pageResult = new PageResult();
        if (CollectionUtils.isEmpty(userList)) {
            pageResult.setTotal(0L);
            return pageResult;
        }
        List<SystemUserDto> dtos = new ArrayList<>();
        for (SystemUser user : page.getRecords()) {
            user.setPassword(null);
            SystemUserDto dto = SystemUserMapperStruct.MAPPER.toDto(user);

            dtos.add(dto);
        }
        pageResult.setTotal(page.getTotal());
        pageResult.setRows(dtos);
        return pageResult;
    }

    @Override
    public SystemUserDto loadUserByUsername(String accountId) {
        SystemUser systemUser = findByAccountId(accountId);
        if(systemUser==null){
            return null;
        }
        SystemUserDto systemUserDto = SystemUserMapperStruct.MAPPER.toDto(systemUser);
        return systemUserDto;
    }

    private EntityWrapper covertWrapper(SystemUserQuery entity) {
        EntityWrapper entityWrapper = new EntityWrapper();

        //姓名模糊查询
        if (StringUtils.isNotBlank(entity.getUsername())) {
            entityWrapper.like("username", entity.getUsername());
        }

        List<Integer> statusList = new ArrayList<>();
        statusList.add(CommonStatusEnum.ENABLED.getValue());
        statusList.add(CommonStatusEnum.DISABLED.getValue());
        entityWrapper.in("status", statusList);
        entityWrapper.orderBy(true, "createTime", false);
        return entityWrapper;
    }
}
