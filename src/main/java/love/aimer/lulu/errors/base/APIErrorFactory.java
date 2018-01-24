
package love.aimer.lulu.errors.base;

/**
 * Core模块错误工厂
 *
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class APIErrorFactory extends AbstractErrorFactory {

    /** 
     * @see AbstractErrorFactory#provideErrorBundleName()
     */
    @Override
    protected String provideErrorBundleName() {
        return "sof-api";
    }

    /**
     * 获取JpaDalErrorFactory单例
     * 
     * @return
     */
    public static APIErrorFactory getInstance() {
        return APIErrorFactoryHolder.FACTORY;
    }

    /**
     * CoreErrorFactoryHolder instance keeper
     * 
     * @author allen
     * @version $Id: APIErrorFactoryHolder.java, v 0.1 2016年2月26日 下午4:20:31 allen Exp $
     */
    private static final class APIErrorFactoryHolder {
        /** instance */
        private static final APIErrorFactory FACTORY = new APIErrorFactory();
    }

    /**
     * 消息服务器启动失败
     * 
     * @param group 消息分组
     * @param nameSrvAddr 消息命名服务器
     * @return
     */
    public LuError producerStartError(String group, String nameSrvAddr) {
        return createError("LY0500102000", group, nameSrvAddr);
    }

    /**
     * 消息投递失败
     * 
     * @param messageId 消息ID
     * @return
     */
    public LuError producerError(String messageId) {
        return createError("LY0500102001", messageId);
    }

    /**
     * 消息序列化失败
     * 
     * @param messageId 消息ID
     * @param serialization 序列化器
     * @return
     */
    public LuError serializeError(String messageId, String serialization) {
        return createError("LY0500102002", messageId, serialization);
    }

    /**
     * 消息反序列化失败
     * 
     * @param messageId 消息ID
     * @param serialization 序列化器
     * @return
     */
    public LuError unserializeError(String messageId, String serialization) {
        return createError("LY0500102003", messageId, serialization);
    }

    /**
     * 消息订阅失败
     * 
     * @param topci
     * @param eventId
     * @return
     */
    public LuError subscribeError(String topci, String eventId) {
        return createError("LY0500102004", topci, eventId);
    }

    /**
     * 消息订阅者启动失败
     * 
     * @param group 消息分组
     * @param nameSrvAddr 消息命名服务器
     * @return
     */
    public LuError subscribeStartError(String group, String nameSrvAddr) {
        return createError("LY0500102005", group, nameSrvAddr);
    }
}
