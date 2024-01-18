package com.example.demo.common.path;


/**
 * @Description 获取项目所在的绝对路径
 * @Author guanguan
 * @Date 2021/9/24 14:15
 * @Version 1.0
 */
public class PathUtil {

    /**
     * 获取项目jar包所在的绝对路径
     * @return
     */
    public String getPath() {
        String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        if (System.getProperty("os.name").contains("dows")) {
            path = path.substring(1,path.length());
        }

        if (path.contains("jar")) {
            path = path.substring(0,path.lastIndexOf("."));
            return path.substring(5,path.lastIndexOf("/"));
        }
        path = path.replace("target/classes/", "");
        return path;
    }

}
