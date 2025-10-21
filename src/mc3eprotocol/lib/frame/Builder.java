package mc3eprotocol.lib.frame;

import mc3eprotocol.lib.define.AccessRoute;
import mc3eprotocol.lib.define.SubHeaderEnum;
import mc3eprotocol.lib.payload.IPayload;
import mc3eprotocol.lib.util.Converter;

/**
 * MC3Eプロトコルフレーム構築クラス
 * 要求/応答フレームの生成を行うビルダークラスです。
 * ペイロードとヘッダー情報からMC3Eフレームを構築します。
 */
public class Builder {
   
    /**
     * アクセス経路設定
     * PLC通信時のアクセスルートを指定します（sh080003ah p.45参照）
     */
    protected AccessRoute accessRoute;

    /**
     * コンストラクタ
     * 指定されたアクセスルートでBuilderインスタンスを初期化します。
     * 
     * @param accessRoute 使用するアクセスルート設定
     */
    public Builder(
        AccessRoute accessRoute
    ) {
        this.accessRoute = accessRoute;
    }

    /**
     * 要求/応答フレームを生成します。
     * ペイロードとヘッダー情報を結合してMC3Eプロトコルフレームを構築します。
     * 
     * @param request 送信するペイロード
     * @param monitorOrFinishCode 監視タイマー値（要求時）または終了コード（応答時）
     * @return 構築されたフレームのバイト配列
     */
    public byte[] build(IPayload request, short monitorOrFinishCode) {
        // ペイロードの取得
        byte[] payload = request.toByteArray();

        // フレームの構築
        return Converter.concatByteArrays(
            SubHeaderEnum.REQUEST.getBytes().getByteArray(),
            accessRoute.toByteArray(),
            Converter.fromIntToByteArray((short)(payload.length + 2), 2),  // データ長
            Converter.fromIntToByteArray(monitorOrFinishCode, 2),  // 監視/終了コード
            payload
        );
    }
}
