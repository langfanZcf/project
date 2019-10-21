package service;

import domain.AByteArray;
import domain.ParameterParseInterface;
import prm_file_0004.PRM_file_0004;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeError.getFileName;

public class ParameterParseTask {

    private  static ParameterParseInterface callback;
    private static String TAG = "ParameterParseTask";

    public ParameterParseTask(ParameterParseInterface callback) {
        this.callback = callback;
    }

    private static void refreshUI(String msg) {
        if (callback != null)
            callback.refreshUI(msg);
    }


    public static void main(String args[]){
        doInBackground();
    }
    public static Boolean doInBackground() {
        try {
            //获取参数文件文件夹
            File paramsDir = new File("C:\\Users\\Stig_Hardware\\Desktop\\");

            if (paramsDir.isDirectory()) {
                //获取文件名称数组,并排序
                String[] fileNames = paramsDir.list();
                Comparator cmp = Collator.getInstance(java.util.Locale.CHINA);
                Arrays.sort(fileNames, cmp);
                System.out.println(TAG+ "参数文件数：" + fileNames.length);
                //按文件名称依次解析参数文件
                for (String fileName : fileNames) {
                    System.out.println(TAG+"解析文件：" + fileName);

                    //只解析PRM.X.X.X的文件
                    String point = "\\.";
                    if (fileName.contains(".") && fileName.split(point).length == 4 && fileName.split(point)[0].equals("PRM")) {

                        String parameterType = fileName.split(point)[1];//参数类型
                     //   refreshUI("正在校验参数文件：" + getFileName(parameterType));
                        System.out.println(TAG+"参数文件路径：" + paramsDir.getPath() + File.separator + fileName);

                        //获取文件数组
                        InputStream inputstream =new FileInputStream(paramsDir.getPath() + File.separator + fileName);
                        //进行读取操作
                        byte[] buffer = new byte[1024];
                        byte[] byteData = new byte[0];
                        int c;
                        while ((c = inputstream.read(buffer)) != -1) {
                            byteData = AByteArray.byteJoint(byteData, buffer, c);
                        }

                        AByteArray readData = new AByteArray(byteData);
                        System.out.println(TAG+"readData.size：" + readData.size());
                        if (readData.size() < 38) {
                            System.out.println(TAG+ "参数文件异常，请重新导入: " + fileName);
                            refreshUI("参数文件异常，请重新导入: " + getFileName(parameterType));
                            break;
                        } else {
                            //调用参数文件解析方法，将返回的list存入数据库
                          //  refreshUI("正在解析参数文件：" + getFileName(parameterType));
                            boolean re = parse(parameterType, fileName, readData);
//                            if (!re) {
//                                refreshUI("解析参数文件失败：" + getFileName(parameterType));
//                                return false;
//                            } else {
//                                refreshUI("解析参数文件成功：" + getFileName(parameterType));
//                            }
                        }
                    }
                }
            }
            Thread.sleep(1000);
            refreshUI("解析参数文件完毕");

        } catch (Exception e) {
            System.out.println(TAG+ e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 调用参数文件解析方法，将返回的list存入数据库
     * <br/>参数文件解析方法返回：值和list，值=0是正确的，判断list的size,>0存入数据库
     *
     * @param type     文件类型
     * @param fileName 文件名
     * @param readData 文件内容
     */
    private static boolean parse(String type, String fileName,  AByteArray readData) {
        if ("0004".equals(type)) {
            return parse0004(fileName, readData);
        }
        return true;
    }


    private static boolean parse0004(String fileName,  AByteArray readData) {
        try {
            int re = ParameterFileParsing.prm_file_0004(fileName, readData);
            //返回：0成功，1文件类型不正确，2文件名与文件头的参数类型不一致，3文件内容与校验码不匹配
            System.out.println(TAG+ "parse：false - " + re);
            System.out.println(TAG+"parse：tickeRateList - " + PRM_file_0004.PRM_file_0004_tickeRateList.size());
            System.out.println(TAG+ "parse：tickeRateTypeList - " + PRM_file_0004.PRM_file_0004_tickeRateTypeList.size());
            System.out.println(TAG+ "parse：rateGroupList - " + PRM_file_0004.PRM_file_0004_rateGroupList.size());
            System.out.println(TAG+ "parse：basisRateList - " + PRM_file_0004.PRM_file_0004_basisRateList.size());
            if (re > 0 || PRM_file_0004.PRM_file_0004_tickeRateList.size() == 0 || PRM_file_0004.PRM_file_0004_tickeRateTypeList.size() == 0 || PRM_file_0004.PRM_file_0004_rateGroupList.size() == 0 || PRM_file_0004.PRM_file_0004_basisRateList.size() == 0) {
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println(TAG+e.getMessage());
        }
        return false;
    }


}
