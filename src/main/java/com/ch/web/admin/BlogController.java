package com.ch.web.admin;

import com.ch.po.Blog;
import com.ch.po.User;
import com.ch.service.BlogService;
import com.ch.service.TagService;
import com.ch.service.TypeService;
import com.ch.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String LIST="admin/blogs";
    private static final String REDIRECT_LIST="redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


//    当地址指向admin/blogs时展示的页面
    @GetMapping("/blogs")
    public  String blogs(@PageableDefault(size =8,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog){
//        将默认的page博客列表展示出来，用model获取后端的数据展示在前端页面上,打开页面的同时将所有的类型都渲染展示出来
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return LIST;
    }

//     当前端进行按条件search查询博客的时候，会将下方的查询结果分页展示出来，而点击上下页的时候仅仅会将下方的表单重新刷新，而不是返回一个重新渲染的blogs页面
    @PostMapping("/blogs/search")
    public  String search(@PageableDefault(size =4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,Model model,BlogQuery blog){
//        将默认的page博客列表展示出来，用model获取后端的数据展示在前端页面上
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }
//    进入新增blog页面的方法
    @GetMapping("/blogs/input")
    public String input(Model model){
        //        初始化类型与标签
        setTypeAndTag(model);
//        修改/初始化的时候拿到一个blog值展示在前端
        model.addAttribute("blog",new Blog());

        return INPUT;
    }
//    获取types和tags的一个方法
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }
    //    进入修改blog页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //        初始化类型与标签
        setTypeAndTag(model);
//        修改/初始化的时候拿到一个blog值展示在前端
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return INPUT;
    }
//    form表单处的提交触发后端的相关代码：
//    ？ 新增blog页面的信息提交？：？开始疑惑本方法添加一个全新的增blog方法或者是修改并重新保存的方法，又或者是二者均可以使用的方法。
//    答：应该是二者均适用的方法
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
//        因为blog属性中并没有设置user属性所以只能从前端取，并
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "博客新增操作失败");
            System.out.println(b.getId());
        } else {
            attributes.addFlashAttribute("message", "博客新增操作成功");
            System.out.println(b.getId());
        }

        return REDIRECT_LIST;
    }
//    博客的删除
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","类型删除操作成功");
        return REDIRECT_LIST;
    }
}
