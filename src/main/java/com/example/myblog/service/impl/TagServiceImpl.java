package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Tag;
import com.example.myblog.service.TagService;
import com.example.myblog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ASUS
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-08-22 20:04:07
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{
    @Autowired
    private TagMapper tagMapper;

    //新增标签
    public int saveTag(Tag tag) {

        int insert = tagMapper.insert(tag);
        return insert;
    }

    //根据id查找标签
    public Tag getTagById(long id) {
        Tag tag = tagMapper.selectById(id);

        return tag ;
    }

    //根据id修改标签
    public int updateTagById(long id,String name) {
        UpdateWrapper<Tag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id)
                .set("name",name);
        return tagMapper.update(null, updateWrapper);
    }

    //根据id删除标签
    public int deleteTagById(long id) {
        int i = tagMapper.deleteById(id);
        return i;
    }

    //根据标签id升序分页查询
    public List<Tag> findPage(Page<Tag> page) {

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        Page<Tag> tagPage = tagMapper.selectPage(page, queryWrapper);
        List<Tag> records = tagPage.getRecords();
        return records;
    }

    //根据name返回标签
    public Tag findTagByName(String name) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);

        Tag tag = tagMapper.selectOne(queryWrapper);
        return tag;
    }
    // 查找所有标签
    public List<Tag> findAllTag() {
        List<Tag> tags = tagMapper.selectList(null);
        return tags;
    }
}




