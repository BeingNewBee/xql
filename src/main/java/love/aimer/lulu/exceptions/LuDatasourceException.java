package love.aimer.lulu.exceptions;

import love.aimer.lulu.errors.base.LuError;

/**
 * @author Erasme
 * @version v0.1 2018-01-24 10:53
 */
public class LuDatasourceException extends LuException {

    /**  */
    private static final long serialVersionUID = 4755634379864220782L;

    /**
     * @param error
     * @param cause
     */
    public LuDatasourceException(LuError error, Throwable cause) {
        super(error, cause);
    }

    /**
     * @param error
     */
    public LuDatasourceException(LuError error) {
        super(error);
    }

    /**
     * @param cause
     */
    public LuDatasourceException(Throwable cause) {
        super(cause);
    }


}
