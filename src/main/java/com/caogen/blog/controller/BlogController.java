package com.caogen.blog.controller;

import com.caogen.blog.dto.InfoResult;
import com.caogen.blog.entity.Blog;
import com.caogen.blog.entity.BlogType;
import com.caogen.blog.finals.ErrorFinal;
import com.caogen.blog.service.BlogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * blog interface
 *
 * @author kcaogen
 */
@RestController
@RequestMapping("/api/blog")
@Api(tags = "博客接口")
public class BlogController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BlogService blogService;

    @ApiOperation(value = "获取所有博客", notes = "查询分页数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "页码,默认为1", required = true, paramType = "query"),
            @ApiImplicitParam(name = "blogType", value = "博客类型", required = false, paramType = "query"),
            @ApiImplicitParam(name = "searchKey", value = "关键字搜索", required = false, paramType = "query")
    })
    @GetMapping
    public InfoResult getBlog(@RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
                              @RequestParam(value = "blogType", defaultValue = "", required = false) String blogType,
                              @RequestParam(value = "searchKey", defaultValue = "", required = false) String searchKey) {
        InfoResult result = new InfoResult(HttpServletResponse.SC_NO_CONTENT);
        try {
            List<Blog> blogList = blogService.getBlog(currentPage, blogType, searchKey);
            if (blogList != null && !blogList.isEmpty()) {
                result = new InfoResult(HttpServletResponse.SC_OK);
                result.setData(blogList);
            }
        } catch (Exception e) {
            result = new InfoResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.setMsg(ErrorFinal.error);
            logger.error("getBlog: " + e);
            e.printStackTrace();
        }
        return result;
    }

    @ApiOperation(value = "获取所有博客类型")
    @GetMapping("/type")
    public InfoResult getBlogType() {
        InfoResult result = new InfoResult(HttpServletResponse.SC_NO_CONTENT);
        try {
            List<BlogType> typeList = blogService.getBlogType();
            if (typeList != null && !typeList.isEmpty()) {
                result = new InfoResult(HttpServletResponse.SC_OK);
                result.setData(typeList);
            }
        } catch (Exception e) {
            result = new InfoResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            result.setMsg(ErrorFinal.error);
            logger.error("getBlogType: " + e);
            e.printStackTrace();
        }
        return result;
    }

}
