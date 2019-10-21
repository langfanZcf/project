package domain;

/**
 * 站点实体类
 * 用于设置-车站设置界面显示
 * <p>
 * Created by Songmeiyu on 2017/4/12 0012.
 */

public class Station {

    /**
     * 站点编号
     */
    private String StationID;
    /**
     * 站点类型：0线路，1站点
     */
    private int Type;
    /**
     * 中文名称
     */
    private String CName;

    public Station(int Type, String StationID, String CName) {
        this.Type = Type;
        this.StationID = StationID;
        this.CName = CName;
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }


}
