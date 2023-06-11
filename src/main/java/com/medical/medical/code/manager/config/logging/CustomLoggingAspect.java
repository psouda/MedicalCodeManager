	package com.medical.medical.code.manager.config.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomLoggingAspect extends LoggingAspect {
	@Pointcut("execution (* com.medical.medical.code.manager.controller.*.*(..))")
	public void applicationPackagePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}
	@Pointcut("within(@org.springframework.web.bind.annotation.ControllerAdvice *)")
	public void errorHandlerResponsePointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
	public void springBeanPointcut() {
		// Method is empty as this is just a Pointcut, the implementations are in the advices.
	}

	@AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
	public void logAfterThrowingError(JoinPoint joinPoint, Throwable e) {
		logAfterThrowing(joinPoint, e);
	}

	@Around("applicationPackagePointcut() || springBeanPointcut() || errorHandlerResponsePointcut()")
	public Object logAroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		return logAround(joinPoint);
	}

}
