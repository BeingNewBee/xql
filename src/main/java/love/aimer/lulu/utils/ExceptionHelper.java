
package love.aimer.lulu.utils;

import java.lang.reflect.InvocationTargetException;

/**
 * 异常处理工具类
 *
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class ExceptionHelper {
    /**
     * 构造函数
     */
    private ExceptionHelper() {
    }

    /**
     * 处理Java动态代理产生的异常，如果异常类型是动态代理反射异常，则获取真实异常类型
     * @param t Java异常
     * @return 实际Java异常
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> T unwrap(T t) {
        if (t instanceof InvocationTargetException) {
            return ((T) ((InvocationTargetException) t).getTargetException());
        }
        return t;
    }
}
