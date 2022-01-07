package demo.entities;

import javax.xml.crypto.Data;

public class BaseResponse {
    private String message;
    private int code;
    private Object data;

    public BaseResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BaseResponse() {
    }

    public BaseResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
