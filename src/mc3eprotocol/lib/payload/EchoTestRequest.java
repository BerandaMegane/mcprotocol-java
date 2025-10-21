package mc3eprotocol.lib.payload;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import mc3eprotocol.lib.define.CommandEnum;
import mc3eprotocol.lib.define.SubCommandEnum;
import mc3eprotocol.lib.util.Converter;

/**
 * 折り返しテスト要求クラス
 * PLCへの接続確認のため、指定したデータをそのまま返すテストを要求します。
 * 通信経路の正常性を確認する目的で使用されます。
 */
public class EchoTestRequest extends AbstractRequest {
    /** 折り返しテスト用データ */
    protected byte[] _echoTestData;

    /**
     * コンストラクタ
     * バイト配列から要求を復元するか、新規に折り返しテスト要求を作成します。
     *
     * @param request 要求データ（監視タイマーより後ろ、nullの場合は新規作成）
     * @param echoTestData 折り返しテスト用データ
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
     * 応答データを解析して折り返しテスト応答オブジェクトを生成します。
     *
     * @param responseData 応答データ（終了コードより後ろ）
     * @return 解析された応答オブジェクト
     */
    @Override
    public AbstractResponse parseResponse(byte[] responseData) {
        return new EchoTestResponse(responseData);
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
            Converter.fromIntToByteArray(_echoTestData.length, 2),
            _echoTestData
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

        // 折返しデータ数
        data = new byte[2];
        short uncheckedLength = buffer.getShort();

        // 折返しデータ
        data = new byte[uncheckedLength];
        buffer.get(data);
        _echoTestData = data;
    }
}
