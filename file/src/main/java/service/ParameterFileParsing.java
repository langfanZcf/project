package service;

import domain.AByteArray;
import domain.AMD5;
import prm_file_0004.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ParameterFileParsing {

    private static String TAG = "ParameterFileParsing";
    /**
     * 费率参数
     *
     * @param fileName 文件名称
     * @param readData 文件流
     * @return 返回：0成功，1文件类型不正确，2文件名与文件头的参数类型不一致，3文件内容与校验码不匹配，4解析结束与校验码不一致，
     * 5费率参数解析异常，6车票费率类型参数解析异常，7费率组参数解析异常，8基础费率组参数解析异常
     */
    public static int prm_file_0004(String fileName, AByteArray readData) {
        long t1 = System.currentTimeMillis();
        int index = 0;

        int r = 0;
        r = parsing(fileName, readData);
        if (r > 0)
            return r;

        byte[] md5 = AByteArray.copyOfRange(readData.getBytes(), readData.size() - 16, readData.size() - 1);
        readData.getByte(0, 21);

        index = 21;

        System.out.println(TAG+"-----------0x0004费率参数----------------");


        int count;
        PRM_file_0004.PRM_file_0004_smallBankPay = readData.getByte(index + 1, index + 4);//银行卡最小支付金额
        index += 4;

        PRM_file_0004.PRM_file_0004_maxBankPay = readData.getByte(index + 1, index + 4);//银行卡最大支付金额
        index += 4;

//        ParameterLib.print(TAG, "----------费率参数--------------");
        count = AByteArray.byteToIntR(readData.getByte(index + 1, index + 4));
        index += 4;
//        ParameterLib.print(TAG, "-------------------费率记录数(" + count + ")");

        PRM_file_0004.PRM_file_0004_tickeRateList.clear();
        P_0004_TickeRate p_0004_tickeRate;
        for (int i = 0; i < count; i++) {
            try {
                p_0004_tickeRate = new P_0004_TickeRate();
                p_0004_tickeRate.P_0004_TickeRate_IntoStationID = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_tickeRate.P_0004_TickeRate_OutStationID = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_tickeRate.P_0004_TickeRate_RateLevel = readData.getByte(index + 1, index + 4);
                index += 4;
                PRM_file_0004.PRM_file_0004_tickeRateList.add(p_0004_tickeRate);
            } catch (Exception e) {
                e.printStackTrace();
                return 5;
            }
        }
//        ParameterLib.print(TAG, "-----------费率记录数（END）----------------");

//        ParameterLib.print(TAG, "----------车票费率类型参数--------------");
        count = AByteArray.byteToIntR(readData.getByte(index + 1, index + 4));
        index += 4;
//        ParameterLib.print(TAG, "-------------------车票费率类型记录数(" + count + ")");
        PRM_file_0004.PRM_file_0004_tickeRateTypeList.clear();
        P_0004_TickeRateType p_0004_tickeRateType;
        for (int i = 0; i < count; i++) {
            try {
                p_0004_tickeRateType = new P_0004_TickeRateType();
                p_0004_tickeRateType.P_0004_TickeRateType_TicketType_ID = readData.getByte(index + 1, index + 1)[0];
                index += 1;
                p_0004_tickeRateType.P_0004_TickeRateType_CardChipType_ID = readData.getByte(index + 1, index + 1)[0];
                index += 1;
                p_0004_tickeRateType.P_0004_TickeRateType_RateType = readData.getByte(index + 1, index + 4);
                index += 4;
                PRM_file_0004.PRM_file_0004_tickeRateTypeList.add(p_0004_tickeRateType);
            } catch (Exception e) {
                e.printStackTrace();
                return 6;
            }
        }
//        ParameterLib.print(TAG, "-----------车票费率类型参数（END）----------------");

//        ParameterLib.print(TAG, "----------费率组参数--------------");
        count = AByteArray.byteToIntR(readData.getByte(index + 1, index + 4));
        index += 4;
//        ParameterLib.print(TAG, "-------------------费率组记录(" + count + ")");
        PRM_file_0004.PRM_file_0004_rateGroupList.clear();
        P_0004_RateGroup p_0004_rateGroup;
        for (int i = 0; i < count; i++) {
            try {
                p_0004_rateGroup = new P_0004_RateGroup();
                p_0004_rateGroup.P_0004_RateGroup_RateType = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_rateGroup.P_0004_RateGroup_SpecialDateType = readData.getByte(index + 1, index + 1)[0];
                index += 1;
                p_0004_rateGroup.P_0004_RateGroup_TimeSectionID = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_rateGroup.P_0004_RateGroup_RateGroupNumber = readData.getByte(index + 1, index + 4);
                index += 4;
                PRM_file_0004.PRM_file_0004_rateGroupList.add(p_0004_rateGroup);
            } catch (Exception e) {
                e.printStackTrace();
                return 7;
            }
        }

//        ParameterLib.print(TAG, "-----------费率组参数（END）----------------");

//        ParameterLib.print(TAG, "----------基础费率组参数--------------");
        count = AByteArray.byteToIntR(readData.getByte(index + 1, index + 4));
        index += 4;
//        ParameterLib.print(TAG, "-------------------费率组记录(" + count + ")");
        PRM_file_0004.PRM_file_0004_basisRateList.clear();
        P_0004_BasisRate p_0004_basisRate;
        for (int i = 0; i < count; i++) {
            try {

                p_0004_basisRate = new P_0004_BasisRate();
                p_0004_basisRate.P_0004_BasisRate_RateGroupNumber = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_basisRate.P_0004_BasisRate_RateLevel = readData.getByte(index + 1, index + 4);
                index += 4;
                p_0004_basisRate.P_0004_BasisRate_RateValue = readData.getByte(index + 1, index + 4);
                index += 4;
                PRM_file_0004.PRM_file_0004_basisRateList.add(p_0004_basisRate);
            } catch (Exception e) {
                e.printStackTrace();
                return 8;
            }
        }

//        ParameterLib.print(TAG, "-----------基础费率组参数（END）----------------");

        System.out.println(TAG+ AByteArray.byteToHexString(readData.getByte(index + 1, index + 16), " ") + "    校验码");
        if (AByteArray.byteToHexString(md5, " ").equals(AByteArray.byteToHexString(readData.getByte(index + 1, index + 16), " ")) == false)
            return 4;

        System.out.println(TAG+ "-----------解析（END）----------------");
        System.out.println(TAG+"费率参数   花费时间 " + ((System.currentTimeMillis() - t1) / 1000.0) + " 秒");

        return r;
    }


    /**
     * 文件验证
     *
     * @param fileName 文件名
     * @param readData 文件流
     * @return 返回：0成功，1文件类型不正确，2文件名与文件头的参数类型不一致，3文件内容与校验码不匹配
     */
    private static int parsing(String fileName, AByteArray readData) {
        String[] names = fileName.split("\\.");
        String parameterType = AByteArray.byteToHexString(AByteArray.bytesRewind(readData.get(9, 10).getBytes()));
        if (readData.get(0) != 0x20) {
            System.out.println(TAG+"文件类型不正确------>" + readData.get(0));
            return 1;
        }
        if (names[1].equals(parameterType) == false) {
            System.out.println(TAG+ "文件名与文件头的参数类型不一致-------->" + names[1] + "------->" + parameterType);
            return 2;
        }
        byte[] data = AByteArray.copyOfRange(readData.getBytes(), 0, readData.size() - 16 - 1);
        byte[] md5 = AByteArray.copyOfRange(readData.getBytes(), readData.size() - 16, readData.size() - 1);
        byte[] new_md5 =  AMD5.encryptMD5(data);
        System.out.println(TAG+ "获取MD5---->" + AByteArray.byteToHexString(md5, " "));
        System.out.println(TAG+ "计算MD5---->" + AByteArray.byteToHexString(new_md5, " "));
        if (AByteArray.byteToHexString(md5, " ").equals(AByteArray.byteToHexString(new_md5, " ")) == false) {
            System.out.println(TAG+"文件内容与校验码不匹配");
            return 3;
        }
        System.out.println(TAG+ "-----------文件头----------------");
        System.out.println(TAG+ readData.get(0, 21).toStringH16(" "));

        return 0;
    }

    //十六进制下数字到字符的映射数组
    private final static String[] hexDigits = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

    /**
     * 轮换字节数组为十六进制字符串
     * @param b 字节数组
     * @return 十六进制字符串
     */
    private static String byteArrayToHexString(byte[] b){
        StringBuffer resultSb = new StringBuffer();
        for(int i=0;i<b.length;i++){
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    //将一个字节转化成十六进制形式的字符串
    private static String byteToHexString(byte b){
        int n = b;
        if(n<0)
            n=256+n;
        int d1 = n/16;
        int d2 = n%16;
        return hexDigits[d1] + hexDigits[d2];
    }

}
