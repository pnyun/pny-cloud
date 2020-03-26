package com.pny.admin.upms.controller;

import com.pny.admin.base.controller.BaseController;
import com.pny.admin.upms.provider.UpmsUserClient;
import com.pny.core.entity.PageResult;
import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import com.pny.server.upms.entity.ex.SystemUserQuery;
import com.pny.util.YunHttpResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pmyun
 * @date 2019/1/2
 */
@RestController
@RequestMapping(value = {"/api/sys"})
public class UpmsUserController extends BaseController {

  @Autowired
  private UpmsUserClient baseClient;


  /**
   * 分页查询用户列表
   *
   * @param param
   * @return
   */
  @GetMapping("findByPage")
  public YunHttpResponse<PageResult<SystemUserDto>> findByPage(SystemUserQuery param) {
    return baseClient.findByPage(param);
  }




}
