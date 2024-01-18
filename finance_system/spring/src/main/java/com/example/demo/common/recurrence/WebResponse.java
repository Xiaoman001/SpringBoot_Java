package com.example.demo.common.recurrence;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebResponse  implements Serializable {

    private Integer code;
    private String message;
    private Object data;

    public WebResponse() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object result) {
        this.data = result;
    }
}