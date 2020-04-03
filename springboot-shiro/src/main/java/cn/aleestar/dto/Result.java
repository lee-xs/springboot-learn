package cn.aleestar.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {

    private Integer code;
    private Object data;

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }
}
