package com.example.myblog.service;

import com.example.myblog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author ASUS
* @description 针对表【t_comment】的数据库操作Service
* @createDate 2023-08-22 20:04:27
*/
public interface CommentService extends IService<Comment> {

    //获取一个博客的所有顶级评论
     List<Comment> getComment(Long blogId);

    //保存评论
    int saveComment(Comment comment);

    //获取一个博客的所有顶级评论的子评论，用list存储，Map<顶级评论id，子评论list>
    Map<Long,List<Comment>> getChidrenComment(List<Comment> comments);

    //获取所有评论
    List<Comment> getAllComment();

}
