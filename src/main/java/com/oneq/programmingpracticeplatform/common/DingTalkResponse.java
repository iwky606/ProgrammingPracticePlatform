package com.oneq.programmingpracticeplatform.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class DingTalkResponse<T> implements Serializable {
    private static final long serialVersionUID = 1340563394201259857L;
    protected boolean success;
    protected String errorCode;
    protected String errorMsg;
    protected T result;
    protected Object[] arguments;

    public static <T> DingTalkResponse<T> ok(T result){
        DingTalkResponse<T> resp = new DingTalkResponse<>();
        resp.setSuccess(true);
        resp.setResult(result);
        return resp;
    }
}
