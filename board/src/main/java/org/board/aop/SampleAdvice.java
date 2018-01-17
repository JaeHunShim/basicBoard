package org.board.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {

	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Around("execution(* org.board.service.BoardService*.*(..))")
	public Object BoardServiceLog(ProceedingJoinPoint pjp) throws Throwable{
		long startTime=System.currentTimeMillis();
		logger.info("========================BoardService Log Start==============================================");
		
		logger.info(Arrays.toString(pjp.getArgs()));
		
		Object result= pjp.proceed();
		
		long endTime= System.currentTimeMillis();
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));
		
		logger.info("=========================BoardService Log End================================================");
		
		return result;
	}
	@Around("execution(* org.board.service.ReplyService*.*(..))")
	public Object replyServiceLog(ProceedingJoinPoint pjp) throws Throwable{
		long startTime=System.currentTimeMillis();
		logger.info("=========================ReplyService Log End================================================");
		logger.info(Arrays.toString(pjp.getArgs()));
		
		Object result= pjp.proceed();
		
		long endTime= System.currentTimeMillis();
		logger.info(pjp.getSignature().getName()+":"+(endTime-startTime));
		logger.info("=========================ReplyService Log End================================================");
		
		return result;
	}
}