package love.aimer.lulu.utils;

/**
 * @author wj42134
 * @date 2018/1/24 10:06
 */
public enum UtilsEnum {

    INSTANCE;

    private ReflectUtil reflectUtil;
    private YamlUtils yamlInstance;

    UtilsEnum() {
        yamlInstance = new YamlUtils();
    }

    public ReflectUtil getReflectInstance() {
        return reflectUtil;
    }

    public YamlUtils getYamlInstance() {
        return yamlInstance;
    }
}
