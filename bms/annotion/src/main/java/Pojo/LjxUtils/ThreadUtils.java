package Pojo.LjxUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadUtils {

    private static ExecutorService executors = null;
    private static final int size = 100;

    static {
        init(size);
    }

    public static void init(int threds) {
        executors = Executors.newFixedThreadPool(threds);
    }

    /**
     * get() 方法会让当前主线程等待线程池执行完毕，然后获取执行结果
     * @param callable
     * @param <V>
     * @return
     */
    public static <V> Future<V> invoke(Callable<V> callable) {
       return executors.submit(callable);
    }


    public static void main(String[] args) throws Exception{

        Future<String> invoke = ThreadUtils.invoke(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                Thread.sleep(3000);
                return "ok";
            }
        });
        System.out.println(invoke.get());
    }

}
