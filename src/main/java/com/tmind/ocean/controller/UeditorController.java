package com.tmind.ocean.controller;

import com.baidu.ueditor.ActionEnter;
import com.tmind.ocean.service.UeditorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用来给UEditor 提供上传功能的代码
 * Created by lijunying on 16/7/3.
 *
 * 编辑图片操作
 */


@Controller
@RequestMapping("wx-view/ued")
public class UeditorController {

    @Resource(name = "ueditorService")
    private UeditorService ueditorService;

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        String rootPath = request.getSession()
                .getServletContext().getRealPath("/");

        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/query/{key}", method = RequestMethod.GET)
    public @ResponseBody
    String queryHtmlContent(@PathVariable String key){
        return ueditorService.queryValue(key);
    }

    @RequestMapping(value="/saveChange",method = RequestMethod.POST)
    @ResponseBody
    public  String queryProductData(@RequestParam String key, @RequestParam String content){
        ueditorService.updateProductById(key, content);
        return "success";
    }
}
