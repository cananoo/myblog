package com.example.myblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_comment
 */
@TableName(value ="t_comment")
@Data
public class Comment implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 所属博客
     */
    private Long blogId;

    /**
     * 所属父评论
     */
    private Long parentCommentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}