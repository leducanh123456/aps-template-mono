package io.github.leducanh123456.dto.base;

import io.github.leducanh123456.anotation.json.ExcludeLogFromSerialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogObjectDTO {

    private List<String> args;

    private String ascTime;

    private String clientMessageId;

    private Long duration;

    private String excInfo;

    private String excText;

    private String fileName;

    private String funcName;

    private String host;

    private String hostIp;

    private String levelName;

    private String lineno;

    private String logType;

    private String message;

    private String method;

    private String module;

    private String msg;

    private String name;

    private String path;

    private String pathName;

    private Long process;

    private String processName;

    private String responseCode;

    private String responseContent;

    private String requestContent;

    private String responseMsg;

    private String serviceName;

    private String sourcesApp;

    private String stackInfo;

    private String status;

    private Long thread;

    private String threadName;

    private Long timestamp;

    private String typeSystem;

    private String url;

    private String reqNo;

    private String username;

    private String state;

    private String data;

    private String preData;

    @ExcludeLogFromSerialization
    private long startTime;

    @ExcludeLogFromSerialization
    private long endTime;

    @ExcludeLogFromSerialization
    private Exception exception;

    @ExcludeLogFromSerialization
    private Object dataWriteLog;

    @ExcludeLogFromSerialization
    private Object dataPreWriteLog;
}
