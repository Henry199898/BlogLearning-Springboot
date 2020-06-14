package com.ch.po;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

//tag类与他的各个属性值和setter、getter方法
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id//主机id
    @GeneratedValue//生成策略
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private  String name;

    @ManyToMany(mappedBy = "tags")//被维护端建立与维护端的关系，即为括号中的内容
    private List<Blog> blogs=new ArrayList<>();

    public Tag() {
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
