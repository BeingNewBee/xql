
package love.aimer.lulu.errors.base;

import love.aimer.lulu.i18n.AbstractMessageFactory;
import love.aimer.lulu.i18n.Message;

/**
 * 错误消息资源创建工厂
 *
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public abstract class AbstractErrorFactory extends AbstractMessageFactory {

    private static final String BUNDLE_ERROR_SUFFIX = "-error";

    /**
     * 获取错误消息资源文件名称，资源文件名称是不包含[error-messages]后缀，和文件扩展类型
     * 通过提供的错误消息资源文件名称，消息工厂计算出资源文件完整路径
     * 
     * 资源文件路径：
     * <code>META-INF/messages/bundleName-error-messages.properties</code>
     * 
     * @return 错误消息资源文件名称
     */
    protected abstract String provideErrorBundleName();

    /**
     * @see love.aimer.lulu.i18n.AbstractMessageFactory#provideBundleName()
     */
    @Override
    protected final String provideBundleName() {
        return provideErrorBundleName() + BUNDLE_ERROR_SUFFIX;
    }

    /**
     * 创建错误消息对象
     * 
     * @param message 错误描述
     * @param errorCode 错误代码
     * @return 错误消息
     */
    public static LuError createStaticError(String message, String errorCode) {
        return new LuError(message, errorCode);
    }

    /**
     * 从错误消息描述资源文件中根据错误代码创建错误消息对象
     * 
     * @param errorCode 错误代码
     * @param args 错误消息占位符参数
     * @return 错误消息
     */
    protected final LuError createError(String errorCode, Object... args) {
        Message msg = getMessage(errorCode, args);
        LuError error = new LuError(msg.getMessage(), errorCode, args);
        return error;
    }
}
