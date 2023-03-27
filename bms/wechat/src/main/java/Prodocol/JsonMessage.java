package Prodocol;

public class JsonMessage extends Message{

    public JsonMessage(int len, byte[] data){
        super(len,data);
    }

    @Override
    public byte[] MessageType() {
        return ProTo.TextType;
    }
}
