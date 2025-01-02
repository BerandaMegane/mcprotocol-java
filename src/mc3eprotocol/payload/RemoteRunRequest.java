package mc3eprotocol.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.Utility;
import mc3eprotocol.define.CommandEnum;
import mc3eprotocol.define.SubCommandEnum;

public class RemoteRunRequest extends AbstractRequest {

    public byte[] _data;

    public RemoteRunRequest() {
        _command = CommandEnum.REMOTE_RUN;
        _subCommand = SubCommandEnum.NONE;
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
            Utility.fromHexStringToBytes("0003", 2),  // 強制実行
            Utility.fromHexStringToBytes("02", 1),  // クリアモード
            Utility.fromHexStringToBytes("00", 1)  // 固定値
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
