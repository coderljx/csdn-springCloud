package Pojo.LjxUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadUtils {

    private static ExecutorService executors = null;
    private static final int size = 100;

    static {
        executors = Executors.newFixedThreadPool(size);
    }


    public static void init(int threds) {
        executors = Executors.newFixedThreadPool(threds);
    }


    public static void main(String[] args) {

    }

}
