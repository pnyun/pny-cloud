package com.pny.core.base;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.pny.core.entity.BaseEntity;
import com.pny.core.entity.CommonQuery;
import com.pny.core.entity.PageResult;
import com.pny.core.util.PageUtil;
import com.pny.util.YunHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author pmyun
 */
public abstract class PageController<T extends IService, S extends BaseEntity>
        extends CompentController<T, S> {

    @GetMapping("page")
    public YunHttpResponse<PageResult<S>> page(CommonQuery query, S entity){

        Page<S> page = PageUtil.of(query.getPageIndex(), query.getPageSize()) ;

        EntityWrapper entityWrapper = covertWrapper(entity) ;
        Page result = baseService.selectPage(page, entityWrapper);
        PageResult pageResult = new PageResult();
        pageResult.setCurrent(result.getCurrent());
        pageResult.setRows(result.getRecords());
        pageResult.setTotal(result.getTotal());
        return YunHttpResponse.success(pageResult) ;
    }


}
