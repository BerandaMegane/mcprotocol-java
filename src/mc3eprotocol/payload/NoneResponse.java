package mc3eprotocol.payload;

public class NoneResponse extends AbstractResponse {

    public NoneResponse(byte[] response) {
        parse(response, null);
    }
    
    @Override
    protected void parse(byte[] responseData, AbstractRequest request) {
        if (responseData.length != 0) {
            throw new IllegalArgumentException("応答データが存在します");
        }
    }

    @Override
    public byte[] toBytes() {
        return new byte[]{};
    }
    
}
