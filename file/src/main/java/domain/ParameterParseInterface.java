package domain;

/**
 * 参数文件解析线程接口
 * <p>
 * Created by Songmeiyu on 2017/4/21.
 */

public interface ParameterParseInterface {
    /**
     * 刷新界面提示
     *
     * @param msg 显示信息
     */
    void refreshUI(String msg);

    /**
     * 解析完成
     *
     * @param re 显示信息
     */
    void parseEnd(boolean re);
}
