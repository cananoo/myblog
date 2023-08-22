package com.example.myblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.myblog.pojo.Tag;
import com.example.myblog.service.TagService;
import com.example.myblog.mapper.TagMapper;
import org.springframework.stereotype.Service;

/**
* @author ASUS
* @description 针对表【t_tag】的数据库操作Service实现
* @createDate 2023-08-22 20:04:07
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{

}




