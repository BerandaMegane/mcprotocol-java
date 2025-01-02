package mc3eprotocol.payload;

import java.nio.*;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class RemoteStopRequest extends AbstractRequest {

    public byte[] _data;

    public RemoteStopRequest() {
        _command = CommandEnum.REMOTE_STOP;
        _subCommand = SubCommandEnum.Q_WORD;
    }

    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new NoneResponse(response);
    }

    @Override
    public byte[] toBytes() {
        return Utility.concatByteArrays(
            _command.getBytes(),
            _subCommand.getBytes(),
            Utility.fromHexStringToBytes("0001", 2)  // 固定値
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

        // モード
        data = new byte[2];
        buffer.get(data);
        _data = data;
    }
}
