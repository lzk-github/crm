package com.kaishengit.dto;

/**
 * Ajax请求的返回数据
 * Created by lzk on 2017/7/17.
 */
public class AjaxResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    private String state;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    public static AjaxResult getSuccessInstance() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(SUCCESS);
        return ajaxResult;
    }

    public static AjaxResult getSuccessInstance(Object obj) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(SUCCESS);
        ajaxResult.setData(obj);
        return ajaxResult;
    }

    public static AjaxResult getErrorInstance(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(ERROR);
        ajaxResult.setMessage(message);
        return ajaxResult;
    }
    public static AjaxResult getErrorInstance() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(ERROR);
        return ajaxResult;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
