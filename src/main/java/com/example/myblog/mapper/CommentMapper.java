package com.example.myblog.mapper;

import com.example.myblog.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

/**
* @author ASUS
* @description 针对表【t_comment】的数据库操作Mapper
* @createDate 2023-08-22 20:04:27
* @Entity com.example.myblog.pojo.Comment
*/
@Component
public interface CommentMapper extends BaseMapper<Comment> {

}




