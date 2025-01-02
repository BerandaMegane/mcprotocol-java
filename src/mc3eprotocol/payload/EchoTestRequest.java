package mc3eprotocol.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class EchoTestRequest extends AbstractRequest {
    protected byte[] _echoTestData;

    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
    public EchoTestRequest(byte[] request, byte[] echoTestData) {
        if (request != null) {
            this.parse(request);
            return;
        }
        _command = CommandEnum.ECHO_TEST;
        _subCommand = SubCommandEnum.NONE;
        _echoTestData = echoTestData;
    }

    /**
     * 応答を解析する
     * 終了コードより後ろを渡すこと
     * @param response
     * @return
     */
    @Override
    public AbstractResponse parseResponse(byte[] responseData) {
        return new EchoTestResponse(responseData);
    }

    /**
     * 監視タイマーより後ろのバイト列に変換する
     * @return
     */
    @Override
    public byte[] toBytes() {
        return Utility.concatByteArrays(
            _command.getBytes(),
            _subCommand.getBytes(),
            Utility.fromIntToBytes(_echoTestData.length, 2),
            _echoTestData
        );
    }
    
    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
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

        // 折返しデータ数
        data = new byte[2];
        short uncheckedLength = buffer.getShort();

        // 折返しデータ
        data = new byte[uncheckedLength];
        buffer.get(data);
        _echoTestData = data;
    }
}
