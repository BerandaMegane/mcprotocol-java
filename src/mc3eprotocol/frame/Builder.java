package mc3eprotocol.frame;

import mc3eprotocol.*;
import mc3eprotocol.define.*;
import mc3eprotocol.payload.*;

public class Builder {

    /**
     * シリーズモデル情報 (サブコマンドの指定に必要)
     */
    protected SeriesModelEnum seriesModel;
    
    /**
     * アクセス経路 sh080003ah p.45
     */
    protected AccessRoute accessRoute;

    /**
     * コンストラクタ
     */
    public Builder(
        SeriesModelEnum seriesModel,
        AccessRoute accessRoute
    ) {
        this.seriesModel = seriesModel;
        this.accessRoute = accessRoute;
    }

    /**
     * 要求/応答 フレームを生成する
     */
    public byte[] build(IPayload request, short monitorOrFinishCode) {
        // ペイロードの取得
        byte[] payload = request.toBytes();

        // フレームの構築
        return Utility.concatByteArrays(
            SubHeaderEnum.REQUEST.getBytes(),
            accessRoute.toBytes(),
            Utility.fromIntToBytes((short)(payload.length + 2), 2),  // データ長
            Utility.fromIntToBytes(monitorOrFinishCode, 2),  // 監視/終了コード
            payload
        );
    }
}
