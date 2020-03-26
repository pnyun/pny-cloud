package com.pny.server.upms.mapperstruct;


import com.pny.server.upms.entity.SystemUser;
import com.pny.server.upms.entity.ex.SystemUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author pmyun
 * @date 2019/4/12 9:50
 */
@Mapper
public interface SystemUserMapperStruct {

    SystemUserMapperStruct MAPPER = Mappers.getMapper(SystemUserMapperStruct.class);


    SystemUserDto toDto(SystemUser systemUser);



}
