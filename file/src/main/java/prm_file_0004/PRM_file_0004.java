package prm_file_0004;


import common.AByteArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 费率参数
 * Created by dihy on 2017/5/5 0005.
 */

public class PRM_file_0004 {

    static String TAG="PRM_file_0004";
    /**
     * 最大限行支付金额
     */
    public static byte[] PRM_file_0004_maxBankPay;
    /**
     * 最小限行支付金额
     */
    public static byte[] PRM_file_0004_smallBankPay;
    /**
     * 费率集合
     */
    public static List<P_0004_TickeRate> PRM_file_0004_tickeRateList = new ArrayList<P_0004_TickeRate>();
    /**
     * 车票费率类型集合
     */
    public static List<P_0004_TickeRateType> PRM_file_0004_tickeRateTypeList = new ArrayList<P_0004_TickeRateType>();
    /**
     * 费率组集合
     */
    public static List<P_0004_RateGroup> PRM_file_0004_rateGroupList = new ArrayList<P_0004_RateGroup>();
    /**
     * 基础费率组集合
     */
    public static List<P_0004_BasisRate> PRM_file_0004_basisRateList = new ArrayList<P_0004_BasisRate>();

    /**
     * 根据出站站点编号查找进站点编号和费率等级
     * @param station
     */
    public static void getTickeRateByStation(byte[] station){
        try {
            AByteArray aByteArray = new AByteArray();
            for (P_0004_TickeRate p : PRM_file_0004_tickeRateList) {
                if(Arrays.equals(station, AByteArray.copyOfRange(p.P_0004_TickeRate_OutStationID, 0, 1)) == true){
                    // TODO: 2019/9/24 返回费率参数
//                    InNode.PRM_file_0004_tickeRateList.add(p);
                }
            }
        } catch (Exception e) {
            System.out.println(TAG+" ->getTickeRateByStation(): "+ e.getMessage());
        }
    }

}
