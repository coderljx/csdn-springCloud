package an.Log;

import DB.Log;
import DB.Response;
import an.Http.Connection;
import com.alibaba.fastjson2.JSONObject;
import com.sun.deploy.net.HttpUtils;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

@Aspect
@Component
public class LogEsImpl {

    @Pointcut("@annotation(an.Log.LogEs)")
    public void Pointcut(){};

//    @Before("Pointcut()")
//    public void Before(JoinPoint joinPoint) throws Exception {
//
//    }
//
//    @After("Pointcut()")
//    public void After(JoinPoint joinPoint) {
//
//    }

//    @Around("Pointcut()")
//    public void Around(JoinPoint joinPoint) {
//
//    }
//

    /**
     * 在接口返回后触发，拿到本次请求的参数
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "Pointcut()",returning = "result")
    public void returng(JoinPoint joinPoint,Object result) {
        Object[] args = joinPoint.getArgs();
        Log log = new Log();
        log.setParams(Arrays.toString(args));
        if (result instanceof Response) {
            Response response = (Response) result;
            log.setResparams(response.toString());
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LogEs annotation = method.getAnnotation(LogEs.class);
        JSONObject of = JSONObject.of("url", annotation.url(), "dec", annotation.dec(), "params", Arrays.toString(args), "resparams", log.getResparams());
        Connection.httpPost(of);
    }


}
