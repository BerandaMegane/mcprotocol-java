package mc3eprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.DeviceCodeEnum;
import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * 一括書込み要求クラス
 * ワード単位またはビット単位でのデバイス一括書込みを要求するクラスです。
 * デバイス仕様、書込み点数、書込みデータを指定します。
 */
public class BlockWriteRequest extends AbstractRequest {
    /** 書込みデータのバイト配列 */
    protected byte[] _writeBytes;
    /** 書込み対象のデバイス仕様 */
    protected DeviceSpec _deviceSpec;
    /** 書込みデバイス点数 */
    protected short _devicePoint;
    /** ビットデバイスかどうかのフラグ */
    protected boolean _isBitDevice;

    /**
     * デバイス仕様を取得します。
     * 
     * @return デバイス仕様
     */
    public DeviceSpec getDeviceSpec() {
        return _deviceSpec;
    }

    /**
     * デバイス点数を取得します。
     * 
     * @return デバイス点数
     */
    public short getDevicePoint() {
        return _devicePoint;
    }

    /**
     * ビットデバイスかどうかを確認します。
     * 
     * @return ビットデバイスの場合はtrue、ワードデバイスの場合はfalse
     */
    public boolean isBitDevice() {
        return _isBitDevice;
    }

    /**
     * バイト配列から要求オブジェクトを復元するコンストラクタ
     * 
     * @param request 要求データ（監視タイマーより後ろ）
     */
    public BlockWriteRequest(byte[] request) {
        this.parse(request);
    }

    /**
     * パラメータ指定コンストラクタ
     * 
     * @param deviceSpec 書込み対象のデバイス仕様
     * @param devicePoint 書込みデバイス点数
     * @param writeBytes 書込みデータ
     * @param isBitDevice ビット単位書込みの場合はtrue、ワード単位書込みの場合はfalse
     */
    public BlockWriteRequest(DeviceSpec deviceSpec, short devicePoint, byte[] writeBytes, boolean isBitDevice) {
        this._deviceSpec = deviceSpec;
        this._devicePoint = devicePoint;
        this._writeBytes = writeBytes;
        this._isBitDevice = isBitDevice;

        this._command = CommandEnum.BLOCK_WRITE;
        if (_isBitDevice) {
            _subCommand = SubCommandEnum.Q_BIT;
        } else {
            _subCommand = SubCommandEnum.Q_WORD;
        }
    }

    /**
     * 書込みデータのバイト配列を取得します。
     * 
     * @return 書込みデータ
     */
    public byte[] getWriteBytes() {
        return _writeBytes;
    }

    /**
     * 応答データを解析して書込み応答オブジェクトを生成します。
     * 書込み要求の応答は通常、データを含まない正常終了応答です。
     * 
     * @param responseData 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    @Override
    public AbstractResponse parseResponse(byte[] responseData) {
        return new NoneResponse(responseData);
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
            _deviceSpec.toByteArray(),
            Converter.fromIntToByteArray(_devicePoint, 2),
            _writeBytes
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

        // デバイス指定
        data = new byte[DeviceCodeEnum.getLength()];
        buffer.get(data);
        _deviceSpec = new DeviceSpec(data);

        // ポイント数
        data = new byte[2];
        _devicePoint = buffer.getShort();

        // データ
        _writeBytes = new byte[buffer.remaining()];
        buffer.get(_writeBytes);
    }

    /**
     * リクエストの詳細情報をコンソールに表示します。
     * デバッグ用途で要求内容を確認するために使用します。
     */
    @Override
    public void printInfo() {
        System.out.println(Converter.hereDoc(s->s, System.lineSeparator(), 
        //   Request payload: 00019000000100000401
            "Request payload: " + Converter.fromBytesToHexStringBigEndian(toByteArray()),
            "                 1   2   3     4 5 6",
            "                 1: コマンド: " + _command.toString(),
            "                 2: サブコマンド: " + _subCommand.toString(),
            "                 3: デバイス番号",
            "                 4: デバイスコード",
            "                 5: デバイス点数",
            "                 6: 書込むデータ"
        ));
    }
}
