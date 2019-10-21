package domain;

/**
 * 票卡实体
 */
public class CardData {
    /**
     * 主类型
     */
    public byte master;
    /**
     * 子类型
     */
    public byte subType;
    /**
     * 芯片类型
     */
    public byte chip;
    /**
     * 当前日期
     */
    public byte[] curdate;
    /**
     * 当前时间
     */
    public long curTime;
    /**
     * 金额
     */
    public int deal;
    /**
     * 优惠金额
     */
    public int minusCasg;
}
