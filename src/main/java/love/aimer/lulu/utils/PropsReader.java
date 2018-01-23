package love.aimer.lulu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.util.HashMap;

/**
 * @author wj42134
 * @date 2018/1/23 23:58
 */
public class PropsReader {
    private static final Logger logger = LoggerFactory.getLogger(PropsReader.class);

    private static Yaml yaml = new Yaml();

    public static String yaml2Map(String yamlSource) {
        File f;
        try {
            f = new File(yamlSource);
            return yaml.load(yamlSource);
        } catch (Exception e) {
            logger.error("Cannot read yaml", e);
            return null;
        } finally {

        }
    }

}
