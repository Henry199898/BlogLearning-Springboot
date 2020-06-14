package com.ch.web;


import com.ch.po.Type;
import com.ch.service.BlogService;
import com.ch.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 8, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id,Model model){

        List<Type> types=typeService.listType();
        if (id==-1){
            id = types.get(0).getId();
        }
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("activeTypeId", id);
        return "types";
    }




}

