package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.entity.UserCategoryEntity;
import com.tmind.ocean.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lijunying on 15/11/22.
 */
@Controller
@RequestMapping("categoryController")
public class CategoryController {

    @Resource(name="categoryService")
    private CategoryService categoryService;

    @RequestMapping(value="/createCategory", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("UserCategoryEntity")UserCategoryEntity category, HttpServletRequest req){
        category.setUser_id(LoginController.getLoginUser(req).getUserId());
        categoryService.createCategory(category);
        return "/chanpin/chanpin2";
    }

    @RequestMapping(value = "/queryCategory", method = RequestMethod.GET)
    public @ResponseBody String queryCategory(HttpServletRequest req){
        List<UserCategoryEntity> list  = categoryService.queryCategory(LoginController.getLoginUser(req).getUserId());
        return new Gson().toJson(list);
    }


    @RequestMapping(value = "/deleteCategory/{categoryIds}",method = RequestMethod.GET)
    public @ResponseBody String deleteCategory(@PathVariable String categoryIds, HttpServletRequest req){
        String[] deleteIds = categoryIds.split(",");
        Integer userId = LoginController.getLoginUser(req).getUserId();
        boolean flag = false;
        for(String id:deleteIds){
            if(categoryService.checkCategoryUsedOrNot(Integer.parseInt(id),userId)){
                if(categoryService.deleteCategory(Integer.parseInt(id),userId)){
                    flag = true;
                }
            }else{
                    flag = false;
                    break;
            }

        }
        if(flag){
            return "success";
        }else{
            return "failed";
        }

    }

    @RequestMapping(value = "/checkCategoryExist", method = RequestMethod.POST)
    public @ResponseBody String checkCategoryExist(@RequestParam String categoryName, HttpServletRequest request){
            if(categoryService.checkCategoryNameExist(LoginController.getLoginUser(request).getUserId(),categoryName)){
                return "failed";
            }else {
                return "success";
            }
    }
}
