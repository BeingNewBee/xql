package love.aimer.lulu.datasource;

import love.aimer.lulu.errors.ErrorFactoryEnums;
import love.aimer.lulu.exceptions.LuDatasourceException;
import love.aimer.lulu.utils.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class SimpleDataSource implements LuDataSource {
    private Logger logger = LoggerFactory.getLogger(SimpleDataSource.class);
    /**
     * props define
     */
    private String PROP_JDBC_DRIVER = "lu.jdbc.driver";
    private String PROP_JDBC_URL = "lu.jdbc.url";
    private String PROP_JDBC_USERNAME = "lu.jdbc.userName";
    private String PROP_JDBC_PASSWORD = "lu.jdbc.password";
    private String PROP_JDBC_DEFAULT_AUTOCOMMIT = "lu.jdbc.defaultAutoCommit";

    private String PROP_POOL_MAX_ACTIVE_CONN = "lu.pool.maximumActiveConnections";
    private String PROP_POOL_MAX_IDLE_CONN = "lu.pool.maximumIdleConnections";
    private String PROP_POOL_MAX_CHECKOUT_TIME = "lu.pool.maximumCheckoutTime";
    private String PROP_POOL_TIME_TO_WAIT = "lu.pool.timeToWait";
    private String PROP_POOL_PING_QUERY = "lu.pool.pingQuery";
    private String PROP_POOL_PING_CONN_OLDER_THAN = "lu.pool.pingConnectionsOlderThan";
    private String PROP_POOL_PING_ENABLED = "lu.pool.pingEnabled";
    private String PROP_POOL_PING_CONN_NOT_USED_FOR = "lu.pool.pingConnectionsNotUsedFor";
    private int expectedConnectionTypeCode = 0;

    private String ADD_DRIVER_PROPS_PREFIX = "lu.driver.";
    private int ADD_DRIVER_PROPS_PREFIX_LENGTH = ADD_DRIVER_PROPS_PREFIX.length();
    private Object POOL_LOCK = new Object();
    private List idleConnections = new ArrayList();
    private List activeConnections = new ArrayList();
    private long requestCount = 0;
    private long accumulatedRequestTime = 0;
    private long accumulatedCheckoutTime = 0;
    private long claimedOverdueConnectionCount = 0;
    private long accumulatedCheckoutTimeOfOverdueConnections = 0;
    private long accumulatedWaitTime = 0;
    private long hadToWaitCount = 0;
    private long badConnectionCount = 0;


    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private boolean jdbcDefaultAutoCommit;
    private Properties driverProps;
    private boolean useDriverProps;

    private int poolMaximumActiveConnections;
    private int poolMaximumIdleConnections;
    private int poolMaximumCheckoutTime;
    private int poolTimeToWait;
    private String poolPingQuery;
    private boolean poolPingEnabled;
    private int poolPingConnectionsOlderThan;
    private int poolPingConnectionsNotUsedFor;

    public SimpleDataSource(Map props) {
        try {
            initialize(props);
        } catch (LuDatasourceException e) {
            logger.error("LuDatasourceException", "", "", "{}", e);
        }
    }

    @Override
    public void initialize(Map props) throws LuDatasourceException {
        try {
            String prop_pool_ping_query = null;

            if (props == null) {
                throw new LuDatasourceException(ErrorFactoryEnums.INSTANCE.getCommonErrorFactory()
                        .paramError("SimpleDataSource: The properties map passed to the initializer was null."));
            }

            if (!(props.containsKey(PROP_JDBC_DRIVER)
                    && props.containsKey(PROP_JDBC_URL)
                    && props.containsKey(PROP_JDBC_USERNAME)
                    && props.containsKey(PROP_JDBC_PASSWORD))) {
                throw new LuDatasourceException(ErrorFactoryEnums.INSTANCE.getCommonErrorFactory()
                        .paramError("SimpleDataSource: Some properties were not set."));
            } else {

                jdbcDriver = (String) props.get(PROP_JDBC_DRIVER);
                jdbcUrl = (String) props.get(PROP_JDBC_URL);
                jdbcUsername = (String) props.get(PROP_JDBC_USERNAME);
                jdbcPassword = (String) props.get(PROP_JDBC_PASSWORD);

                poolMaximumActiveConnections =
                        props.containsKey(PROP_POOL_MAX_ACTIVE_CONN)
                                ? Integer.parseInt((String) props.get(PROP_POOL_MAX_ACTIVE_CONN))
                                : 10;

                poolMaximumIdleConnections =
                        props.containsKey(PROP_POOL_MAX_IDLE_CONN)
                                ? Integer.parseInt((String) props.get(PROP_POOL_MAX_IDLE_CONN))
                                : 5;

                poolMaximumCheckoutTime =
                        props.containsKey(PROP_POOL_MAX_CHECKOUT_TIME)
                                ? Integer.parseInt((String) props.get(PROP_POOL_MAX_CHECKOUT_TIME))
                                : 20000;

                poolTimeToWait =
                        props.containsKey(PROP_POOL_TIME_TO_WAIT)
                                ? Integer.parseInt((String) props.get(PROP_POOL_TIME_TO_WAIT))
                                : 20000;

                poolPingEnabled =
                        props.containsKey(PROP_POOL_PING_ENABLED)
                                && Boolean.valueOf((String) props.get(PROP_POOL_PING_ENABLED));

                prop_pool_ping_query = (String) props.get(PROP_POOL_PING_QUERY);
                poolPingQuery =
                        props.containsKey(PROP_POOL_PING_QUERY)
                                ? prop_pool_ping_query
                                : "NO PING QUERY SET";

                poolPingConnectionsOlderThan =
                        props.containsKey(PROP_POOL_PING_CONN_OLDER_THAN)
                                ? Integer.parseInt((String) props.get(PROP_POOL_PING_CONN_OLDER_THAN))
                                : 0;

                poolPingConnectionsNotUsedFor =
                        props.containsKey(PROP_POOL_PING_CONN_NOT_USED_FOR)
                                ? Integer.parseInt((String) props.get(PROP_POOL_PING_CONN_NOT_USED_FOR))
                                : 0;

                jdbcDefaultAutoCommit =
                        props.containsKey(PROP_JDBC_DEFAULT_AUTOCOMMIT)
                                && Boolean.valueOf((String) props.get(PROP_JDBC_DEFAULT_AUTOCOMMIT));

                useDriverProps = false;
                Iterator propIter = props.keySet().iterator();
                driverProps = new Properties();
                driverProps.put("user", jdbcUsername);
                driverProps.put("password", jdbcPassword);
                while (propIter.hasNext()) {
                    String name = (String) propIter.next();
                    String value = (String) props.get(name);
                    if (name.startsWith(ADD_DRIVER_PROPS_PREFIX)) {
                        driverProps.put(name.substring(ADD_DRIVER_PROPS_PREFIX_LENGTH), value);
                        useDriverProps = true;
                    }
                }

                expectedConnectionTypeCode = assembleConnectionTypeCode(jdbcUrl, jdbcUsername, jdbcPassword);

                ReflectUtil.on(jdbcDriver);

                if (poolPingEnabled && (!props.containsKey(PROP_POOL_PING_QUERY) ||
                        prop_pool_ping_query.trim().length() == 0)) {
                    throw new LuDatasourceException(ErrorFactoryEnums.INSTANCE.getCommonErrorFactory()
                            .paramError("SimpleDataSource: property '" + PROP_POOL_PING_ENABLED + "' is true, but property '" +
                                    PROP_POOL_PING_QUERY + "' is not set correctly."));
                }
            }

        } catch (Exception e) {
            throw new LuDatasourceException(ErrorFactoryEnums.INSTANCE.getCommonErrorFactory()
                    .paramError("SimpleDataSource: Error while loading properties."));
        }
    }

    @Override
    public int assembleConnectionTypeCode(String url, String username, String password) {
        return ("" + url + username + password).hashCode();
    }
}
