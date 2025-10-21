package mc3eprotocol.lib;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.define.DeviceSpec;
import mc3eprotocol.lib.define.DataTypeEnum;
import mc3eprotocol.lib.exception.RequestAbnormalException;
import mc3eprotocol.lib.exception.ResponseAbnormalException;
import mc3eprotocol.lib.frame.Builder;
import mc3eprotocol.lib.frame.Frame3E;
import mc3eprotocol.lib.frame.Parser;
import mc3eprotocol.lib.payload.AbnormalResponse;
import mc3eprotocol.lib.payload.AbstractRequest;
import mc3eprotocol.lib.payload.AbstractResponse;
import mc3eprotocol.lib.payload.BlockBitReadResponse;
import mc3eprotocol.lib.payload.BlockReadRequest;
import mc3eprotocol.lib.payload.BlockWordReadResponse;
import mc3eprotocol.lib.payload.BlockWriteRequest;
import mc3eprotocol.lib.payload.EchoTestRequest;
import mc3eprotocol.lib.payload.EchoTestResponse;
import mc3eprotocol.lib.payload.PlcTypeNameRequest;
import mc3eprotocol.lib.payload.PlcTypeNameResponse;
import mc3eprotocol.lib.payload.RemoteRunRequest;
import mc3eprotocol.lib.payload.RemoteStopRequest;
import mc3eprotocol.lib.util.Converter;

/**
 * MC3EプロトコルクライアントクラスPLCとの通信を行うためのメインクラスです。
 * TCP/IPソケット通信を使用してPLCへの接続、データの読み書き、制御コマンドの実行を行います。
 */
public class Client {
    /** デバッグモードフラグ */
    private boolean _isDebug;
    /** 接続先ホスト名またはIPアドレス */
    private String _host;
    /** 接続先ポート番号 */
    private int _port;

    /** TCPソケット */
    private Socket _so;
    /** 出力ストリーム */
    private OutputStream _out;
    /** 入力ストリーム */
    private BufferedInputStream _in;
    /** 受信バッファサイズ */
    private int _bufferSize = 1024;

    /** アクセスルート設定 */
    private AccessRoute _accessRoute;
    /** 監視タイマー値 */
    private short _monitorTimer;

    /** フレーム構築器 */
    Builder _builder;

    /**
     * コンストラクタ
     * MC3Eプロトコルクライアントのインスタンスを作成します。
     * 
     * @param host 接続先のホスト名またはIPアドレス
     * @param port 接続先のポート番号
     * @param isDebug デバッグモードを有効にする場合はtrue
     */
    public Client(String host, int port, boolean isDebug) {
        this._isDebug = isDebug;
        this._host = host;
        this._port = port;

        this._accessRoute = AccessRoute.DEFAULT;
        this._monitorTimer = (short)0x0001;

        this._builder = new Builder(_accessRoute);
    }

    /**
     * PLCへ安全に接続を確立します。
     * TCPソケットを作成し、入出力ストリームを初期化します。
     * @return 接続が失敗したら true を返す
     */
    public boolean connect() {
        try {
            unsafe_connect();
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
            return true;
        }
        return false;
    }

    public void unsafe_connect() throws Exception {
        _so = new Socket(_host, _port);
        _so.setSoTimeout(1000);  // タイムアウト時間
        _in = new BufferedInputStream(_so.getInputStream());
        _out = _so.getOutputStream();
    }


    /**
     * PLCとの接続を切断します。
     * 開いているストリームとソケットを適切にクローズします。
     */
    public void disconnect() {
        if(_out != null) try { _out.close(); } catch(Exception e){ e.printStackTrace(); }
        if(_in != null) try { _in.close(); } catch(Exception e){ e.printStackTrace(); }
        if(_so != null) try { _so.close(); } catch(Exception e){ e.printStackTrace(); }
    }

    /**
     * 現在の接続状態を確認します。
     * 
     * @return 接続中の場合はtrue、切断中の場合はfalse
     */
    public boolean isConnected() {
        if (_so == null) {
            return false;
        }
        if (_so.isConnected() && ! _so.isClosed()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 指定されたデバイスの値を取得します。
     * デバイス種別に応じて自動的にワード読込みまたはビット読込みを実行します。
     * 
     * @param deviceString デバイス名（例："D100", "M0"）
     * @return デバイスの値（ビットデバイスの場合は0または1）
     * @throws UnsupportedOperationException サポートされていないデバイス種別の場合
     */
    public short getDevice2(String deviceString) {
        DeviceSpec device = new DeviceSpec(deviceString);
        switch (device.getDeviceCode().getDataType()) {
            case WORD:
                return readBlockWord(device, (short)1)[0];
            case BIT:
                return (short)(readBlockBit(device, (short)1)[0] ? 1 : 0);
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * 指定されたデバイスに値を設定します。
     * デバイス種別に応じて自動的にワード書込みまたはビット書込みを実行します。
     * 
     * @param deviceString デバイス名（例："D100", "M0"）
     * @param value 設定する値（ビットデバイスの場合は0または1）
     * @throws UnsupportedOperationException サポートされていないデバイス種別の場合
     * @throws IllegalArgumentException ビットデバイスで0,1以外の値が指定された場合
     */
    public void setDevice2(String deviceString, short value) {
        DeviceSpec device = new DeviceSpec(deviceString);
        switch (device.getDeviceCode().getDataType()) {
            case WORD:
                writeBlockWord(device, new short[]{value});
                break;
            case BIT:
                if (value == 0) {
                    writeBlockBit(device, new boolean[]{false});
                } else if (value == 1) {
                    writeBlockBit(device, new boolean[]{true});
                } else {
                    throw new IllegalArgumentException("test");
                }
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    /**
     * 指定されたデバイスの値をコンソールに表示します。
     * 
     * @param deviceName 表示するデバイス名
     */
    public void printDevice2(String deviceName) {
        short value = getDevice2(deviceName);
        System.out.println(deviceName + ": " + value);
    }

    /**
     * ワード単位でのデバイス一括読込みを実行します。
     * 
     * @param deviceSpec 読込み開始デバイス仕様
     * @param devicePoint 読込みデバイス点数
     * @return 読込まれたデータの配列
     */
    public short[] readBlockWord(DeviceSpec deviceSpec, short devicePoint) {
        if (_isDebug) {
            System.out.println("一括読込み（ワード単位）: readBlockWord");
        }
        // 要求伝文を構築
        BlockReadRequest requestPayload = new BlockReadRequest(
            deviceSpec,
            devicePoint,
            false
        );
        // 送信
        BlockWordReadResponse responseFrame = (BlockWordReadResponse)sendRequest(requestPayload);
        return responseFrame.getReadData();
    }
    
    /**
     * ワード単位でのデバイス一括書込みを実行します。
     * 
     * @param deviceSpec 書込み開始デバイス仕様
     * @param writeData 書込むデータの配列
     */
    public void writeBlockWord(DeviceSpec deviceSpec, short[] writeData) {
        if (_isDebug) {
            System.out.println("一括書込み（ワード単位）: writeBlockWord");
        }
        // 要求伝文を構築
        BlockWriteRequest requestPayload = new BlockWriteRequest(
            deviceSpec,
            (short)writeData.length,
            Converter.fromShortArrayToBytes(writeData),
            false
        );
        // 送信
        sendRequest(requestPayload);
    }

    /**
     * ビット単位でのデバイス一括読込みを実行します。
     * 
     * @param deviceSpec 読込み開始デバイス仕様（ビットデバイスのみ）
     * @param devicePoint 読込みデバイス点数
     * @return 読込まれたデータの配列（true/false）
     * @throws UnsupportedOperationException ビットデバイス以外が指定された場合
     */
    public boolean[] readBlockBit(DeviceSpec deviceSpec, short devicePoint) {
        if (_isDebug) {
            System.out.println("一括読込み（ビット単位）: readBlockBit");
        }
        if (deviceSpec.getDeviceCode().getDataType() != DataTypeEnum.BIT) {
            throw new UnsupportedOperationException("ビットデバイス以外は対応していません");
        }

        // 要求伝文を構築
        BlockReadRequest requestPayload = new BlockReadRequest(
            deviceSpec,
            devicePoint,
            true
        );
        // 送信
        BlockBitReadResponse responseFrame = (BlockBitReadResponse)sendRequest(requestPayload);
        return responseFrame.getReadData();
    }

    /**
     * ビット単位でのデバイス一括書込みを実行します。
     * 配列サイズが奇数の場合は自動的に偶数に調整されます。
     * 
     * @param deviceSpec 書込み開始デバイス仕様（ビットデバイスのみ）
     * @param writeData 書込むデータの配列（true/false）
     * @throws IllegalArgumentException 配列サイズが0またはビットデバイス以外が指定された場合
     */
    public void writeBlockBit(DeviceSpec deviceSpec, boolean[] writeData) {
        if (_isDebug) {
            System.out.println("一括書込み（ビット単位）: writeBlockBit");
        }

        if (writeData.length < 1) {
            throw new IllegalArgumentException("配列のサイズが間違っています");
        }
        if (deviceSpec.getDeviceCode().getDataType() != DataTypeEnum.BIT) {
            throw new IllegalArgumentException("ビットデバイス以外は対応していません");
        }

        // 配列サイズが奇数であれば、偶数に揃える
        if (writeData.length % 2 != 0) {
            boolean[] temp = new boolean[writeData.length + 1];
            for (int i=0; i<writeData.length; i++) {
                temp[i] = writeData[i];
            }
            writeData = temp;
        }
  
        // 送信データの変換
        byte[] sendBytes = new byte[writeData.length / 2];
        for (int i=0; i<sendBytes.length; i++) {
            byte temp0 = (byte)(writeData[i*2 + 0] ? 0x1 : 0x0);
            byte temp1 = (byte)(writeData[i*2 + 1] ? 0x1 : 0x0);
            sendBytes[i] = (byte)(temp0 << 4 | temp1);
        }

        // 要求伝文を構築
        BlockWriteRequest requestPayload = new BlockWriteRequest(
            deviceSpec,
            (short)writeData.length,
            sendBytes,
            true
        );

        // 送信
        sendRequest(requestPayload);
    }

    /**
     * PLC形名を読み取ります。
     * 
     * @return PLC形名文字列
     */
    public String readPlcTypeName() {
        PlcTypeNameRequest requestPayload = new PlcTypeNameRequest();
        PlcTypeNameResponse responsePayload = (PlcTypeNameResponse)sendRequest(requestPayload);
        return responsePayload.getPlcTypeName();
    }

    /**
     * リモートRUNコマンドを実行します。
     * PLCをRUN状態に移行させます。
     */
    public void remoteRun() {
        RemoteRunRequest requestPayload = new RemoteRunRequest(
            RemoteRunRequest.FORCE_EXEC,
            RemoteRunRequest.DEVICE_UNCLEAR
        );
        sendRequest(requestPayload);
    }
    
    /**
     * リモートSTOPコマンドを実行します。
     * PLCをSTOP状態に移行させます。
     */
    public void remoteStop() {
        RemoteStopRequest requestPayload = new RemoteStopRequest();
        sendRequest(requestPayload);
    }

    /**
     * 折り返しテストを実行します。
     * 送信したデータがそのまま返ってくるかを確認します。
     * 
     * @param data テスト用データ
     */
    public void echoTest(byte[] data) {
        EchoTestRequest requestPayload = new EchoTestRequest(null, data);
        EchoTestResponse responsePayload = (EchoTestResponse)sendRequest(requestPayload);
        
        System.out.println("Echo test");
        System.out.println("send: " + Converter.fromBytesToHexStringBigEndian(data));
        System.out.println("recv: " + Converter.fromBytesToHexStringBigEndian(responsePayload.getReadData()));

        if (java.util.Arrays.equals(data, responsePayload.getReadData())) {
            System.out.println("正常: データは一致しています");
        } else {
            System.out.println("異常: データは一致していません");
        }
    }

    /**
     * PLCに要求伝文を送信し、応答伝文を受信・解析します。
     * 
     * @param requestPayload 送信する要求ペイロード
     * @return 解析された応答ペイロード
     * @throws RequestAbnormalException 送信に失敗した場合
     * @throws ResponseAbnormalException 応答の終了コードが異常の場合
     */
    public AbstractResponse sendRequest(AbstractRequest requestPayload) {
        // 要求伝文を構築
        byte[] requestBytes = _builder.build(requestPayload, _monitorTimer);
        
        // デバッグ
        if (_isDebug) {
            Parser.printFrameInfo(requestBytes);
            requestPayload.printInfo();
        }
        
        byte[] responseBytes;
        try {
            // 送信
            _out = _so.getOutputStream();
            _out.write(requestBytes);
            _out.flush();
            // 応答伝文を取得
            responseBytes = new byte[_bufferSize];
            _in.read(responseBytes);
        } catch (Exception e){
            throw new RequestAbnormalException("送信できませんでした");    
        }

        // 応答伝文を解析
        Frame3E responseFrame = Parser.parseFrame(responseBytes);

        // デバッグ
        if (_isDebug) {
            Parser.printFrameInfo(responseFrame.toBytes());
        }

        // 終了コードが正常でない場合は例外を投げる
        if (!responseFrame.isNormalFinish()) {
            AbnormalResponse res = new AbnormalResponse(responseFrame._payloadByteArray);
            res.printErrorInfo();
            throw new ResponseAbnormalException("終了コードが正常ではありません: " + responseFrame._monitorOrFinishCode);
        }
        return requestPayload.parseResponse(responseFrame._payloadByteArray);
    }
}
