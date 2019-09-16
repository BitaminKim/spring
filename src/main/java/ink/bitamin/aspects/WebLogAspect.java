package ink.bitamin.aspects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class WebLogAspect {
    private final static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    private final String POINT_CUT = "execution(public * ink.bitamin.controller.*.*(..))";

    //控制需要切入log的包
    @Pointcut(POINT_CUT)
    public void webLog(){
        logger.info("loadAspect--->:" + POINT_CUT);
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("*********************** Request Detail **************************");
        logger.info("HTTP URL:" + request.getRequestURL().toString());
        logger.info("HTTP Method:" + request.getMethod());
        logger.info("HTTP IP:" + request.getRemoteAddr());
        logger.info("Class Method:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("Class Arguments:" + Arrays.toString(joinPoint.getArgs()));
        logger.info("Request QueryString:" + request.getQueryString());

    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object object = proceedingJoinPoint.proceed();
        logger.info("Time:" + (System.currentTimeMillis() - startTime) + "ms");
        return object;
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) throws Throwable {
        logger.info("response:" + result);
        logger.info("***********************  Request End  ***************************");
    }
}

