package service;


import common.AByteArray;
import domain.CardData;
import domain.FareItem;
import prm_file_0004.PRM_file_0004;
import prm_file_0004.P_0004_BasisRate;
import prm_file_0004.P_0004_RateGroup;
import prm_file_0004.P_0004_TickeRateType;

import java.util.Date;

import static service.ParameterParseTask.doInBackground;

/**
 * 站点票价查询
 */
public class StationFareQuery {

    private static String TAG = "StationFareQuery";

    public static void main(String args[]){
        //程序启动即加载
        doInBackground();
        //定义入参
        String start="0224";
        String end="0241";
        FareQueryMoney(start,end);
    }

    /**
     * 系统时间
     */
    public static long timestamp = 0;
    protected static CardData cardData;
    public static boolean FareQueryMoney(String start,String end) {
        cardData = new CardData();

        cardData.chip =0x01;//芯片类型：一票通Ultralight
        cardData.subType =0x01;//车票类型：普通单程票

        int ret = 0;
        FareItem.inStation= AByteArray.hexToByte(start).getByte(0,1);
        FareItem.targetStation=AByteArray.hexToByte(end).getByte(0,1);
        ret = STupdateCheckTicketRoute(FareItem.inStation[0], FareItem.inStation[1], FareItem.targetStation[0], FareItem.targetStation[1], cardData);
        if (ret != 0) {
            FareItem.money = 0;
            return false;
        }
        FareItem.money = cardData.deal;
        System.out.println("票价："+FareItem.money);
        return true;
    }


    /**
     * 费率等级
     */
    public static int rateLevel = 0;
    /**
     * 费率类型
     */
    static int chipRate = 0;
    /**
     * 车票费率组代码
     */
    static int rateGnum = 0;

    /**
     * 单程票补出站时判断是否夸线路，并计算优惠金额
     *
     * @param Sroute   首站线路
     * @param Sstation 首站站点
     * @param Droute   终站线路
     * @param Dstation 终站站点
     * @param cardData 票卡实体类
     * @return
     */
    private static int STupdateCheckTicketRoute(byte Sroute, byte Sstation, byte Droute, byte Dstation, CardData cardData) {
        int ret = 0;
        /**
         * 费率等级
         */
        rateLevel = 0;
        /**
         * 费率类型
         */
        chipRate = 0;
        /**
         * 车票费率组代码
         */
        rateGnum = 0;


        ret = checkTicketMon(Sroute, Sstation, Droute, Dstation);
        if (ret != 0) {//费率等级
            //cc_RespErrMsgToECU(ret);
            return ret;
        }

        if (rateLevel == -1) {//跨线路
            //cc_RespErrMsgToECU(ERR_ROUTE_78);
            return 78;
        }

        ret = getRateType(cardData.chip, cardData.subType);
        if (ret != 0) {//费率类型
            //cc_RespErrMsgToECU(ret);
            return ret;
        }


        //============如果获取全票价，可以直接省略获取费率组代码，费率组代码默认为0
//        ret = getRateDeal(chipRate, cardData.curdate);
//        if (ret != 0) { //车票费率组代码
//            //cc_RespErrMsgToECU(ret);
//            return ret;
//        }

        ret = getRateMon(rateGnum, rateLevel, cardData);
        if (ret != 0) {//基础费率值
            //cc_RespErrMsgToECU(ret);
            return ret;
        }

        System.out.println(TAG+"补出站更新优惠金额=" + cardData.minusCasg);

//        /*    adverage=calMarkAdverage(scrAdverage);
//         *decash=*decash-adverage;*/
//
//        ret = UpdateGJcardRestrict(cardData, Droute, Dstation);
//        if (ret != 0) {//限制使用范围
//            return ret;
//        }


        return 0;
    }

    /**
     * 根据进出站节点获得费率等级
     *
     * @param Sroute   进站线路
     * @param Sstation 进站站点
     * @param Droute   出站线路
     * @param Dstation 出站站点
     * @return
     */
    public static int checkTicketMon(byte Sroute, byte Sstation, byte Droute, byte Dstation) {

        System.out.println(TAG+ " ****** 读取票价->费率等级 ****** "+ false);
        System.out.println(TAG+"进站节点 " + AByteArray.byteToHexString(Sroute) + " " + AByteArray.byteToHexString(Sstation)+false);
        System.out.println(TAG+ "目标节点 " + AByteArray.byteToHexString(Droute) + " " + AByteArray.byteToHexString(Dstation)+ false);

        if (PRM_file_0004.PRM_file_0004_tickeRateList.size() <= 0) {
            return 1;
        }

        byte[] instation;
        byte[] outstation;
        for (int i = 0; i < PRM_file_0004.PRM_file_0004_tickeRateList.size(); i++) {// 出站车站编码
            instation = AByteArray.copyOfRange(PRM_file_0004.PRM_file_0004_tickeRateList.get(i).P_0004_TickeRate_IntoStationID, 0, 1);
            outstation = AByteArray.copyOfRange(PRM_file_0004.PRM_file_0004_tickeRateList.get(i).P_0004_TickeRate_OutStationID, 0, 1);
            if ((outstation[0] == Droute) && (outstation[1] == Dstation)) {
                if ((instation[0] == Sroute) && (instation[1] == Sstation)) {
                    rateLevel = AByteArray.byteToIntR(PRM_file_0004.PRM_file_0004_tickeRateList.get(i).P_0004_TickeRate_RateLevel);
                    System.out.println(TAG+ "费率等级 " + rateLevel+ false);
                    return 0;
                }
            }
        }
        return 1;
    }


    /**
     * 根据芯片类型获得费率类型
     *
     * @param chip    芯片类型
     * @param subType 车票类型
     * @return
     */
    public static int getRateType(byte chip, byte subType) {
        System.out.println(TAG+ " ***根据芯片类型--->费率类型***"+ false);
        System.out.println(TAG+ " 当前芯片类型：" + AByteArray.byteToHexString(chip)+ false);
        System.out.println(TAG+" 当前车票类型：" + AByteArray.byteToHexString(subType)+ false);
        System.out.println(TAG+ " 当前芯片类型 记录数 =" + PRM_file_0004.PRM_file_0004_tickeRateTypeList.size()+false);

        P_0004_TickeRateType p_tickeRateType;
        for (int i = 0; i < PRM_file_0004.PRM_file_0004_tickeRateTypeList.size(); i++) {
            p_tickeRateType = PRM_file_0004.PRM_file_0004_tickeRateTypeList.get(i);
            if ((p_tickeRateType.P_0004_TickeRateType_TicketType_ID == chip) && (p_tickeRateType.P_0004_TickeRateType_CardChipType_ID == subType)) {
                chipRate = AByteArray.byteToIntR(p_tickeRateType.P_0004_TickeRateType_RateType);

                System.out.println(TAG+" 票卡芯片类型=" + AByteArray.byteToHexString(chip)+false);
                System.out.println(TAG+ " 车票类型=" + AByteArray.byteToHexString(subType)+ false);
                System.out.println(TAG+ " 费率类型=" + chipRate+ false);
                return 0;
            }
        }
        return 214;
    }

    /**
     * 特殊日期类型
     */
    static byte sPecial = 0;
    /**
     * 时间段
     */
    static int timeNum = 0;
    /**
     * 验证费率组
     *
     * @param rangeRate 当前费率类型
     * @param special   特殊日期类型
     * @param timeD     时间段编号
     * @return
     */
    public static int checkRateGroup(int rangeRate, byte special, int timeD) {
        System.out.println(TAG+" ****** 读取费率组记录内容 ******");
        System.out.println(TAG+" 费率组记录数= " + PRM_file_0004.PRM_file_0004_rateGroupList.size());
        System.out.println(TAG+" 当前费率类型= " + rangeRate);
        System.out.println(TAG+" 特殊日期类型= " + special);
        System.out.println(TAG+ " 时间段编号= " + timeD);

        P_0004_RateGroup p_rateGroup;
        for (int i = 0; i < PRM_file_0004.PRM_file_0004_rateGroupList.size(); i++)//费率组记录数
        {
            p_rateGroup = PRM_file_0004.PRM_file_0004_rateGroupList.get(i);
            if ((rangeRate == AByteArray.byteToIntR(p_rateGroup.P_0004_RateGroup_RateType))
                    && ((special == p_rateGroup.P_0004_RateGroup_SpecialDateType || (p_rateGroup.P_0004_RateGroup_SpecialDateType == 0x00))
                    && ((timeD == AByteArray.byteToIntR(p_rateGroup.P_0004_RateGroup_TimeSectionID)) || (AByteArray.byteToIntR(p_rateGroup.P_0004_RateGroup_TimeSectionID)) == 0))) {
                rateGnum = AByteArray.byteToIntR(p_rateGroup.P_0004_RateGroup_RateGroupNumber);
                System.out.println(TAG+ " 车票费率组代码= " + rateGnum);
                return 0;
            }
        }

        System.out.println(TAG+" ****** 匹配不成功 ******  ");
        return 214;
    }


    /**
     * 根据费率等级获取费率值和折扣   读取基础费率组内容
     *
     * @param rateGroup 当前车票费率组代码
     * @param rangeRate 当前费率等级
     * @param cardData  (需要返回) dealMon:交易金额 beneMon：全价金额
     * @return
     */
    public static int getRateMon(int rateGroup, int rangeRate, CardData cardData) {
//        *编写思路：
//        2017-07-25 ：
//          根据rateGroup:当前车票费率组代码
//          rangeRate:费率等级获取 交易金额dealMon为 和费率等级为0的全票价用于计算不同计费方式的优惠金额计算

        System.out.println(TAG+ " ****** 读取基础费率组内容 ******");
        System.out.println(TAG+ " 当前费率等级= " + rangeRate);
        System.out.println(TAG+" 当前车票费率组代码= " + rateGroup);


//        if(InNode.tickeRateList.size()==0)
//        {
//            return ERR_RATEP_LOST_218;//费率参数不存在
//        }
        if (PRM_file_0004.PRM_file_0004_basisRateList.size() == 0) {
            System.out.println(TAG+"基础费率组参数集合：" + PRM_file_0004.PRM_file_0004_basisRateList.size());
            return 218;//费率参数不存在
        }

        P_0004_BasisRate p_basisRate, p_basisRate2;
        for (int i = 0; i < PRM_file_0004.PRM_file_0004_basisRateList.size(); i++) {
            p_basisRate = PRM_file_0004.PRM_file_0004_basisRateList.get(i);
            if ((AByteArray.byteToIntR(p_basisRate.P_0004_BasisRate_RateLevel) == rangeRate)
                    && (AByteArray.byteToIntR(p_basisRate.P_0004_BasisRate_RateGroupNumber) == rateGroup)) {
                cardData.deal = AByteArray.byteToIntR(p_basisRate.P_0004_BasisRate_RateValue);//交易费率值
//                dealMon=((Brateg+i)->fare);//交易费率值
                if (rateGroup == 0) {
//                    cardData.minusCasg = 0;
                    cardData.minusCasg = cardData.deal;//优惠金额

                    System.out.println(TAG+ " 费率等级= " + rangeRate);
                    System.out.println(TAG+" 费率值= " + AByteArray.byteToIntR(p_basisRate.P_0004_BasisRate_RateValue));
                    System.out.println(TAG+ " 交易金额= " + cardData.deal);
                    System.out.println(TAG+ " 优惠金额= " + cardData.minusCasg);
                    System.out.println(TAG+ " --------------------------------------------- ");
                } else {
                    for (int j = 0; j < PRM_file_0004.PRM_file_0004_basisRateList.size(); j++) {
                        p_basisRate2 = PRM_file_0004.PRM_file_0004_basisRateList.get(j);
                        if ((AByteArray.byteToIntR(p_basisRate2.P_0004_BasisRate_RateLevel) == rangeRate)
                                && (AByteArray.byteToIntR(p_basisRate2.P_0004_BasisRate_RateGroupNumber) == 0x00)) {
//                            cardData.minusCasg = AByteArray.byteToIntR(p_basisRate2.RateValue) - cardData.deal;
                            cardData.minusCasg = AByteArray.byteToIntR(p_basisRate2.P_0004_BasisRate_RateValue);

                            System.out.println(TAG+ " 费率等级= " + rangeRate);
                            System.out.println(TAG+" 费率值= " + AByteArray.byteToIntR(p_basisRate2.P_0004_BasisRate_RateValue));
                            System.out.println(TAG+" 交易金额= " + cardData.deal);
                            System.out.println(TAG+ " 优惠金额= " + cardData.minusCasg);
                            System.out.println(TAG+ " --------------------------------------------- ");
                        }
                    }
                }
                return 0;
            }
        }
        System.out.println(TAG+" 没有匹配的费率 ");
        return 214;
    }

}
