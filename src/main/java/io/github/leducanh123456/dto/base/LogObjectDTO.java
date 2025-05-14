package io.github.leducanh123456.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("asctime")
    private String ascTime;

    private String clientMessageId;

    private Long duration;

    @JsonProperty("exc_info")
    private String excInfo;

    @JsonProperty("exc_text")
    private String excText;

    @JsonProperty("filename")
    private String fileName;

    private String funcName;

    private String host;

    @JsonProperty("host_ip")
    private String hostIp;

    @JsonProperty("levelname")
    private String levelName;

    private String lineno;

    @JsonProperty("logtype")
    private String logType;

    private String message;

    private String method;

    private String module;

    private String msg;

    private String name;

    private String path;

    @JsonProperty("pathname")
    private String pathName;

    private Long process;

    private String processName;

    @JsonProperty("responsecode")
    private String responseCode;

    private String responseContent;

    private String requestContent;

    private String responseMsg;

    @JsonProperty("servicename")
    private String serviceName;

    @JsonProperty("sourcesapp")
    private String sourcesApp;

    @JsonProperty("stack_info")
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
