package mc3eprotocol.lib.payload;

import java.nio.*;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * PLC形名読取り要求クラス
 * PLCのCPU形名を読み出す要求を行うクラスです。
 * 接続されているPLCの機種情報を取得するために使用されます。
 */
public class PlcTypeNameRequest extends AbstractRequest {

    /**
     * コンストラクタ
     * PLC形名読取り要求オブジェクトを初期化します。
     */
    public PlcTypeNameRequest() {
        _command = CommandEnum.CPU_TYPE_READ;
        _subCommand = SubCommandEnum.Q_WORD;
    }

    /**
     * 応答データを解析してPLC形名応答オブジェクトを生成します。
     *
     * @param response 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    @Override
    public AbstractResponse parseResponse(byte[] response) {
        return new PlcTypeNameResponse(response, this);
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
            _subCommand.getBytes().getByteArray()
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
