package com.ch.web.admin;


import com.ch.po.Tag;
import com.ch.po.Type;
import com.ch.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size =10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
//        利用model使前端获得一个tag值
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        //根据tag的名字从数据库中获取已存在的tag，验证tag与已知是否重名
        Tag tag1=tagService.getTagByName(tag.getName());
        if (tag1!=null){
            result.rejectValue("name","nameError","该名称不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t=tagService.updateTag(id,tag);
        if (t==null){
            attributes.addFlashAttribute("message","类型修改操作失败");

        }else{
            attributes.addFlashAttribute("message","类型修改操作成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
//    通过RedirctAttribute对象可以得到后端代码验证之后的消息，同时也需要前端页面接受这个消息
//    valid是对tag的对象中的name校验
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
//        验证是否为同名的type，如果同名则报出异常的错误提示，result会自动获得验证的消息
        Tag tag1=tagService.getTagByName(tag.getName());//将传进来的type对象的名字传进数据库中进行查询，相同名字的不可以通过，如果相同名字则type1不为空
        if (tag1!=null){
            result.rejectValue("name","nameError","该名称不能重复添加");//指定了一个nameError异常，需要自己去自定义，异常的信息自动被前端th:if="${#fields.hasErrors('name')}"处获取name相关的异常，自动提示相对应的错误
        }

//       返回一个校验的结果，是否存在空异常
//        if (result.hasErrors()){
//            return "admin/tags-input";
//        }
        if (result.hasErrors()){
            return "admin/tags-input";
        }
//        定义操作的信息，即为新增操作的成功与否
        Tag t=tagService.saveTag(tag);
        if (t==null){
            attributes.addFlashAttribute("message","类型添加操作失败");

        }else{
            attributes.addFlashAttribute("message","类型添加操作成功");
        }
        return "redirect:/admin/tags";
    }

    //tag的删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","类型删除操作成功");
        return "redirect:/admin/tags";
    }



}
