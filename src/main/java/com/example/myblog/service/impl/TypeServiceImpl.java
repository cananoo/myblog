package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Type;
import com.example.myblog.service.TypeService;
import com.example.myblog.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_type】的数据库操作Service实现
* @createDate 2023-08-22 20:04:03
*/
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

    @Autowired
    private  TypeMapper typeMapper;

    //新增类型
    public int saveType(Type type) {

        int insert = typeMapper.insert(type);
        return insert;
    }

    //根据id查找类型
    public Type getTypeById(long id) {
        Type type = typeMapper.selectById(id);

        return type;
    }

    //根据id修改类型
    public void updateTypeById(long id,String name) {
        UpdateWrapper<Type> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                     .set("name",name);
          typeMapper.update(null, updateWrapper);
    }

    //根据id删除类型
    public void deleteTypeById(long id) {
        typeMapper.deleteById(id);
    }

    //根据类型id升序分页查询
    public List<Type> findPage(Page<Type> page) {

        QueryWrapper<Type> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        Page<Type> typePage = typeMapper.selectPage(page, queryWrapper);
        List<Type> records = typePage.getRecords();
       return records;
    }
}




