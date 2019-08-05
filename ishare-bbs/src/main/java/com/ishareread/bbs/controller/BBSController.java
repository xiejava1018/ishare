package com.ishareread.bbs.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class BBSController {
	
	@GetMapping("/index")
	public String showIndex() {
		return "index";
	}
	
    @RequestMapping("/list")
    public String list() {
        return "list";
    }
    
    @RequestMapping("/hello")
    public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
        model.addAttribute("name", name);
        return "hello";
    }

}
