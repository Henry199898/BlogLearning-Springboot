package com.ch.web.admin;


import com.ch.po.Type;
import com.ch.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Controller//控制器注解
@RequestMapping("/admin")//默认的地址，相当于本类中的公共地址开头
public class TypeController {


    @Autowired
    private TypeService typeService;
//简单的分页查看的处理
    @GetMapping("/types")
//    参数传递注解，其中size代表分页的类型个数；按照sort类型中的id属性排序，direction指代排序的顺序为正或者负//Model为前端展示模型
    public String types(@PageableDefault(size =10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
//       根据前端页面spring自动建立一个列表,通过model将typeService获得的页面展示出来

        model.addAttribute("page",typeService.listType(pageable));//page为一个分页对象
        return "admin/types";//一切准备就绪返回types的前端页面
    }
//    新增按钮调用的方法，弹出一个新增的前端页面
    @GetMapping("/types/input")
    public String input(Model model){
//        利用model使前端获得一个type值
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

//    通过对id进行识别，编辑分类标签，注解pathVAriable是将getmapping中的id与下面的相互对应
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }
//    与上面相邻的editInput方法相关联，编辑分类并保存，其实与下面的新增分类的方法可以公用，本代码中简单修改了下方代码
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if (type1!=null){
            result.rejectValue("name","nameError","该名称不能重复添加");
        }

        if (result.hasErrors()){
            return "admin/types-input";
        }
        Type t=typeService.updateType(id,type);
        if (t==null){
            attributes.addFlashAttribute("message","类型修改操作失败");

        }else{
            attributes.addFlashAttribute("message","类型修改操作成功");
        }
        return "redirect:/admin/types";
    }

//    新增类型并提交，其中提交按钮的反应方法用post方式与上方的types的get方法不会冲突
    @PostMapping("/types")
//    通过RedirctAttribute对象可以得到后端代码验证之后的消息，同时也需要前端页面接受这个消息
//    valid是对type的对象中的name校验
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
//        验证是否为同名的type，如果同名则报出异常的错误提示，result会自动获得验证的消息
        Type type1=typeService.getTypeByName(type.getName());//将传进来的type对象的名字传进数据库中进行查询，相同名字的不可以通过，如果相同名字则type1不为空
        if (type1!=null){
            result.rejectValue("name","nameError","该名称不能重复添加");//指定了一个nameError异常，需要自己去自定义，异常的信息自动被前端th:if="${#fields.hasErrors('name')}"处获取name相关的异常，自动提示相对应的错误
        }

//       返回一个校验的结果，是否存在空异常
        if (result.hasErrors()){
            return "admin/types-input";
        }
//        定义操作的信息，即为新增操作的成功与否
        Type t=typeService.saveType(type);
        if (t==null){
            attributes.addFlashAttribute("message","类型添加操作失败");

        }else{
            attributes.addFlashAttribute("message","类型添加操作成功");
        }
        return "redirect:/admin/types";
    }
//类型的删除
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","类型删除操作成功");
        return "redirect:/admin/types";
    }
}
