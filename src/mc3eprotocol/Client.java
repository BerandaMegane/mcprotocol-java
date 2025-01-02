package mc3eprotocol;

import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.net.Socket;

import mc3eprotocol.define.*;
import mc3eprotocol.frame.*;
import mc3eprotocol.payload.*;

public class Client {
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
    public Client(String host, int port, SeriesModelEnum seriesModel) {
        this._host = host;
        this._port = port;

        this._seriesModel = seriesModel;
        this._accessRoute = AccessRoute.DEFAULT;
        this._monitorTimer = (short)0x0001;

        this._builder = new Builder(_seriesModel, _accessRoute);
    }

    /**
     * 接続時に呼び出す
     * @throws Exception
     */
    public void connect() throws Exception {
        _so = new Socket(_host, _port);
        _in = new BufferedInputStream(_so.getInputStream());
        _out = _so.getOutputStream();
    }

    /**
     * 切断時に呼び出す
     * @throws Exception
     */
    public void disconnect() throws Exception {
        if(_out != null) try { _out.close(); } catch(Exception e){}
        if(_in != null) try { _in.close(); } catch(Exception e){}
        if(_so != null) try { _so.close(); } catch(Exception e){}
    }

    /**
     * デバイス値を取得する
     * @param deviceString
     * @return
     * @throws Exception
     */
    public short getDeviceValue2(String deviceString) throws Exception {
        return getBlockWordRead(new DeviceSpec(deviceString), (short)1)[0];
    }

    /**
     * デバイス値を表示する
     * @param deviceName
     * @throws Exception
     */
    public void printDeviceValue2(String deviceName) throws Exception {
        short value = getDeviceValue2(deviceName);
        System.out.println(deviceName + ": " + value);
    }

    /**
     * デバイス値を設定する
     * @param deviceString
     * @param value
     * @throws Exception
     */
    public void setDeviceValue2(String deviceString, short value) throws Exception {
       setBlockWordWrite(new DeviceSpec(deviceString), new short[]{value});
    }

    /**
     * 一括書込み（ワード単位）
     * @param deviceSpec
     * @param writeData
     * @throws Exception
     */
    public void setBlockWordWrite(DeviceSpec deviceSpec, short[] writeData) throws Exception {
        // 要求伝文を構築
        BlockWriteRequest requestPayload = new BlockWriteRequest(
            _seriesModel,
            deviceSpec,
            Utility.fromShortArrayToBytes(writeData),
            false
        );
        // 送信
        sendRequest(requestPayload);
    }

    /**
     * 一括読込み（ワード単位）
     * @param deviceSpec
     * @param devicePoint
     * @return
     * @throws Exception
     */
    public short[] getBlockWordRead(DeviceSpec deviceSpec, short devicePoint) throws Exception {
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
     * PLC 形名を取得する
     * @return
     * @throws Exception
     */
    public String readPlcTypeName() throws Exception {
        PlcTypeNameRequest requestPayload = new PlcTypeNameRequest();
        PlcTypeNameResponse responsePayload = (PlcTypeNameResponse)sendRequest(requestPayload);
        return responsePayload.getPlcTypeName();
    }

    /**
     * リモートRUNを実行する
     * @throws Exception
     */
    public void remoteRun() throws Exception {
        RemoteRunRequest requestPayload = new RemoteRunRequest();
        sendRequest(requestPayload);
    }
    
    /**
     * リモートSTOPを実行する
     * @throws Exception
     */
    public void remoteStop() throws Exception {
        RemoteStopRequest requestPayload = new RemoteStopRequest();
        sendRequest(requestPayload);
    }

    public void echoTest(byte[] data) throws Exception {
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
     * @throws Exception
     */
    public AbstractResponse sendRequest(AbstractRequest requestPayload) throws Exception {
        // 要求伝文を構築
        byte[] requestBytes = _builder.build(requestPayload, _monitorTimer);
        // デバッグ
        // Parser.printInfo(requestBytes);
        // 送信
        _out = _so.getOutputStream();
        _out.write(requestBytes);
        _out.flush();
        // 応答伝文を取得
        byte[] responseBytes = new byte[_bufferSize];
        _in.read(responseBytes);
        // 応答伝文を解析
        Frame3E responseFrame = Parser.parseFrame(responseBytes);
        // Parser.printInfo(responseFrame.toBytes());
        // 終了コードが正常でない場合は例外を投げる
        if (!responseFrame.isNormalFinish()) {
            AbnormalResponse res = new AbnormalResponse(responseFrame._payload);
            res.printErrorInfo();
            throw new ResponseAbnormalException("終了コードが正常ではありません: " + responseFrame._monitorOrFinishCode);
        }
        return requestPayload.parseResponse(responseFrame._payload);
    }
}
