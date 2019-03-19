package com.hongyao.hongbao.model.bean;

import java.io.Serializable;

/**
 * Created by liaolan on 16/3/23.
 */
public class MessageHead implements Serializable {
    private StatusBean status;
    private Object result;


    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static class StatusBean implements Serializable{
        private int code;
        private String msg;

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
    }
}
