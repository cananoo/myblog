package com.example.myblog.service;



import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.ui.Model;

import java.util.List;


/**
* @author ASUS
* @description 针对表【t_type】的数据库操作Service
* @createDate 2023-08-22 20:04:03
*/
public interface TypeService extends IService<Type> {

    //新增类型
    int saveType(Type type);

    //根据id查询类型
    Type getTypeById(long id);

    //根据id修改类型名称
    int updateTypeById(long id,String name);

    //根据id删除类型
    int deleteTypeById(long id);

    //返回固定页面的数据
    List<Type> findPage(Page<Type> page);

    //根据name返回类型
    Type findTypeByName(String name);

    //查找所有分类
    List<Type> findAllType();

}
