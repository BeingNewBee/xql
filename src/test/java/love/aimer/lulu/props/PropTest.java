package love.aimer.lulu.props;

import org.junit.Test;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * @author wj42134
 * @date 2018/1/24 00:08
 */

public class PropTest {

    @Test
    public void testProps(){
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource("lulu/lu-default.yaml"));
        System.out.println(yaml.getObject());

    }
}
