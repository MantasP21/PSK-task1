package lt.vu.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@LoggedInvocation
public class MethodLogger implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodLogger.class);

    @AroundInvoke
    public Object logMethodInvocation(InvocationContext context) throws Exception {
        LOGGER.info("Called method: {}", context.getMethod().getName());
        return context.proceed();
    }
}
