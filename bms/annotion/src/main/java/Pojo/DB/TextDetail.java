package Pojo.DB;

import an.NotNull.NotNull;
import lombok.Data;

@Data
public class TextDetail extends Base{

    @NotNull
    private Integer userId;

    @NotNull
    private Integer textId;

    @NotNull
    private Character action;

    public TextDetail() {
    }


    public TextDetail(Integer userId, Integer textId, Character action) {
        this.userId = userId;
        this.textId = textId;
        this.action = action;
    }
}
