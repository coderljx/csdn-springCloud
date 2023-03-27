package Prodocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Message {

    private int len;
    private byte[] data;

    public abstract byte[] MessageType();

}
