package com.ltp.gradesubmission.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;
    private List<String> message;

    public ErrorResponse(List<String> message){
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message){
        this.message = message;
    }

    public LocalDateTime getTimeStamp(){
        return LocalDateTime.now();
    }

    public void setTimeStamp(LocalDateTime timeStamp){
        this.timeStamp = timeStamp;
    }

}
