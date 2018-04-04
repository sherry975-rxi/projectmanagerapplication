package project.restControllers.exceptions;

import java.io.Serializable;

/**
 * This class exists to create objects that contain the Http status of the error thrown, the Error message and Instant when the error occurred
 */

public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status; //Http status of the error
    private String msg; //Error message
    private Long timeStamp; //Instant when the error occurred


    public StandardError(Integer status, String msg, Long timeStamp) {
        this.status = status;
        this.msg = msg;
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public StandardError setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public StandardError setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public StandardError setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
        return this;
    }
}
