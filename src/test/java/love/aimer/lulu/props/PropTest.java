package love.aimer.lulu.props;

import love.aimer.lulu.utils.PropsReader;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author wj42134
 * @date 2018/1/24 00:08
 */

public class PropTest {

    @Test
    public void testProps(){
        Object object =  PropsReader.yaml2Map("/Users/Erasme/docs/sourceTree/lulu/src/test/resources/lulu/lu-default.yaml");
        System.out.println(object);
    }
}
