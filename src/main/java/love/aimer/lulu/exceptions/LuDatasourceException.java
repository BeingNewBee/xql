package love.aimer.lulu.exceptions;

/**
 * @author wj42134
 * @date 2018/1/23 23:50
 */
public class LuDatasourceException {
    private static LuDatasourceException ourInstance = new LuDatasourceException();

    public static LuDatasourceException getInstance() {
        return ourInstance;
    }

    private LuDatasourceException() {
    }
}
