package mc3eprotocol.lib.define;

/**
 * データ型列挙型
 * PLCデバイスのデータサイズを定義します。
 */
public enum DataTypeEnum {
    /** ビット型（1ビット）- X, Y, M等のリレーデバイス */
    BIT,
    /** ワード型（16ビット）- D, T, C等のレジスターデバイス */
    WORD,
    /** ダブルワード型（32ビット）- ロングタイマー、ロングカウンター等 */
    DWORD,
}
