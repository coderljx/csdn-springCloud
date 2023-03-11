package Pojo;

import Pojo.LjxEx.TypeException;
import Pojo.LjxUtils.MapUtils;
import Pojo.LjxUtils.StringUtils;
import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchArgsMap {

    private Integer per_page;
    private Integer curr_page;
    private String search;
    private Map<String, Object> filters;
    private Map<String, Object> order1;

    private SearchArgs.ArgsItem argsItem = new SearchArgs.ArgsItem();
    private SearchArgs.Order order = new SearchArgs.Order();

    public SearchArgsMap(Map<String, Object> filters, Map<String, Object> order) {
        this.filters = filters;
        this.order1 = order;
    }

    public SearchArgsMap(String data) throws NoSuchFieldException, IllegalAccessException {
        if (StringUtils.isEmp(data)) throw new TypeException("data 不能为空");

        Map map = JSONObject.parseObject(data, Map.class);
        this.filters = (Map<String, Object>) map.get("payload");
        this.order1 = (Map<String, Object>) map.get("order");
        this.MapTpArgsItem();
    }


    /**
     * 将前端传入的map转换成bean
     *
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void MapTpArgsItem() throws NoSuchFieldException, IllegalAccessException {
        if (this.filters == null) throw new TypeException("filters 不能为空");

        Map<String, Object> filters = this.getFilters();
        if (filters.get("rules") != null) {
            List<Map<String, Object>> rules = (List<Map<String, Object>>) filters.get("rules");
            if (rules.size() == 0) return;

            List<Map<String, Object>> solr = new ArrayList<>();
            // 简版搜索
            solr.addAll(rules);
            if (solr.size() != 0) {
                argsItem.setChildren(this.GetConditionFormMaps(solr));
            }
        }
    }

    /**
     * 解析map中的参数，设置order排序
     */
    public <T> Map<String, Object> MapToOrder(Class<T> cls) {
        if (this.order == null) throw new TypeException("order 不能为空");

        Map<String, Object> order = this.order1;
        if (order.get("field") == null || order.get("order_type") == null) throw new TypeException("order 不能为空");

        String field = (String) order.get("field");
        String type = (String) order.get("order_type");
        if (!field.equals("") && !type.equals("")) {
            if (MapUtils.MapExistsBean(field, cls)) {
                this.order.setField(field);
                this.order.setOrder_type(type);
                return this.order1;
            }
        }
        throw new TypeException("order 不能为空");
    }


    /**
     * 获取Condition，从map中获取
     */
    private List<SearchArgs.Condition> GetConditionFormMaps(List<Map<String, Object>> content) throws NoSuchFieldException, IllegalAccessException {
        List<SearchArgs.Condition> list = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : content) {
            SearchArgs.Condition t = new SearchArgs.Condition();
            String[] names = MapUtils.BeanKeys(t.getClass());
            for (String name : names) {
                Field field = t.getClass().getField(name);
                if (field.getType().isInstance(stringObjectMap.get(name))) {
                    if (name.equals(field.getName())) {
                        field.set(t, stringObjectMap.get(name));
                    }
                }
            }
            list.add(t);
        }
        return list;
    }


}