package mc3eprotocol.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class BlockWriteRequest extends AbstractRequest {
    protected byte[] _writeBytes;
    protected SeriesModelEnum _seriesModel;

    protected DeviceSpec _deviceSpec;
    protected short _devicePoint;
    protected boolean _isBitDevice;

    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
    public BlockWriteRequest(SeriesModelEnum seriesModel, byte[] request) {
        _seriesModel = seriesModel;
        this.parse(request);
    }

    /**
     * コンストラクタ ビット/ワード両対応
     * @param seriesModel
     * @param deviceSpec
     * @param devicePoint
     * @param writeBytes
     * @param isBitDevice
     */
    public BlockWriteRequest(SeriesModelEnum seriesModel, DeviceSpec deviceSpec, short devicePoint, byte[] writeBytes, boolean isBitDevice) {
        this._seriesModel = seriesModel;
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
     * 応答を解析する
     * 終了コードより後ろを渡すこと
     * @param response
     * @return
     */
    @Override
    public AbstractResponse parseResponse(byte[] responseData) {
        return new NoneResponse(responseData);
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
            _deviceSpec.toBytes(_seriesModel),
            Utility.fromIntToBytes(_devicePoint, 2),
            _writeBytes
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

        // デバイス指定
        data = new byte[DeviceCodeEnum.getLength(_seriesModel)];
        buffer.get(data);
        _deviceSpec = new DeviceSpec(_seriesModel, data);

        // ポイント数
        data = new byte[2];
        _devicePoint = buffer.getShort();

        // データ
        _writeBytes = new byte[buffer.remaining()];
        buffer.get(_writeBytes);
    }

    /**
     * リクエストの情報を表示する
     */
    @Override
    public void printInfo() {
        System.out.println(Utility.hereDoc(s->s, System.lineSeparator(), 
        //   Request payload: 00019000000100000401
            "Request payload: " + Utility.fromBytesToHexStringBigEndian(toBytes()),
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
