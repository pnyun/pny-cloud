package com.pny.core.base;

import com.baomidou.mybatisplus.service.IService;
import com.pny.core.entity.BaseEntity;
import com.pny.core.idwork.IdWorker;
import com.pny.exception.Checks;
import com.pny.util.YunHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 基础增删改查
 * @author pmyun
 */
public abstract class CommonController<T extends IService, S extends BaseEntity> extends BaseController {

    @Autowired
    protected T baseService ;

    @Autowired
    private IdWorker idWorker;


    /**
     * 详情信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public YunHttpResponse<S> get(@PathVariable Long id){
        return YunHttpResponse.success((S)baseService.selectById(id)) ;
    }

    /**
     * 创建或者更新
     * @param entity
     * @return
     */
    @PostMapping("saveOrUpdate")
    public YunHttpResponse<Boolean> saveOrUpdate(@RequestBody S entity){

        Checks.checkObjectNotNull("参数不能为空", entity);

        checkSaveOrUpdateParams(entity) ;

        boolean result = false ;
        if(entity.getId() == null){
            entity.setId(idWorker.nextId());
            entity = beforeSave(entity);
            result = baseService.insert(entity) ;
        }else{
            entity = beforeUpdate(entity) ;
            result = baseService.updateById(entity) ;
        }

        return YunHttpResponse.success(result) ;
    }

    /**
     * 保存前操作
     * @param entity
     * @return
     */
    protected S beforeSave(S entity){
        return entity ;
    }

    /**
     * 更新前操作
     * @param entity
     * @return
     */
    protected S beforeUpdate(S entity){
        return entity ;
    }

    /**
     * 创建 或者 修改时  必填参数校验
     * @param entity
     */
    protected void checkSaveOrUpdateParams(S entity){

    }

    /**
     * 根据ID 删除
     * @return
     */
    @DeleteMapping("{id}")
    public YunHttpResponse<Boolean> delete(@PathVariable Long id){
        Checks.checkParamNotNull("id",id);
        return YunHttpResponse.success(baseService.deleteById(id)) ;
    }

}
