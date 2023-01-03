package an.Log;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogEs {

    String url(); // 接口的请求路径

    String dec(); // 接口的作用
}
