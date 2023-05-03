package Pojo.Consumer;

import Pojo.DB.Coco;
import Pojo.DB.Response;
import Pojo.LjxEx.DataException;
import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.UUID;

public class Consumet {

    /**
     * 抽取所有的接口重复代码
     * 所有接口的逻辑
     * @param run
     * @return
     * @param <T>
     */
    public static <T> Response<?> Logic(Run<T> run) {
        Coco coco = Coco.InitCoco;
        coco.code = 200;
        coco.message = "success";
        Response<?> response;
        T resource = null;
        try {
            resource = run.run();
        } catch (TypeException message) {
            coco = UUID.ExceptionFill(message);
        } catch (DataException dataException) {
            coco.code = -102;
            coco.message = dataException.getMessage();
        } catch (Exception e) {
            coco.code = -101;
            coco.message = e.getMessage();
            e.printStackTrace();
        } finally {
            response = new Response<>(coco,resource);
        }
        return response;
    }


}
