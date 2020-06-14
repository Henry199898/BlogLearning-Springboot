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
public class AboutShowController {



    @GetMapping("/about")
    public String about(){

        return "about";
    }




}

