
package love.aimer.lulu.exceptions;

import love.aimer.lulu.errors.base.AbstractErrorFactory;
import love.aimer.lulu.errors.base.LuError;
import love.aimer.lulu.utils.ExceptionHelper;

/**
 * 基础异常，所有的异常定义都应继承该异常
 *
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class LuException extends Exception {

    /**  */
    private static final long serialVersionUID = 1L;

    /**错误描述*/
    private String message;

    /**错误消息对象*/
    private LuError i18nError;

    /**
     * 构造方法
     *
     * @param error 错误实例
     */
    public LuException(LuError error) {
        super();
        setError(error);
    }

    /**
     * 构造方法
     *
     * @param error 错误实例
     * @param cause 异常
     */
    public LuException(LuError error, Throwable cause) {
        super(ExceptionHelper.unwrap(cause));
        setError(error);
    }

    /**
     * 构造方法
     *
     * @param cause 异常
     */
    public LuException(Throwable cause) {
        super(ExceptionHelper.unwrap(cause));
        if (cause != null) {
            setError(AbstractErrorFactory.createStaticError(cause.getMessage() + " (" + cause.getClass().getName() + ")", LuError.DEFAULT_ERROR_CODE));
        }
    }

    /**
     * 获取该异常的错误消息
     *
     * @return 错误消息
     */
    public LuError getError() {
        return i18nError;
    }

    /**
     * 获取错误消息的错误码
     *
     * @return
     */
    public String getErrorCode() {
        return i18nError == null ? LuError.DEFAULT_ERROR_CODE : i18nError.getCode();
    }

    /**
     * @return
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 在原错误信息基础上，添加额外错误描述
     *
     * @param s
     */
    protected void appendMessage(String s) {
        message += s;
    }

    /**
     * 预处理消息
     *
     * @param s
     */
    protected void prependMessage(String s) {
        message = message + ". " + s;
    }

    /**
     * 设置异常的错误消息
     *
     * 一般来说，一条错误消息针对资源文件中的一条配置记录
     * 错误码＝错误描述
     *
     * @param error 资源文件描述的错误消息
     */
    protected void setError(LuError error) {
        this.message = error.getMessage();
        this.i18nError = error;
    }

}
