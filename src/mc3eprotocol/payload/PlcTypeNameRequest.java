package mc3eprotocol.payload;

import java.nio.*;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class PlcTypeNameRequest extends AbstractRequest {

    public PlcTypeNameRequest() {
        _command = CommandEnum.CPU_TYPE_READ;
        _subCommand = SubCommandEnum.Q_WORD;
    }

    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new PlcTypeNameResponse(response, this);
    }

    @Override
    public byte[] toBytes() {
        return Utility.concatByteArrays(
            _command.getBytes(),
            _subCommand.getBytes()
        );
    }

    @Override
    public void parse(byte[] request) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(request);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        byte[] data;

        // コマンド
        data = new byte[2];
        buffer.get(data);
        _command = CommandEnum.build(data);

        // サブコマンド
        data = new byte[2];
        buffer.get(data);
        _subCommand = SubCommandEnum.build(data);
    }
}
