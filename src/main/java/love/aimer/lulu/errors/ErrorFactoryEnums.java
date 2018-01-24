package love.aimer.lulu.errors;

/**
 * @author wj42134
 * @date 2018/1/24 10:06
 */
public enum ErrorFactoryEnums {
    INSTANCE;

    private CommonErrorFactory commonErrorFactory;

    ErrorFactoryEnums() {
        commonErrorFactory = new CommonErrorFactory();
    }

    public CommonErrorFactory getCommonErrorFactory() {
        return commonErrorFactory;
    }

}
