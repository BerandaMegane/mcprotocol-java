package mc3eprotocol;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

import mc3eprotocol.define.*;
import mc3eprotocol.frame.*;
import mc3eprotocol.payload.*;

public class Client {
    private boolean _isDebug;
    private String _host;
    private int _port;

    private Socket _so;
    private OutputStream _out;
    private BufferedInputStream _in;
    private int _bufferSize = 1024;

    private SeriesModelEnum _seriesModel;
    private AccessRoute _accessRoute;
    private short _monitorTimer;

    Builder _builder;

    /**
     * コンストラクタ
     * @param host
     * @param port
     * @param seriesModel
     */
    public Client(String host, int port, SeriesModelEnum seriesModel, boolean isDebug) {
        this._isDebug = isDebug;
        this._host = host;
        this._port = port;

        this._seriesModel = seriesModel;
        this._accessRoute = AccessRoute.DEFAULT;
        this._monitorTimer = (short)0x0001;

        this._builder = new Builder(_seriesModel, _accessRoute);
    }

    /**
     * 接続時に呼び出す
     */
    public void connect() {
        try {
            _so = new Socket(_host, _port);
            _in = new BufferedInputStream(_so.getInputStream());
            _out = _so.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
            disconnect();
        }
    }

    /**
     * 切断時に呼び出す
     */
    public void disconnect() {
        if(_out != null) try { _out.close(); } catch(Exception e){ e.printStackTrace(); }
        if(_in != null) try { _in.close(); } catch(Exception e){ e.printStackTrace(); }
        if(_so != null) try { _so.close(); } catch(Exception e){ e.printStackTrace(); }
    }

    /**
     * 接続状態を返す
     * @return
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
     * デバイス値を取得する
     * @param deviceString
     * @return
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
     * デバイス値を設定する
     * @param deviceString
     * @param value
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
     * デバイス値を表示する
     * @param deviceName
     */
    public void printDevice2(String deviceName) {
        short value = getDevice2(deviceName);
        System.out.println(deviceName + ": " + value);
    }

    /**
     * 一括読込み（ワード単位）
     * @param deviceSpec
     * @param devicePoint
     * @return
     */
    public short[] readBlockWord(DeviceSpec deviceSpec, short devicePoint) {
        if (_isDebug) {
            System.out.println("一括読込み（ワード単位）: readBlockWord");
        }
        // 要求伝文を構築
        BlockReadRequest requestPayload = new BlockReadRequest(
            _seriesModel,
            deviceSpec,
            devicePoint,
            false
        );
        // 送信
        BlockWordReadResponse responseFrame = (BlockWordReadResponse)sendRequest(requestPayload);
        return responseFrame.getReadData();
    }
    
    /**
     * 一括書込み（ワード単位）
     * @param deviceSpec
     * @param writeData
     */
    public void writeBlockWord(DeviceSpec deviceSpec, short[] writeData) {
        if (_isDebug) {
            System.out.println("一括書込み（ワード単位）: writeBlockWord");
        }
        // 要求伝文を構築
        BlockWriteRequest requestPayload = new BlockWriteRequest(
            _seriesModel,
            deviceSpec,
            (short)writeData.length,
            Utility.fromShortArrayToBytes(writeData),
            false
        );
        // 送信
        sendRequest(requestPayload);
    }

    /**
     * 一括読込み（ビット単位）
     * @param deviceSpec
     * @param devicePoint
     * @return
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
            _seriesModel,
            deviceSpec,
            devicePoint,
            true
        );
        // 送信
        BlockBitReadResponse responseFrame = (BlockBitReadResponse)sendRequest(requestPayload);
        return responseFrame.getReadData();
    }

    /**
     * 一括書込み（ビット単位）
     * @param deviceSpec
     * @param writeData
     */
    public void writeBlockBit(DeviceSpec deviceSpec, boolean[] writeData) {
        if (_isDebug) {
            System.out.println("一括書込み（ビット単位）: readBlockBit");
        }

        if (writeData.length < 1) {
            throw new IllegalArgumentException("配列のサイズが間違っています");
        }
        if (deviceSpec.getDeviceCode().getDataType() != DataTypeEnum.BIT) {
            throw new IllegalArgumentException("ビットデバイス以外は対応していません");
        }

        // 配列サイズが奇数であれば、一つ増やしておく
        boolean[] temp;
        temp = new boolean[writeData.length + 1];
        for (int i=0; i<writeData.length; i++) {
            temp[i] = writeData[i];
        }
  
        // 送信データの変換
        byte[] sendBytes = new byte[temp.length / 2];
        for (int i=0; i<sendBytes.length; i++) {
            byte temp0 = (byte)(writeData[i*2 + 0] ? 0x1 : 0x0);
            byte temp1 = (byte)(writeData[i*2 + 1] ? 0x1 : 0x0);
            sendBytes[i] = (byte)(temp0 << 4 | temp1);
        }

        // 要求伝文を構築
        BlockWriteRequest requestPayload = new BlockWriteRequest(
            _seriesModel,
            deviceSpec,
            (short)writeData.length,
            sendBytes,
            true
        );

        // 送信
        sendRequest(requestPayload);
    }

    /**
     * PLC 形名を取得する
     * @return
     */
    public String readPlcTypeName() {
        PlcTypeNameRequest requestPayload = new PlcTypeNameRequest();
        PlcTypeNameResponse responsePayload = (PlcTypeNameResponse)sendRequest(requestPayload);
        return responsePayload.getPlcTypeName();
    }

    /**
     * リモートRUNを実行する
     */
    public void remoteRun() {
        RemoteRunRequest requestPayload = new RemoteRunRequest();
        sendRequest(requestPayload);
    }
    
    /**
     * リモートSTOPを実行する
     */
    public void remoteStop() {
        RemoteStopRequest requestPayload = new RemoteStopRequest();
        sendRequest(requestPayload);
    }

    /**
     * 折返しテストを実行する
     * @param data
     */
    public void echoTest(byte[] data) {
        EchoTestRequest requestPayload = new EchoTestRequest(null, data);
        EchoTestResponse responsePayload = (EchoTestResponse)sendRequest(requestPayload);
        
        System.out.println("Echo test");
        System.out.println("send: " + Utility.fromBytesToHexStringBigEndian(data));
        System.out.println("recv: " + Utility.fromBytesToHexStringBigEndian(responsePayload.getReadData()));
    }

    /**
     * PLC に要求伝文を送信し、応答伝文を取得する
     * @param requestPayload
     * @return
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
            AbnormalResponse res = new AbnormalResponse(responseFrame._payload);
            res.printErrorInfo();
            throw new ResponseAbnormalException("終了コードが正常ではありません: " + responseFrame._monitorOrFinishCode);
        }
        return requestPayload.parseResponse(responseFrame._payload);
    }
}
