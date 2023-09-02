package com.example.myblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.myblog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.myblog.pojo.Tag;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_tag】的数据库操作Service
* @createDate 2023-08-22 20:04:07
*/
public interface TagService extends IService<Tag> {
    //新增标签
    int saveTag(Tag Tag);

    //根据id查询标签
    Tag getTagById(long id);

    //根据id修改标签名称
    int updateTagById(long id,String name);

    //根据id删除标签
    int deleteTagById(long id);

    //返回固定页面的数据
    List<Tag> findPage(Page<Tag> page);

    //根据name返回标签
    Tag findTagByName(String name);

    //查找所有标签
    List<Tag> findAllTag();

}
