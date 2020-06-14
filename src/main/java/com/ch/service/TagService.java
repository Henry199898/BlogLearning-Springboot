package com.ch.service;

import com.ch.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    //    增
    Tag saveTag(Tag tag);
    //    根据id查
    Tag getTag(Long id);
    //    分页查询
    Page<Tag> listTag(Pageable pageable);
    List<Tag> listTag();
    List<Tag> listTagTop(Integer size);
    List<Tag> listTag(String ids);
    //    通过名称查询一个类型
    Tag getTagByName(String name);
    //    改
    Tag  updateTag(Long id,Tag tag);
    //    删除
    void deleteTag(Long id);
}
