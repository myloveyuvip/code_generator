package com.yuly.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuly.model.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by yuliyao on 2016/11/30.
 */
@Controller
public class IndexController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        List<Test> testList = jdbcTemplate.query("select * from test", new BeanPropertyRowMapper<Test>(Test.class));
        ObjectMapper objectMapper = new ObjectMapper();
        String result = "";
        try {
            result = objectMapper.writeValueAsString(testList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
