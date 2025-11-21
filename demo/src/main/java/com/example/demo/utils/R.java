package com.example.demo.utils;

public class R<T> {
    private int code;
    private String msg;
    private T data;

    private  R(int code,String msg,T data) {
         this.code = code;
         this.msg = msg;
         this.data = data;
    }

    public static<T> R<T> success(String msg) {
        return new R<>(200,msg, null);
    }

    public static <T> R<T> success (String msg,T data){
        return new R<>(200,msg,data);
    }

    public static<T> R<T> failed(String msg) {
        return new R<>(500,msg, null);
    }

    public static<T> R<T> failed(int code,String msg) {
        return new R<>(code,msg, null);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
