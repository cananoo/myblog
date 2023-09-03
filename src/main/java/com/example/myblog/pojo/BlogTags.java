package com.example.myblog.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_blog_tags
 */
@TableName(value ="t_blog_tags")
@Data
public class BlogTags implements Serializable {
    /**
     * 
     */
    private Long blogsId;

    /**
     * 
     */
    private Long tagsId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}