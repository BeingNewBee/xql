package love.aimer.lulu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.Resource;

import java.util.Map;

/**
 * @author wj42134
 * @data 2018/1/23 23:58
 */
public class YamlUtils {
    private static final Logger logger = LoggerFactory.getLogger(YamlUtils.class);
    public static Map<String, Object> yaml2Map(Resource yamlSource) {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();

        yaml.setResources(yamlSource);
        return yaml.getObject();
    }

}
