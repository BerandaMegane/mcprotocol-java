package mc3eprotocol.lib.define;

import java.util.Arrays;

import mc3eprotocol.lib.util.Bytes;

/**
 * デバイスコード列挙型
 * PLCの各種デバイスコードを定義します（sh080003ah p.68参照）。
 * デバイスの種別、データ型、番号の進数を管理します。
 */
public enum DeviceCodeEnum {
    /**
     * 特殊リレー
     * 記号: SM
     */
    SpecialRelay("SM", DataTypeEnum.BIT, 10),

    /**
     * 特殊レジスター
     * 記号: SD
     */
    SpecialRegister("SD", DataTypeEnum.WORD, 10),

    /**
     * 入力
     * 記号: X
     */
    Input("X", DataTypeEnum.BIT, 16),

    /**
     * 出力
     * 記号: Y
     */
    Output("Y", DataTypeEnum.BIT, 16),

    /**
     * 内部リレー
     * 記号: M
     */
    InternalRelay("M", DataTypeEnum.BIT, 10),

    /**
     * ラッチリレー
     * 記号: L
     */
    LatchRelay("L", DataTypeEnum.BIT, 10),

    /**
     * アナンシェーター
     * 記号: F
     */
    Annunciator("F", DataTypeEnum.BIT, 10),

    /**
     * エッジリレー
     * 記号: V
     */
    EdgeRelay("V", DataTypeEnum.BIT, 10),

    /**
     * リンクリレー
     * 記号: B
     */
    LinkRelay("B", DataTypeEnum.BIT, 16),

    /**
     * データレジスター
     * 記号: D
     */
    DataRegister("D", DataTypeEnum.WORD, 10),

    /**
     * リンクレジスター
     * 記号: W
     */
    LinkRegister("W", DataTypeEnum.WORD, 16),

    /**
     * タイマー(接点)
     * 記号: TS
     */
    TimerContact("TS", DataTypeEnum.BIT, 10),

    /**
     * タイマー(コイル)
     * 記号: TC
     */
    TimerCoil("TC", DataTypeEnum.BIT, 10),

    /**
     * タイマー(現在値)
     * 記号: TN
     */
    TimerCurrentValue("TN", DataTypeEnum.WORD, 10),

    /**
     * ロングタイマー(接点)
     * 記号: LTS
     */
    LongTimerContact("LTS", DataTypeEnum.BIT, 10),

    /**
     * ロングタイマー(コイル)
     * 記号: LTC
     */
    LongTimerCoil("LTC", DataTypeEnum.BIT, 10),

    /**
     * ロングタイマー(現在値)
     * 記号: LTN
     */
    LongTimerCurrentValue("LTN", DataTypeEnum.DWORD, 10),

    /**
     * 積算タイマー(接点)
     * 記号: STS
     */
    RetentiveTimerContact("STS", DataTypeEnum.BIT, 10),

    /**
     * 積算タイマー(コイル)
     * 記号: STC
     */
    RetentiveTimerCoil("STC", DataTypeEnum.BIT, 10),

    /**
     * 積算タイマー(現在値)
     * 記号: STN
     */
    RetentiveTimerCurrentValue("STN", DataTypeEnum.WORD, 10),

    /**
     * ロング積算タイマー(接点)
     * 記号: LSTS
     */
    LongRetentiveTimerContact("LSTS", DataTypeEnum.BIT, 10),

    /**
     * ロング積算タイマー(コイル)
     * 記号: LSTC
     */
    LongRetentiveTimerCoil("LSTC", DataTypeEnum.BIT, 10),

    /**
     * ロング積算タイマー(現在値)
     * 記号: LSTN
     */
    LongRetentiveTimerCurrentValue("LSTN", DataTypeEnum.DWORD, 10),

    /**
     * カウンター(接点)
     * 記号: CS
     */
    CounterContact("CS", DataTypeEnum.BIT, 10),

    /**
     * カウンター(コイル)
     * 記号: CC
     */
    CounterCoil("CC", DataTypeEnum.BIT, 10),

    /**
     * カウンター(現在値)
     * 記号: CN
     */
    CounterCurrentValue("CN", DataTypeEnum.WORD, 10),

    /**
     * ロングカウンター(接点)
     * 記号: LCS
     */
    LongCounterContact("LCS", DataTypeEnum.BIT, 10),

    /**
     * ロングカウンター(コイル)
     * 記号: LCC
     */
    LongCounterCoil("LCC", DataTypeEnum.BIT, 10),

    /**
     * ロングカウンター(現在値)
     * 記号: LCN
     */
    LongCounterCurrentValue("LCN", DataTypeEnum.DWORD, 10),
    
    /**
     * リンク特殊リレー
     * 記号: SB
     */
    LinkSpecialRelay("SB", DataTypeEnum.BIT, 16),

    /**
     * リンク特殊レジスター
     * 記号: SW
     */
    LinkSpecialRegister("SW", DataTypeEnum.WORD, 16),

    /**
     * ステップリレー
     * 記号: S
     */
    StepRelay("S", DataTypeEnum.BIT, 10),

    /**
     * ダイレクトアクセス入力
     * 記号: DX
     */
    DirectAccessInput("DX", DataTypeEnum.BIT, 16),

    /**
     * ダイレクトアクセス出力
     * 記号: DY
     */
    DirectAccessOutput("DY", DataTypeEnum.BIT, 16),

    /**
     * インデックスレジスター
     * 記号: Z
     */
    IndexRegister("Z", DataTypeEnum.WORD, 10),

    /**
     * ロングインデックスレジスター
     * 記号: LZ
     */
    LongIndexRegister("LZ", DataTypeEnum.DWORD, 10),

    /**
     * ファイルレジスター(ブロック切換え方式)
     * 記号: R
     */
    FileRegisterBlockSwitchingMethod("R", DataTypeEnum.WORD, 10),

    /**
     * ファイルレジスター(連番アクセス方式)
     * 記号: ZR
     */
    FileRegisterSerialNumberAccessMethod("ZR", DataTypeEnum.WORD, 16),

    /**
     * 拡張データレジスター
     * 記号: D
     */
    ExtendedDataRegister("D", DataTypeEnum.WORD, 10),

    /**
     * 拡張リンクレジスター
     * 記号: W
     */
    ExtendedLinkRegister("W", DataTypeEnum.WORD, 16),

    /** リフレッシュデータレジスタ
     * 記号: RD
     */
    RefreshDataRegister("RD", DataTypeEnum.WORD, 10),
    ;

    /** デバイス記号 */
    private final String _symbol;
    /** データ型 */
    private final DataTypeEnum _dataType;
    /** デバイス番号の基数（10進数または16進数） */
    private final int _deviceNumberBase;

    /**
     * コンストラクタ
     * 
     * @param symbol デバイス記号
     * @param dataType データ型
     * @param deviceNumberBase デバイス番号の基数
     */
    DeviceCodeEnum(String symbol, DataTypeEnum dataType, int deviceNumberBase) {
        this._symbol = symbol;
        this._dataType = dataType;
        this._deviceNumberBase = deviceNumberBase;
    }

    /**
     * バイト配列からデバイスコードを構築します。
     * 
     * @param bytes デバイスコードのバイト配列
     * @return 対応するデバイスコード列挙値
     * @throws IllegalArgumentException 指定されたデバイスコードが存在しない場合
     */
    public static DeviceCodeEnum build(byte[] bytes) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (Arrays.equals(deviceCode.getBytes().getByteArray(), bytes)) {
                return deviceCode;
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }
    
    /**
     * デバイス記号からデバイスコードを構築します。
     * 
     * @param deviceSymbol デバイス記号文字列
     * @return 対応するデバイスコード列挙値
     * @throws IllegalArgumentException 指定されたデバイスコードが存在しない場合
     */
    public static DeviceCodeEnum build(String deviceSymbol) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (deviceCode.getDeviceSymbol().equals(deviceSymbol)) {
                return deviceCode;
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }

    /**
     * 指定された記号のデバイスコードが存在するかを確認します。
     * 
     * @param target 確認する記号文字列
     * @return 存在する場合はtrue、それ以外はfalse
     */
    public static boolean containsCodeString(String target) {
        return Arrays.stream(DeviceCodeEnum.values()).anyMatch(r -> r.getDeviceSymbol().equals(target));
    }

    /**
     * デバイス指定の全体バイト長を取得します。
     * 
     * @return デバイス番号長 + デバイスコード長
     */
    public static int getLength() {
        return getDeviceNumberLength() + getDeviceCodeLength();
    }

    /**
     * デバイス番号のバイト長を取得します。
     * 
     * @return デバイス番号のバイト長
     */
    public static int getDeviceNumberLength() {
        return 3;
    }
    
    /**
     * デバイスコードのバイト長を取得します。
     * 
     * @return デバイスコードのバイト長
     */
    public static int getDeviceCodeLength() {
        return 1;
    }

    /**
     * デバイス記号を取得します。
     * 
     * @return デバイス記号文字列
     */
    public String getDeviceSymbol() {
        return _symbol;
    }

    /**
     * データ型を取得します。
     * 
     * @return データ型
     */
    public DataTypeEnum getDataType() {
        return _dataType;
    }

    /**
     * デバイスコードのバイトデータを取得します。
     * 
     * @return デバイスコードのBytesオブジェクト
     * @throws IllegalArgumentException 指定されたデバイスが使用できない場合
     */
    public Bytes getBytes() {
        switch (this) {
            case SpecialRelay:                         return new Bytes("91");
            case Input:                                return new Bytes("9C");
            case SpecialRegister:                      return new Bytes("A9");
            case Output:                               return new Bytes("9D");
            case InternalRelay:                        return new Bytes("90");
            case LatchRelay:                           return new Bytes("92");
            case Annunciator:                          return new Bytes("93");
            case EdgeRelay:                            return new Bytes("94");
            case LinkRelay:                            return new Bytes("A0");
            case DataRegister:                         return new Bytes("A8");
            case LinkRegister:                         return new Bytes("B4");
            case TimerContact:                         return new Bytes("C1");
            case TimerCoil:                            return new Bytes("C0");
            case TimerCurrentValue:                    return new Bytes("C2");
            case RetentiveTimerContact:                return new Bytes("C7");
            case RetentiveTimerCoil:                   return new Bytes("C6");
            case RetentiveTimerCurrentValue:           return new Bytes("C8");
            case CounterContact:                       return new Bytes("C4");
            case CounterCoil:                          return new Bytes("C3");
            case CounterCurrentValue:                  return new Bytes("C5");
            case LinkSpecialRelay:                     return new Bytes("A1");
            case LinkSpecialRegister:                  return new Bytes("B5");
            case StepRelay:                            return new Bytes("98");
            case DirectAccessInput:                    return new Bytes("A2");
            case DirectAccessOutput:                   return new Bytes("A3");
            case IndexRegister:                        return new Bytes("CC");
            case FileRegisterBlockSwitchingMethod:     return new Bytes("AF");
            case FileRegisterSerialNumberAccessMethod: return new Bytes("B0");
            case ExtendedDataRegister:                 return new Bytes("A8");
            case ExtendedLinkRegister:                 return new Bytes("B4");
            default: throw new IllegalArgumentException("指定のデバイスは使用できません。");
        }
    }

    /**
     * デバイス番号の基数を取得します。
     * 例：入力リレー X は16進数、データレジスタ D は10進数
     * 
     * @return デバイス番号の基数（10または16）
     */
    public int getDeviceNumberBase() {
        return _deviceNumberBase;
    }
}
