package com.example.demo.common.recurrence;

/**
 * @Method 返回状态码
 * @Author zj
 * @Date 2021/7/14 14:57
 */
public class HttpStatus {
    /**
     * 操作成功
     */
    public static final int SUCCESS = 200;

    /**
     * 新建或修改数据成功
     */
    public static final int CREATED = 201;

    /**
     * 表示一个请求已经进入后台排队（异步任务）
     */
    public static final int ACCEPTED = 202;

    /**
     * 表示一个请求表单验证失败的返回
     */
    public static final int FROM = 203;

    /**
     * 删除数据成功
     */
    public static final int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    public static final int MOVED_PERM = 301;

    /**
     * 重定向
     */
    public static final int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    public static final int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    public static final int BAD_REQUEST = 400;

    /**
     * 未授权(重新登录)
     */
    public static final int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    public static final int FORBIDDEN = 403;

    /**
     * 用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的
     */
    public static final int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    public static final int BAD_METHOD = 405;

    /**
     * 用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）
     */
    public static final int NOT_ACCEPTABLE = 406;

    /**
     * 资源冲突，或者资源被锁
     */
    public static final int CONFLICT = 409;

    /**
     * 用户请求的资源被永久删除，且不会再得到的
     */
    public static final int GONE = 410;

    /**
     * 不支持的数据，媒体类型
     */
    public static final int UNSUPPORTED_TYPE = 415;

    /**
     * 当创建一个对象时，发生一个验证错误
     */
    public static final int UNPROCESABLE_ENTITY = 422;

    /**
     * 系统内部错误
     */
    public static final int ERROR = 500;

    /**
     * 接口未实现
     */
    public static final int NOT_IMPLEMENTED = 501;

    /**
     * 后台定义的错误
     */
    public static final int DEFINITION_ERROR = 705;
}
