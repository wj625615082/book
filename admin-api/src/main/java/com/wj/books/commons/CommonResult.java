package com.wj.books.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回结果
 *
 * @author wujun
 * @date 2025-04-19
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResult {

    private Integer code;
    private String message;
    private Object data;

    public CommonResult ok(){
        this.code = 0;
        return this;
    }

    public CommonResult ok(Object data) {
        this.code = 0;
        this.data = data;
        return this;
    }

    public CommonResult okPage(Page page) {
        this.code = 0;
        Map<String, Object> data = new HashMap<>(4);
        data.put("total", page.getTotalElements());
        data.put("page", page.getNumber());
        data.put("size", page.getSize());
        data.put("list", page.getContent());
        this.data = data;
        return this;
    }

    public CommonResult okList(List list) {
        this.code = 0;
        Map<String, Object> data = new HashMap<>(4);
        data.put("total", list.size());
        data.put("page", 1);
        data.put("limit", list.size());
        data.put("list", list);
        this.data = data;
        return this;
    }

    public CommonResult failed(Integer code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }

    public final static Integer PASS_ERR = 1;

}
