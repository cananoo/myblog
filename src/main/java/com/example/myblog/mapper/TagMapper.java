package com.example.myblog.mapper;

import com.example.myblog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
* @author ASUS
* @description 针对表【t_tag】的数据库操作Mapper
* @createDate 2023-08-22 20:04:07
* @Entity com.example.myblog.pojo.Tag
*/
@Component
public interface TagMapper extends BaseMapper<Tag> {

}




