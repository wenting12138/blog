package com.wenting.blog.controller.admin;

import com.wenting.blog.bean.Tag;
import com.wenting.blog.bean.Type;
import com.wenting.blog.service.TagService;
import com.wenting.blog.service.TypeService;
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


    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("tag", tagService.findTagById(id));
        return "admin/tag-input";
    }


    // 查询分类列表
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 5, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", tagService.getTagAll(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }


    // 添加分类
    @PostMapping("/tag/save")
    public String post(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes){

        Tag tag1 = tagService.findByName(tag.getName());

        if (tag1 != null){
            bindingResult.rejectValue("name", "nameError", "标签不能重复添加");
        }

        if (bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag result = tagService.saveTag(tag);
        if (result == null){
            attributes.addFlashAttribute("message", "操作失败");
        }else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    // 修改分类
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult bindingResult,@PathVariable("id") Long id, RedirectAttributes attributes){

        Tag type1 = tagService.findByName(tag.getName());

        if (type1 != null){
            bindingResult.rejectValue("name", "nameError", "标签不能重复添加");
        }

        if (bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag result = tagService.update(id, tag);
        if (result == null){
            attributes.addFlashAttribute("message", "修改失败");
        }else {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable("id") Long id, RedirectAttributes attributes){
        try {
            tagService.deleteTagById(id);
        }catch (Exception e){
            attributes.addFlashAttribute("message", "修改失败");
        }finally {
            attributes.addFlashAttribute("message", "修改成功");
        }
        return "redirect:/admin/tags";
    }
}
