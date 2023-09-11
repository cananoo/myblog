package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Comment;
import com.example.myblog.service.CommentService;
import com.example.myblog.mapper.CommentMapper;
import com.example.myblog.util.AvatarDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @author ASUS
* @description 针对表【t_comment】的数据库操作Service实现
* @createDate 2023-08-22 20:04:27
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> getComment(Long blogId) {
        //获取顶级评论
        List<Comment> comments = new ArrayList<>();
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("blog_id",blogId);
        wrapper.isNull("parent_comment_id");
        wrapper.orderByDesc("create_time");
        List<Comment> comments1 = commentMapper.selectList(wrapper);
        for (Comment comment : comments1) {
            comments.add(comment);
        }
        return comments;
    }

    @Override
    public int saveComment(Comment comment) {
        if (comment.getParentCommentId() != -1){
            comment.setParentCommentId(comment.getParentCommentId());
        }else {
            comment.setParentCommentId(null);
        }
        comment.setCreateTime(new Date());

        int insert = commentMapper.insert(comment);


        return insert;
    }

    @Override
    public Map<Long, List<Comment>> getChidrenComment(List<Comment> comments) {
        Map<Long, List<Comment>> childrenComment = new HashMap<>();
        for (Comment comment : comments) {
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            List<Comment> children = new ArrayList<>();
            childrenComment.put(comment.getId(),children);
            List<Long> ids = new ArrayList<>();
            ids.add(comment.getId());
            while (!commentMapper.selectList(wrapper.in("parent_comment_id",ids)).isEmpty()){
                //链式查找每个评论的子评论
                List<Comment> newchildren = commentMapper.selectList(wrapper);
                //清空ids
                ids.clear();
                //把这些子评论加入到childrenComment中
                for (int i = 0; i < newchildren.size(); i++) {
                    children.add(newchildren.get(i));
                    ids.add(newchildren.get(i).getId());
                }
               wrapper.clear();
            }
        }
        return childrenComment;
    }

    //获取所有评论
    @Override
    public List<Comment> getAllComment() {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        List<Comment> comments = commentMapper.selectList(wrapper);
        return comments;
    }
}




