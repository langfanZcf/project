/**
 * FileName: BaseSecurity
 * Author: 地球是平的
 * Date: 2019/9/19 14:27
 */
package domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: BaseSecurity
 * @Description: 加密父类
 * @Author: 地球是平的
 * @Date: 2019/9/19 14:27
 */
public class BaseSecurity {


    /**
     * 对data进行algorithm算法加密
     *
     * @param data
     *            明文字节数组
     * @param algorithm
     *            加密算法
     * @return 密文字节数组
     */
    protected static byte[] encryptAlgorithm(byte[] data, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
