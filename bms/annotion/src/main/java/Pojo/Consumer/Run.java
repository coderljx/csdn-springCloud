package Pojo.Consumer;

public interface Run<T> {

    /**
     * 不同的接口的内部实现该逻辑，
     * T 泛型值的是需要返回的数据类型，不需要返回值的 返回 null 即可
     * @return
     * @throws Exception
     */
    T run() throws Exception;

}
