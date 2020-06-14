package com.ch.web;

import com.ch.NotFoundException;
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
import org.springframework.web.bind.annotation.RequestParam;

//返回到首页的控制器
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
//    pageage是一个提前定义好的页面大小模板（指页面共有几个元素，排列顺序是什么等等）
    public String index(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
//      在前台展示blog、types、tags这三个page类型的单子
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listType(pageable));
        model.addAttribute("tags", tagService.listTag(pageable));
//        放在右下角的推荐博客中
        model.addAttribute("recommendBlogs",blogService.listBlog(pageable));
        return "index";
    }
//    查询方法
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
//        由于sql语句的特定形式将要查询的内容用百分号包裹起来组成一个字符串，所以有如下的query转换
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
//        在查询之后还将原来的查询字符串返回到原本的输入框中，前端输入框使用th:value来接收传递的值
        model.addAttribute("query",query);
        return "search";
    }

//    博客详情页面展示
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
//        bug    未  解   决   markdown   转  化  未  html   工 具  未  成   功   所  以   在   前  端  展  现 的 博   客 详 情 还 是  一个 未 转 化 的 状 态
        model.addAttribute("blog",blogService.getAndConvert(id));
        return "blog";
    }


}


//      测试1  异常除零：
//        int i=9/0;正常的话会回到index首页页面

//      测试2  博客找不到的异常
//        String blog=null;
//        if(blog==null){
//            throw new NotFoundException("博客不存在。。。。");
//        }

//       测试3 aop切面测试
//        System.out.println(".........index..........");