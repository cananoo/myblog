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
 * @TableName t_blog
 */
@TableName(value ="t_blog")
@Data
public class Blog implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 首图
     */
    private String firstPicture;

    /**
     * 博客标记
     */
    private String flag;

    /**
     * 浏览数
     */
    private Integer views;

    /**
     * 开启赞赏
     */
    private Boolean appreciation;

    /**
     * 开启转载声名
     */
    private Boolean shareStatement;

    /**
     * 开启评论
     */
    private Boolean commentabled;

    /**
     * 是否发布
     */
    private Boolean published;

    /**
     * 是否推荐
     */
    private Boolean recommend;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 类型
     */
    private Long typeId;

    /**
     * 作者
     */
    private Long userId;

    /**
     * 描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}