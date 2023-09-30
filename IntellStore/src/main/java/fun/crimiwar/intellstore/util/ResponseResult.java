package fun.crimiwar.intellstore.util;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * 响应封装类
 *
 */
@Data
@AllArgsConstructor
public class ResponseResult {

    private boolean flag;

    private Object data;

    private String message;

    private int state;

    public ResponseResult(boolean flag,Object data,int state){
        this.data = data;
        this.flag = flag;
        this.state = state;
    }

    public ResponseResult(boolean flag,int state){
        this.data = data;
        this.flag = flag;
        this.state = state;
    }

    public ResponseResult(boolean flag,String msg,int state){
        this.data = data;
        this.flag = flag;
        this.state = state;
    }
}
