package com.pny.core.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.pny.core.entity.BaseEntity;
import com.pny.util.YunHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 常用基础数据组件
 * @author pmyun
 */
public abstract class CompentController<T extends IService, S extends BaseEntity> extends CommonController<T , S> {

    @GetMapping("list")
    public YunHttpResponse<List<S>> list(S entity){
        return YunHttpResponse.success(baseService.selectList(covertWrapper(entity))) ;
    }

    /**
     * 查询条件 默认按照ID 倒序
     * @param entity
     * @return
     */
    protected EntityWrapper covertWrapper(S entity){
        EntityWrapper entityWrapper = new EntityWrapper(entity) ;
        entityWrapper.orderBy(true , "id" , false) ;
        return entityWrapper ;
    }

}
