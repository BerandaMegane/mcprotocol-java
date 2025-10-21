package mc3eprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Bytes;
import mc3eprotocol.lib.util.Converter;

// sh082624f p. 616
/**
 * リモートRUN要求クラス
 * PLCをリモートでRUN状態に移行させる要求を行うクラスです（sh082624f p. 616参照）。
 * 実行モードやクリアモードを指定してPLCの動作制御を行います。
 */
public class RemoteRunRequest extends AbstractRequest {

    /** 実行モード */
    private Bytes _execMode;
    /** クリアモード */
    private Bytes _clearMode;

    /** 強制実行モード */
    public static final Bytes FORCE_EXEC   = new Bytes("0003");
    /** 非強制実行モード */
    public static final Bytes UNFORCE_EXEC = new Bytes("0001");
    /** デバイス未クリアモード */
    public static final Bytes DEVICE_UNCLEAR = new Bytes("00");

    /**
     * コンストラクタ
     * 実行モードとクリアモードを指定してリモートRUN要求オブジェクトを作成します。
     * 
     * @param execMode 実行モード（FORCE_EXEC または UNFORCE_EXEC）
     * @param clearMode クリアモード（DEVICE_UNCLEAR等）
     */
    public RemoteRunRequest(Bytes execMode, Bytes clearMode) {
        _command = CommandEnum.REMOTE_RUN;
        _subCommand = SubCommandEnum.NONE;
        _execMode = execMode;
        _clearMode = clearMode;
    }

    /**
     * 応答データを解析してRUN応答オブジェクトを生成します。
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
            _execMode.getByteArray(),   // 実行モード
            _clearMode.getByteArray(),  // クリアモード
            Converter.fromHexStringToBytes("00", 1)  // 固定値
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

        // モード
        data = new byte[2];
        buffer.get(data);
        _execMode = new Bytes(data);

        // クリア
        data = new byte[1];
        buffer.get(data);
        _clearMode = new Bytes(data);
    }
}
