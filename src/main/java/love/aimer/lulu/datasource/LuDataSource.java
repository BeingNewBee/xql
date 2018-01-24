package love.aimer.lulu.datasource;

import love.aimer.lulu.exceptions.LuDatasourceException;

import java.util.Map;

/**
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
interface LuDataSource {

    void initialize(Map props) throws LuDatasourceException;

    int assembleConnectionTypeCode(String url, String username, String password);
}
