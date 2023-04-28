package Pojo.DB;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    /**
     * status : 当前响应状态
     * msg ：   当前响应信息
     * data ：  当前响应数据
     * count ： 当前响应数据个数
     */
    private Integer status;
    private String msg;
    private T data;
    private Integer count;

    public Response(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public Response(int code, String msg, T data) {
        this(code,msg);
        this.data = data;
    }

    public Response(int code, String msg, T data, Integer count) {
        this(code,msg,data);
        this.count = count;
    }

    public Response(Coco coco) {
        this.status = coco.code;
        // 如果是系统出现了预料之外的错误，我们不能直接抛出给用户，只需要返回系统错误即可
        if (coco.code == -101) {
            this.msg = "系统错误,请联系管理员!";
        }else {
            this.msg = coco.message;
        }
    }

    public Response(Coco coco, T data) {
        this(coco);
        this.data = data;
    }

    public Response(Coco coco, T data , Integer count) {
        this(coco, data);
        this.count = count;
    }

    public Response(){
        Coco ok = Coco.ok;
        this.status = ok.code;
        this.msg = ok.message;
    }

    public Response(T data){
        this();
        this.data = data;
    }

    public Response(T data,Integer count){
        this();
        this.data = data;
        this.count = count;
    }



}