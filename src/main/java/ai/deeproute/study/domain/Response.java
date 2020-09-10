package ai.deeproute.study.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private String message;
    private Object body;
    private ResponseStatus status;
}
