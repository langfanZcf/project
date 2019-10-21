/**
 * FileName: AByteArray
 * Author: 地球是平的
 * Date: 2019/9/19 11:06
 */
package domain;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: AByteArray
 * @Description: 字节流操作类
 * @Author: 地球是平的
 * @Date: 2019/9/19 11:06
 */
public class AByteArray {

    String TAG = "AByteArray";
    byte[] byteArray = new byte[0];

    public AByteArray() {
        // this.byteArray = new byte[0];
    }

    public AByteArray(byte[] bytes) {
        this.byteArray = bytes;
    }

    /**
     * 设置流对象大小
     *
     * @param size
     *            流对象的大小（单位：字节）
     */
    public void resize(int size) {
        byteArray = new byte[size];
    }

    /**
     * 将流进行分割
     *
     * @param sep
     *            分割符
     * @return 分割后的流对象列表
     */
    public List<AByteArray> split(char sep) {
        List<AByteArray> list = new ArrayList<AByteArray>();
        AByteArray tmpArray = new AByteArray();
        int i;
        for (i = 0; i < byteArray.length; i++) {
            if (byteArray[i] == sep) {
                list.add(tmpArray);
                tmpArray = new AByteArray();
            } else {
                tmpArray.add(byteArray[i]);
            }
        }
        list.add(tmpArray);
        return list;
    }

    /**
     * 将16进制字符串转换为字节流对象
     *
     * @param str
     *            要转换的字符串
     * @return 转换后的字节流对象
     */
    public static AByteArray hexToByte(String str) {
        if (str.length() % 2 != 0) {
            return new AByteArray();
        }
        AByteArray arr = new AByteArray();
        char ch;
        for (int i = 0; i < str.length(); i += 2) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                ch = (char) ((str.charAt(i) - 48) * 16);
            } else if (str.charAt(i) >= 65 && str.charAt(i) <= 70) {
                ch = (char) ((str.charAt(i) - 55) * 16);
            } else if (str.charAt(i) >= 97 && str.charAt(i) <= 102) {
                ch = (char) ((str.charAt(i) - 87) * 16);
            } else {
                return new AByteArray();
            }
            int m = i + 1;
            if (str.charAt(m) >= 48 && str.charAt(m) <= 57) {
                ch += str.charAt(m) - 48;
            } else if (str.charAt(m) >= 65 && str.charAt(m) <= 70) {
                ch += str.charAt(m) - 55;
            } else if (str.charAt(m) >= 97 && str.charAt(m) <= 102) {
                ch += str.charAt(m) - 87;
            } else {
                return new AByteArray();
            }
            arr.add(ch);
        }
        return arr;
    }

    /**
     * 追加字符串到本对象中
     *
     * @param str
     *            要追加的字符串
     * @return 追加后的字节流对象
     */
    public AByteArray add(String str) {

        if (str == null) {
            return this;
        }
        byte[] newByte = str.getBytes();
        int m = byteArray.length;
        int n = newByte.length;
        byte[] C = new byte[m + n];

//		for (int i = 0; i < m; i++) {
//			C[i] = byteArray[i];
//		}
        System.arraycopy(byteArray, 0, C, 0, m);

//		for (int j = 0; j < n; j++) {
//			C[m + j] = newByte[j];
//		}
        System.arraycopy(newByte, 0, C, m, n);

        byteArray = C;
        return this;
    }

    /**
     * 追加字节流对象到本对象中
     *
     * @param array
     *            要追加的对象
     * @return 追加后的字节流对象
     */
    public AByteArray add(AByteArray array) {

        if (array == null) {
            return this;
        }
        byte[] newByte = array.getBytes();
        int m = byteArray.length;
        int n = newByte.length;
        byte[] C = new byte[m + n];

//		for (int i = 0; i < m; i++) {
//			C[i] = byteArray[i];
//		}
        System.arraycopy(byteArray, 0, C, 0, m);

//		for (int j = 0; j < n; j++) {
//			C[m + j] = newByte[j];
//		}
        System.arraycopy(newByte, 0, C, m, n);
        byteArray = C;
        return this;
    }

    /**
     * 追加字节流数组到本对象中
     *
     * @param bytes
     *            要追加的数组
     * @return 追加后的字节流对象
     */
    public AByteArray add(byte[] bytes) {

        if (bytes == null) {
            return this;
        }
        int m = byteArray.length;
        int n = bytes.length;
        byte[] C = new byte[m + n];

//		for (int i = 0; i < m; i++) {
//			C[i] = byteArray[i];
//		}
        System.arraycopy(byteArray, 0, C, 0, m);

//		for (int j = 0; j < n; j++) {
//			C[m + j] = bytes[j];
//		}
        System.arraycopy(bytes, 0, C, m, n);

        byteArray = C;
        return this;
    }

    /**
     * 追加字节数据到本对象中
     *
     * @param newByte
     *            要追加的字节数据
     * @return 追加后的字节流对象
     */
    public AByteArray add(Byte newByte) {
        if (newByte == null) {
            return this;
        }
        int m = byteArray.length;
        byte[] C = new byte[m + 1];
//		for (int i = 0; i < m; i++) {
//			C[i] = byteArray[i];
//		}
        System.arraycopy(byteArray, 0, C, 0, m);

        C[m] = newByte;

        byteArray = C;
        return this;
    }

    /**
     * 追加字符数据到本对象中
     *
     * @param ch
     *            要追加的字符数据
     * @return 追加后的字节流对象
     */
    public AByteArray add(char ch) {
        int m = byteArray.length;
        byte[] C = new byte[m + 1];
        for (int i = 0; i < m; i++) {
            C[i] = byteArray[i];
        }
        C[m] = (byte) ch;

        byteArray = C;
        return this;
    }

    /**
     * 获取指定索引的数据
     *
     * @param index
     *            要获取的数据索引位置
     * @return 获取的字节数据
     */
    public Byte get(int index) {
        if (index < 0 || index >= byteArray.length) {
            return null;
        }
        return byteArray[index];
    }

    /**
     * 获取指定索引的数据
     *
     * @param bytes
     *            数据
     * @param index
     *            要获取的数据索引位置
     * @return 获取的字节数据
     */
    public static Byte get(byte[] bytes, int index) {
        if (index < 0 || index >= bytes.length) {
            return null;
        }
        return bytes[index];
    }

    /**
     * 从后向前取出length个长度数据
     *
     * @param length
     *            要取出的数据长度
     * @return 取出的数据
     */
    public AByteArray right(int length) {
        if (length >= byteArray.length) {
            return this;
        }
        AByteArray array = new AByteArray();

        int i = length;
        int j = byteArray.length - length;
        for (; i > 0; i--, j++) {
            array.add(get(j));
        }
        return array;
    }

    /**
     * 获取所有byte[]
     *
     * @return 对象内的所有数据的字符数组形式
     */
    public byte[] getBytes() {
        return byteArray;
    }

    /**
     * 获取指定范围的数据
     *
     * @param start
     *            开始的索引
     * @param end
     *            结束的索引
     * @return 获取的数据对象
     */
    public AByteArray get(int start, int end) {
        if (start < 0 || start > end || start >= byteArray.length || end >= byteArray.length) {
            return null;
        }
        int length = end - start + 1;
        AByteArray array = new AByteArray();
        for (int i = 0; i < length; i++) {
            array.add(byteArray[start + i]);
        }
        return array;
    }

    /**
     * 获取指定范围的数据
     *
     * @param start
     *            开始的索引
     * @param end
     *            结束的索引
     * @return 获取的数据对象
     */
    public byte[] getByte(int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = byteArray.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start + 1;
        int copyLength = Math.min(resultLength, originalLength - start);
        byte[] result = new byte[resultLength];
        System.arraycopy(byteArray, start, result, 0, copyLength);
        return result;
    }

    /**
     * 获取指定范围的数据
     *
     * @param bytes
     *            数据
     * @param start
     *            开始的索引
     * @param end
     *            结束的索引
     * @return 获取的数据对象
     */
    public static AByteArray get(byte[] bytes, int start, int end) {
        if (start < 0 || start > end || start >= bytes.length || end >= bytes.length) {
            return null;
        }
        int length = end - start + 1;
        AByteArray array = new AByteArray();
        for (int i = 0; i < length; i++) {
            array.add(bytes[start + i]);
        }
        return array;
    }

    /**
     * 获取index位置后的所有数据，包括index位置的数据
     *
     * @param index
     *            指定的数据索引
     * @return 获取的数据对象
     */
    public AByteArray getBehind(int index) {
        int length = byteArray.length;
        if (index >= length || index < 0) {
            return null;
        }
        AByteArray array = new AByteArray();
        for (int i = index; i < length; i++) {
            array.add(byteArray[i]);
        }
        return array;
    }

    /**
     * int类型转换为byte数组
     *
     * @param number
     *            要转换的数字
     * @return 转换后的字节流数组
     */
    public static byte[] intToBytes(int number) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (number & 0xff);
        targets[2] = (byte) ((number >> 8) & 0xff);
        targets[1] = (byte) ((number >> 16) & 0xff);
        targets[0] = (byte) (number >> 24);
        return targets;
        // 下面的方式也能实现 效率低
        // ByteArrayOutputStream boutput = new ByteArrayOutputStream();
        // DataOutputStream doutput = new DataOutputStream(boutput);
        // try {
        // doutput.writeInt(number);
        //
        // } catch (IOException e) {
        // Log.e(TAG,
        // "整型" + number + "转换byte[]异常：" + e.toString());
        //
        // }
        // targets=boutput.toByteArray();

    }

    /**
     * 获取指定位置前的数据，包括指定位置的数据
     *
     * @param index
     *            指定的数据索引
     * @return 获取的数据对象
     */
    public AByteArray getBefore(int index) {
        int length = byteArray.length;
        if (index >= length || index < 0) {
            return null;
        }
        AByteArray array = new AByteArray();
        for (int i = 0; i < index + 1; i++) {
            array.add(byteArray[i]);
        }
        return array;
    }

    /**
     * 删除指定范围的元素
     *
     * @param start
     *            删除内容的起始位置
     * @param end
     *            删除内容的结束位置
     */
    public void remove(int start, int end) {

        if (start < 0 || start > end || start >= byteArray.length || end >= byteArray.length) {
            return;
        }
        int length = byteArray.length - end + start - 1;
        byte[] C = new byte[length];
        for (int i = 0, j = 0; i < byteArray.length; i++) {

            if (i >= start && i <= end) {
                continue;
            }
            C[j] = byteArray[i];
            j++;
        }
        byteArray = C;
    }

    /**
     * 获取指定范围的元素，并将该内容从流里删除
     *
     * @param start
     *            获取内容的起始位置
     * @param end
     *            获取内容的结束位置
     * @return 获取的内容
     */
    public AByteArray cut(int start, int end) {

        if (start > end || start >= byteArray.length || end >= byteArray.length) {
            return null;
        }
        int length = byteArray.length - end + start - 1;
        byte[] C = new byte[length];
        byte[] D = new byte[end - start + 1];
        for (int i = 0, j = 0, k = 0; i < byteArray.length; i++) {
            if (i >= start && i <= end) {
                D[k] = byteArray[i];
                k++;
            } else {
                C[j] = byteArray[i];
                j++;
            }
        }
        byteArray = C;
        AByteArray ba = new AByteArray();
        ba.add(D);
        return ba;
    }

    /**
     * 获取对象内存储的数据大小
     *
     * @return 对象内存储的数据大小
     */
    public int size() {
        return byteArray.length;
    }

    /**
     * 清空对象内数据
     */
    public void clear() {
        byteArray = new byte[0];
    }

    /**
     * 将数据转换成ByteBuffer
     *
     * @return 转换后的对象
     */
    public ByteBuffer toByteBuffer() {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);

        return buffer;
    }

    /**
     * 将流转换成16进制字符串，并用sep分隔
     *
     * @param sep
     *            分隔符
     *
     * @return 转换后的字符串
     */
    public String toStringH16(String sep) {
        StringBuffer sb = new StringBuffer(byteArray.length);
        String sTemp;
        for (int i = 0; i < byteArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & byteArray[i]);
            if (sTemp.length() == 1) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
            sb.append(sep);
        }
        return sb.toString();
    }

    /**
     * byte[]转int
     *
     * @return 转换后的数字
     */
    public int toInt() {
        int ret = 0;
        for (final byte a : byteArray) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
        // ============
        // 只对前四个字节操作
        // byte[] byte1 = new byte[4];
        // if (byteArray.length < 4) {
        // for (int i = 0; i < byte1.length; i++) {
        // if (byteArray.length > i) {
        //
        // byte1[4 - i - 1] = byteArray[byteArray.length - i - 1];
        // }
        // }
        //
        // } else {
        // byte1 = byteArray;
        // }
        // int integer = byte1[3] & 0xFF;
        //
        // integer |= ((byte1[2] << 8) & 0xFF00);
        //
        // integer |= ((byte1[1] << 16) & 0xFF0000);
        //
        // integer |= ((byte1[0] << 24) & 0xFF000000);
        //
        // return integer;
    }

    /**
     * byte[]转int
     *
     * @param start
     *            起始位置
     * @param count
     *            长度
     * @return
     */
    public int toInt(int start, int count) {
        int ret = 0;

        final int e = start + count;
        for (int i = start; i < e; ++i) {
            ret <<= 8;
            ret |= byteArray[i] & 0xFF;
        }
        return ret;
    }

    /**
     * 将数据转换成String(**必须是String转换的byte[]，需测试)
     *
     * @return 转换后的对象
     */
    public String toString() {
        return new String(byteArray);
    }

    /**
     * 判断bytearray和本对象是否一致
     *
     * @return true，一致；false，不一致
     */
    public boolean equals(AByteArray bytearray) {
        if (byteArray.length != bytearray.size()) {
            return false;
        } else {
            for (int i = 0; i < byteArray.length; i++) {

                if (byteArray[i] != bytearray.get(i)) {
                    return false;
                }
            }
            return true;
        }

    }

    /**
     * 将bytearray复制到本对象，会清空本对象
     */
    public void copy(AByteArray bytearray) {
        this.clear();
        this.add(bytearray);
    }

    public static byte[] copyOfRange(byte[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start + 1;
        int copyLength = Math.min(resultLength, originalLength - start);
        byte[] result = new byte[resultLength];
        System.arraycopy(original, start, result, 0, copyLength);
        return result;
    }

    private final static char[] HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    public static boolean testBit(byte data, int bit) {
        final byte mask = (byte) ((1 << bit) & 0x000000FF);

        return (data & mask) == mask;
    }

    /**
     * byte[]转int(大端)
     *
     * @param b
     *            byte[]
     * @param start
     *            起始位置
     * @param count
     *            长度
     * @return
     */
    public static int byteToInt(byte[] b, int start, int count) {
        int ret = 0;

        final int e = start + count;
        for (int i = start; i < e; ++i) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }

    /**
     * 从右byte[]转int(小端)
     *
     * @param b
     *            byte[]
     * @param start
     *            起始位置
     * @param count
     *            长度
     * @return
     */
    public static int byteToIntR(byte[] b, int start, int count) {
        int ret = 0;

        for (int i = start; (i >= 0 && count > 0); --i, --count) {
            ret <<= 8;
            ret |= b[i] & 0xFF;
        }
        return ret;
    }

    /**
     * byte[]转Int(大端)
     *
     * @param b
     *            byte or byte[]
     * @return
     */
    public static int byteToInt(byte... b) {
        int ret = 0;
        for (final byte a : b) {
            ret <<= 8;
            ret |= a & 0xFF;
        }
        return ret;
    }

    /**
     * 从右byte[]转int(小端)
     *
     * @param b
     *            byte or byte[]
     * @return
     */
    public static int byteToIntR(byte... b) {
        return byteToIntR(b, b.length - 1, b.length);
    }

    /**
     * byte[]转16进制字符串
     *
     * @param d
     *            byte or byte[]
     * @return
     */
    public static String byteToHexString(byte... d) {
        return (d == null || d.length == 0) ? "" : byteToHexString(d, 0, d.length);
    }

    /**
     * byte[]转16进制字符串
     *
     * @param d
     *            byte[]
     * @param sep
     *            分隔符
     * @return
     */
    public static String byteToHexString(byte[] d, String sep) {
        byte[] b = d;
        StringBuffer sb = new StringBuffer(b.length);
        String sTemp;
        for (int i = 0; i < b.length; i++) {
            sTemp = Integer.toHexString(0xFF & b[i]);
            if (sTemp.length() == 1) {
                sb.append(0);
            }
            sb.append(sTemp.toUpperCase());
            sb.append(sep);
        }
        return sb.toString();
    }

    /**
     * byte[]转16进制字符串
     *
     * @param d
     *            byte[]
     * @param start
     *            起始位置
     * @param count
     *            长度
     * @return
     */
    public static String byteToHexString(byte[] d, int start, int count) {
        final char[] ret = new char[count * 2];
        final int e = start + count;

        int x = 0;
        for (int i = start; i < e; ++i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    /**
     * 从右byte[]转16进制字符串(小端)
     *
     * @param d
     *            byte[]
     * @param start
     *            起始位置
     * @param count
     *            长度
     * @return
     */
    public static String byteToHexStringR(byte[] d, int start, int count) {
        final char[] ret = new char[count * 2];

        int x = 0;
        for (int i = start + count - 1; i >= start; --i) {
            final byte v = d[i];
            ret[x++] = HEX[0x0F & (v >> 4)];
            ret[x++] = HEX[0x0F & v];
        }
        return new String(ret);
    }

    // public static String ensureString(String str) {
    // return str == null ? "" : str;
    // }

    /**
     * int 转 String(小端)
     *
     * @param n
     * @return
     */
    public static String toStringR(int n) {
        final StringBuilder ret = new StringBuilder(16).append('0');

        long N = 0xFFFFFFFFL & n;
        while (N != 0) {
            ret.append((int) (N % 100));
            N /= 100;
        }

        return ret.toString();
    }

    /**
     * 字符串转换指定进制
     *
     * @param txt
     *            字符串
     * @param radix
     *            进制
     * @param def
     *            失败时设置默认值
     * @return
     */
    public static int parseInt(String txt, int radix, int def) {
        int ret;
        try {
            ret = Integer.valueOf(txt, radix);
        } catch (Exception e) {
            ret = def;
        }

        return ret;
    }

    public static int BCDtoInt(byte[] b, int s, int n) {
        int ret = 0;

        final int e = s + n;
        for (int i = s; i < e; ++i) {
            int h = (b[i] >> 4) & 0x0F;
            int l = b[i] & 0x0F;

            if (h > 9 || l > 9)
                return -1;

            ret = ret * 100 + h * 10 + l;
        }

        return ret;
    }

    public static int BCDtoInt(byte... b) {
        return BCDtoInt(b, 0, b.length);
    }

    /**
     * 一个字节的十进制 转 一个字节的BCD
     *
     * @param Dec
     * @return
     */
    public static byte OneDectoBCD(int Dec) {
        int temp;
        byte temp_8;
        temp = Dec % 100;
        temp_8 =(byte) (((temp / 10) << 4) + ((temp % 10) & 0x0F));
        // / Dec /= 100;
        return temp_8;
    }

    /**
     * 多个字节的十进制 转 多个字节的BCD
     * @param Dec
     * @param length
     * @return
     */
    public static byte[] DectoBCD(int Dec, int length) {
        int temp;
        byte[] Bcd = new byte[length];
        for ( int i = length - 1; i >= 0; i--) {
            temp = Dec % 100;
            Bcd[i] = (byte) (((temp / 10) << 4) + ((temp % 10) & 0x0F));
            Dec /= 100;
        }
        return Bcd;
    }

    /**
     * BCD码 转 十进制码
     * @param bcd
     * @return
     */
    public static int BcdToDec(byte bcd)
    {
        return (0xff & (bcd>>4))*10 +(0xf & bcd);

    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param s
     * @return
     */
    public static String stringToHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 反转
     *
     * @param src
     * @return
     */
    public static byte[] bytesRewind(byte[] src) {
        int length = src.length;
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[length - i - 1] = src[i];
        }
        return result;
    }

    /**
     * 两个byte[]连接
     *
     * @param byteLeft
     *            byte[]左边
     * @param byteRight
     *            byte[] 右边
     * @param byteRight_length
     *            右边长度
     * @return
     */
    public static byte[] byteJoint(byte[] byteLeft, byte[] byteRight, int byteRight_length) {
        if (byteRight.length < byteRight_length)
            return null;
        byte[] ab = new byte[byteLeft.length + byteRight_length];
        System.arraycopy(byteLeft, 0, ab, 0, byteLeft.length);
        System.arraycopy(byteRight, 0, ab, byteLeft.length, byteRight_length);
        return ab;
    }

    /**
     * 修改
     * @param bytes 修改数据
     * @param start 修改起始位置
     */
    public void alter(byte[] bytes,int start){
        for (int i = 0; i < bytes.length; ++i) {
            byteArray[start+i]=bytes[i];
        }
    }

    /**
     * 修改
     * @param start 修改起始位置
     * @param bytes 修改数据
     */
    public void alter(int start,byte... bytes){
        for (int i = 0; i < bytes.length; ++i) {
            byteArray[start+i]=bytes[i];
        }
    }

    /**
     * 修改
     * @param src 原始数据
     * @param bytes 修改数据
     * @param start 修改起始位置
     */
    public static void alter(byte[] src,byte[] bytes,int start){
        for (int i = 0; i < bytes.length; ++i) {
            src[start+i]=bytes[i];
        }
    }

    /**
     * 修改
     * @param src 原始数据
     * @param srcStart 修改起始位置
     * @param bytes 修改数据
     */
    public static void alter(byte[] src,int srcStart,byte... bytes){
        for (int i = 0; i < bytes.length; ++i) {
            src[srcStart+i]=bytes[i];
        }
    }
}
