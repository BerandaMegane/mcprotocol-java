package mc3eprotocol.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.*;
import mc3eprotocol.define.*;

public class BlockReadRequest extends AbstractRequest {
    protected SeriesModelEnum _seriesModel;
    protected DeviceSpec _deviceSpec;
    protected short _devicePoint;
    public boolean _isBitDevice;

    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
    public BlockReadRequest(SeriesModelEnum seriesModel, byte[] request) {
        _seriesModel = seriesModel;
        parse(request);
    }

    /**
     * コンストラクタ
     * @param seriesModel
     * @param deviceSpec
     * @param devicePoint
     * @param isBitDevice
     */
    public BlockReadRequest(SeriesModelEnum seriesModel, DeviceSpec deviceSpec, short devicePoint, boolean isBitDevice) {

        this._seriesModel = seriesModel;
        this._deviceSpec = deviceSpec;
        this._devicePoint = devicePoint;
        this._isBitDevice = isBitDevice;

        _command = CommandEnum.BLOCK_READ;
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
    public AbstractResponse parseResponse(byte[] response) {
        if (_isBitDevice) {
            throw new UnsupportedOperationException("Unimplemented method 'parseResponse'");
            // return new BlockBitReadResponse(response);
        } else {
            return new BlockWordReadResponse(response, this);
        }
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
            Utility.fromIntToBytes(_devicePoint, 2)
        );
    }
    
    /**
     * バイト列から解析してメンバ変数にセットする
     * 監視タイマーより後ろを渡すこと
     * @param request
     */
    @Override
    public void parse(byte[] bytes) {
        // バッファ
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
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
        buffer.get(data);
        _devicePoint = (short)Utility.fromBytesToInt(data);
    }
}
