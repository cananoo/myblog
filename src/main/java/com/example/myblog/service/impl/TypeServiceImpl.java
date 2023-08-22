package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Type;
import com.example.myblog.service.TypeService;
import com.example.myblog.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_type】的数据库操作Service实现
* @createDate 2023-08-22 20:04:03
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




