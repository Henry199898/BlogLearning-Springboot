package com.ch.service;

import com.ch.NotFoundException;
import com.ch.dao.BlogRepository;
import com.ch.po.Blog;
import com.ch.po.Type;
import com.ch.util.MarkdownUtils;
import com.ch.util.MyBeanUtils;
import com.ch.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.ch.util.MarkdownUtils.markdownToHtmlExtensions;

@Service//service层注解，表明该类是一个service层
public class BlogServiceImpl  implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findByid(id);
    }
//返回一个经markdown转化工具转化之后的文本内容,注意不要将数据库中的content内容也更新为html格式的内容，
// 而是new一个新的BLog对象将数据库中的对象复制过来，将复制后的对象进行处理得到需要的blog对象传递到前端
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog=blogRepository.findByid(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b=new Blog();
        BeanUtils.copyProperties(blog,b);
        String content=b.getContent();
        b.setContent(markdownToHtmlExtensions(content));
        return b;
    }

    //    分页动态查询功能，重点，这里在BLogRepository中多加了一个继承父类JpaSpecificationExecutor<Blog>
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
//        一个查询博客各种属性作为关键字查询某一个博客的处理方法BlogQuery

        /*这个findAll的方法参数内容较多，第一个为传递一个新建的 Specification<Blog>()方法，第二个为pageable*/
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//        列表存储博客的查询类型
                List<Predicate> predicates=new ArrayList<>();
//                添加博客标题的查询条件
                if(!"".equals(blog.getTitle())&&blog.getTitle()!=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
//                添加博客类型的条件,通过类型的id辨识type
                if (blog.getTypeId()!=null){
                    predicates.add(cb.equal(root.<Type>get("type"),blog.getTypeId()));
                }
//                添加博客是否推荐的特征到查询的正则条件中
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
//                将最终的查询条件填充到CriteriaQuery中去
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlogTop(Integer size) {
        return null;
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query,pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId()==null){
            //设置blog的新增与更新时间
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
//      初始的时候给blog设置为0浏览量
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

//    更新博客，注意此时将更改过后的blog对象的博客传递到该方法中
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.findByid(id);
        if(b==null){
            throw new NotFoundException("该博客并不存在");
        }
//        借助BeanUtils的拷贝工具将更新信息过后的blog复制给数据库中的博客b
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
