package com.wenting.blog.controller.admin;

import com.wenting.blog.bean.Type;
import com.wenting.blog.dao.TypeDao;
import com.wenting.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;


    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }


    // 查询分类列表
    @GetMapping("/types")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }


    // 添加分类
    @PostMapping("/save")
    public String post(@Valid Type type, BindingResult bindingResult, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if (type != null){
            bindingResult.rejectValue("name", "nameError", "分类不能重复添加");
        }

        if (bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type result = typeService.saveType(type);
        if (result == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/types";
    }

    // 修改分类
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult bindingResult,@PathVariable("id") Long id, RedirectAttributes attributes){

        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){
            bindingResult.rejectValue("name", "nameError", "分类不能重复添加");
        }

        if (bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type result = typeService.updateType(id, type);
        if (result == null){
            attributes.addFlashAttribute("message", "修改失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            typeService.deleteType(id);
        }catch (Exception e){
            attributes.addFlashAttribute("message", "修改失败");
        }finally {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/types";
    }
}
