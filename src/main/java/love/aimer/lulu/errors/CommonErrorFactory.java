
package love.aimer.lulu.errors;

import love.aimer.lulu.errors.base.AbstractErrorFactory;
import love.aimer.lulu.errors.base.LuError;

/**
 * 自定义的异常工厂
 *
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class CommonErrorFactory extends AbstractErrorFactory {

//    /**
//     * @return
//     */
//    public static CommonErrorFactory getInstance() {
//        return CommonErrorFactoryHolder.INSTANCE;
//    }

    /**
     * 指定异常信息文件的名称，该文件在resources\META-INF\messages\common-messages.properties
     */
    @Override
    protected String provideErrorBundleName() {
        return "common";
    }


//    /**
//     * 单例实现
//     */
//    private static final class CommonErrorFactoryHolder {
//        /**
//         * instance
//         */
//        private static final CommonErrorFactory INSTANCE = new CommonErrorFactory();
//
//    }


    public LuError paramError(String arg, String ... args) {
        return createError("Lu00000001", arg, args);
    }
}
