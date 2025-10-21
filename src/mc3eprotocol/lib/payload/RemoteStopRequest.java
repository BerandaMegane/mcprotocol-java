package mc3eprotocol.lib.payload;

import java.nio.*;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * リモートSTOP要求クラス
 * PLCをリモートでSTOP状態に移行させる要求を行うクラスです（sh082624f p. 616参照）。
 * PLCのプログラム実行を停止させるために使用されます。
 */
public class RemoteStopRequest extends AbstractRequest {

    /**
     * コンストラクタ
     * リモートSTOP要求オブジェクトを初期化します。
     */
    public RemoteStopRequest() {
        _command = CommandEnum.REMOTE_STOP;
        _subCommand = SubCommandEnum.NONE;
    }

    /**
     * 応答データを解析してSTOP応答オブジェクトを生成します。
     *
     * @param response 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new NoneResponse(response);
    }

    /**
     * 要求データをバイト配列に変換します。
     *
     * @return 要求データのバイト配列（監視タイマーより後ろ）
     */
    @Override
    public byte[] toByteArray() {
        return Converter.concatByteArrays(
            _command.getBytes().getByteArray(),
            _subCommand.getBytes().getByteArray(),
            Converter.fromHexStringToBytes("0000", 2)  // 固定値
        );
    }

    /**
     * バイト配列から要求データを解析してメンバ変数にセットします。
     *
     * @param request 要求データ（監視タイマーより後ろ）
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
    }
}
