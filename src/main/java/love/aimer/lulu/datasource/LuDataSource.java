package love.aimer.lulu.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wj42134
 * @date 2018/1/23 23:35
 */
interface LuDataSource {
    /**
     * props define
     */
    String PROP_JDBC_DRIVER = "lu.jdbc.driver";
    String PROP_JDBC_URL = "lu.jdbc.url";
    String PROP_JDBC_USERNAME = "lu.jdbc.userName";
    String PROP_JDBC_PASSWORD = "lu.jdbc.password";
    String PROP_JDBC_DEFAULT_AUTOCOMMIT = "lu.jdbc.defaultAutoCommit";

    String PROP_POOL_MAX_ACTIVE_CONN = "lu.pool.maximumActiveConnections";
    String PROP_POOL_MAX_IDLE_CONN = "lu.pool.maximumIdleConnections";
    String PROP_POOL_MAX_CHECKOUT_TIME = "lu.pool.maximumCheckoutTime";
    String PROP_POOL_TIME_TO_WAIT = "lu.pool.timeToWait";
    String PROP_POOL_PING_QUERY = "lu.pool.pingQuery";
    String PROP_POOL_PING_CONN_OLDER_THAN = "lu.pool.pingConnectionsOlderThan";
    String PROP_POOL_PING_ENABLED = "lu.pool.pingEnabled";
    String PROP_POOL_PING_CONN_NOT_USED_FOR = "lu.pool.pingConnectionsNotUsedFor";
    int expectedConnectionTypeCode = 0;

    String ADD_DRIVER_PROPS_PREFIX = "lu.driver.";
    int ADD_DRIVER_PROPS_PREFIX_LENGTH = ADD_DRIVER_PROPS_PREFIX.length();

    Object POOL_LOCK = new Object();
    List idleConnections = new ArrayList();
    List activeConnections = new ArrayList();
    long requestCount = 0;
    long accumulatedRequestTime = 0;
    long accumulatedCheckoutTime = 0;
    long claimedOverdueConnectionCount = 0;
    long accumulatedCheckoutTimeOfOverdueConnections = 0;
    long accumulatedWaitTime = 0;
    long hadToWaitCount = 0;
    long badConnectionCount = 0;
}
