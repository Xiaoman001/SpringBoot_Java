package com.example.demo.common.recurrence;

/**
 * 接口接收和返回参数
 */
public class BaseRest {

    public BaseRest() {}

    /**
     * 增删改返回不同的状态码
     * @return
     */
    public static WebResponse buildSuccessResponse(boolean flag,String srt) {

        WebResponse webResponse = new WebResponse();
        webResponse.setData(flag);
        if (srt.equals("POST") || srt.equals("PUT") || srt.equals("PATCH")){
            webResponse.setCode(HttpStatus.CREATED);
        }else if (srt.equals("DELETE")){
            webResponse.setCode(HttpStatus.NO_CONTENT);
        }
        webResponse.setMessage("成功!");
        return webResponse;
    }


    /**
     * 错误信息返回
     * @param error
     * @return
     */
    public static WebResponse buildErrorResponse(String error) {
        WebResponse webResponse = new WebResponse();
        webResponse.setMessage(error);
        webResponse.setCode(HttpStatus.DEFINITION_ERROR);
        return webResponse;
    }

    /**
     * 查询信息返回
     * @param data
     * @return
     */
    public static WebResponse buildResponse(Object data) {
        WebResponse webResponse = new WebResponse();
        webResponse.setData(data);
        webResponse.setCode(HttpStatus.SUCCESS);
        webResponse.setMessage("成功");
        return webResponse;
    }


    /**
     * 内部定义的状态码以及错误信息返回
     * @param code
     * @param message
     * @return
     */
    public static WebResponse buildResponse(Integer code, String message) {
        WebResponse webResponse = new WebResponse();
        webResponse.setMessage(message);
        webResponse.setCode(code);
        return webResponse;
    }
}