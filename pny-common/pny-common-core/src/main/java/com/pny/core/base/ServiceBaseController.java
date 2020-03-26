package com.pny.core.base;

import com.baomidou.mybatisplus.service.IService;
import com.pny.core.entity.BaseEntity;
import com.pny.core.exception.ResourceNotFoundException;

/**
 *
 */
public abstract class ServiceBaseController extends BaseController {

    /**
     * Gets a model based on its {@code id}. This method will throw
     * {@code ResourceNotFoundException} if such a model cannot be found. This
     * exception will trigger the exception handler to output property debugging
     * information for the user.
     * 
     * @param <T>
     *        the class of the model, should be a subclass of {@code HasId}.
     * @param service
     *        the service to obtain the model
     * @param id
     *        the id of a model
     * @return the valid model from database if any.
     * @throws ResourceNotFoundException
     */
    protected <T extends BaseEntity> T getModel(IService<T> service, Long id) {
        if (id < 1) {
            throw new ResourceNotFoundException("config.resource.missing", id);
        }

        T model = service.selectById(id);

        if (model == null) {
            throw new ResourceNotFoundException("config.resource.missing", id);
        }

        return model;
    }

}
