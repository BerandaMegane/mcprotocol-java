package mc3eprotocol.define;

import java.util.Arrays;

import mc3eprotocol.*;

/**
 * デバイスコード sh080003ah p.68
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

    private final String _symbol;
    private final DataTypeEnum _dataType;
    private final int _deviceNumberBase;

    /**
     * コンストラクタ
     */
    DeviceCodeEnum(String symbol, DataTypeEnum dataType, int deviceNumberBase) {
        this._symbol = symbol;
        this._dataType = dataType;
        this._deviceNumberBase = deviceNumberBase;
    }

    public static DeviceCodeEnum build(byte[] bytes, SeriesModelEnum seriesModel) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (seriesModel == SeriesModelEnum.Q_SERIES) {
                if (Arrays.equals(deviceCode.toQSeriesBytes(), bytes)) {
                    return deviceCode;
                }
            } else if (seriesModel == SeriesModelEnum.IQ_SERIES) {
                if (Arrays.equals(deviceCode.toIQSeriesBytes(), bytes)) {
                    return deviceCode;
                }
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }
    
    public static DeviceCodeEnum build(String deviceSymbol) {
        for (DeviceCodeEnum deviceCode : DeviceCodeEnum.values()) {
            if (deviceCode.getDeviceSymbol().equals(deviceSymbol)) {
                return deviceCode;
            }
        }
        throw new IllegalArgumentException("指定のデバイスコードは存在しません。");
    }

    public static boolean containsCodeString(String target) {
        return Arrays.stream(DeviceCodeEnum.values()).anyMatch(r -> r.getDeviceSymbol().equals(target));
    }

    public static int getLength(SeriesModelEnum seriesModel) {
        return getDeviceNumberLength(seriesModel) + getDeviceCodeLength(seriesModel);
    }

    public static int getDeviceNumberLength(SeriesModelEnum seriesModel) {
        if (seriesModel == SeriesModelEnum.Q_SERIES) {
            return 3;
        } else if (seriesModel == SeriesModelEnum.IQ_SERIES) {
            return 4;
        } else {
            throw new IllegalArgumentException("シリーズモデルが不正です");
        }
    }

    public static int getDeviceCodeLength(SeriesModelEnum seriesModel) {
        if (seriesModel == SeriesModelEnum.Q_SERIES) {
            return 1;
        } else if (seriesModel == SeriesModelEnum.IQ_SERIES) {
            return 2;
        } else {
            throw new IllegalArgumentException("シリーズモデルが不正です");
        }
    }

    public String getDeviceSymbol() {
        return _symbol;
    }

    public DataTypeEnum getDataType() {
        return _dataType;
    }

    public byte[] toBytes(SeriesModelEnum seriesModel) {
        byte[] bytes = null;

        switch (seriesModel) {
            case Q_SERIES:
                bytes = this.toQSeriesBytes();
                break;
        
            case IQ_SERIES:
                bytes = this.toIQSeriesBytes();
                break;

            default:
                throw new IllegalArgumentException("指定のシリーズモデルは使用できません。");
        }
        return bytes;
    }

    public String toQ_SeriesHexString() {
        switch (this) {
            case SpecialRelay: return "91";
            case SpecialRegister: return "A9";
            case Input: return "9C";
            case Output: return "9D";
            case InternalRelay: return "90";
            case LatchRelay: return "92";
            case Annunciator: return "93";
            case EdgeRelay: return "94";
            case LinkRelay: return "A0";
            case DataRegister: return "A8";
            case LinkRegister: return "B4";
            case TimerContact: return "C1";
            case TimerCoil: return "C0";
            case TimerCurrentValue: return "C2";
            case RetentiveTimerContact: return "C7";
            case RetentiveTimerCoil: return "C6";
            case RetentiveTimerCurrentValue: return "C8";
            case CounterContact: return "C4";
            case CounterCoil: return "C3";
            case CounterCurrentValue: return "C5";
            case LinkSpecialRelay: return "A1";
            case LinkSpecialRegister: return "B5";
            case StepRelay: return "98";
            case DirectAccessInput: return "A2";
            case DirectAccessOutput: return "A3";
            case IndexRegister: return "CC";
            case FileRegisterBlockSwitchingMethod: return "AF";
            case FileRegisterSerialNumberAccessMethod: return "B0";
            case ExtendedDataRegister: return "A8";
            case ExtendedLinkRegister: return "B4";
            default: throw new IllegalArgumentException("指定のデバイスは使用できません。");
        }
    }

    public byte[] toQSeriesBytes() {
        return Utility.fromHexStringToBytes(this.toQ_SeriesHexString(), 1);
    }

    public String toIQSeriesHexString() {
        switch (this) {
            case SpecialRelay: return "0091";
            case SpecialRegister: return "00A9";
            case Input: return "009C";
            case Output: return "009D";
            case InternalRelay: return "0090";
            case LatchRelay: return "0092";
            case Annunciator: return "0093";
            case EdgeRelay: return "0094";
            case LinkRelay: return "00A0";
            case DataRegister: return "00A8";
            case LinkRegister: return "00B4";
            case TimerContact: return "00C1";
            case TimerCoil: return "00C0";
            case TimerCurrentValue: return "00C2";
            case LongTimerContact: return "0051";
            case LongTimerCoil: return "0050";
            case LongTimerCurrentValue: return "0052";
            case RetentiveTimerContact: return "00C7";
            case RetentiveTimerCoil: return "00C6";
            case RetentiveTimerCurrentValue: return "00C8";
            case LongRetentiveTimerContact: return "0059";
            case LongRetentiveTimerCoil: return "0058";
            case LongRetentiveTimerCurrentValue: return "005A";
            case CounterContact: return "00C4";
            case CounterCoil: return "00C3";
            case CounterCurrentValue: return "00C5";
            case LongCounterContact: return "0055";
            case LongCounterCoil: return "0054";
            case LongCounterCurrentValue: return "0056";
            case LinkSpecialRelay: return "00A1";
            case LinkSpecialRegister: return "00B5";
            case StepRelay: return "0098";
            case DirectAccessInput: return "00A2";
            case DirectAccessOutput: return "00A3";
            case IndexRegister: return "00CC";
            case LongIndexRegister: return "0062";
            case FileRegisterBlockSwitchingMethod: return "00AF";
            case FileRegisterSerialNumberAccessMethod: return "00B0";
            case RefreshDataRegister: return "002C";
            default: throw new IllegalArgumentException("指定のデバイスは使用できません。");
        }
    }

    public byte[] toIQSeriesBytes() {
        return Utility.fromHexStringToBytes(this.toIQSeriesHexString(), 2);
    }

    /**
     * デバイス番号の基数を取得
     * 例えば、入力リレー X は16進数、データレジスタ D は10進数
     * @return
     */
    public int getDeviceNumberBase() {
        return _deviceNumberBase;
    }
}
