package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Comment;
import com.example.myblog.service.CommentService;
import com.example.myblog.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2023-08-22 20:04:27
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




