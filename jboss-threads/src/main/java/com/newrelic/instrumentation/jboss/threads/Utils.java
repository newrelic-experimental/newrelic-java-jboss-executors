package com.newrelic.instrumentation.jboss.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.RunnableScheduledFuture;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

public class Utils {

	private static List<String> ignoredPackages = new ArrayList<String>();
	
	private static List<String> ignoredClasses = new ArrayList<String>();

	private static List<Class<?>> ignoredSuperClasses = new ArrayList<Class<?>>();

	private static final String NEWRELICAGENT = "com.newrelic.agent";

	
	@SuppressWarnings("unchecked")
	public static <T> NRRunnable getWrapper(Runnable runnable) {		
		if(runnable == null || runnable instanceof NRRunnable || runnable instanceof FutureTask) return null;
		
		
		String fullclassname = runnable.getClass().getName();
		if(ignoredClasses.contains(fullclassname)) return null;
		
		for(Class<?> theClass : ignoredSuperClasses) {
			if(theClass.isInstance(runnable)) return null;
		}
						
		Package runPackage = runnable.getClass().getPackage();
		
		if(runPackage.getName().startsWith(NEWRELICAGENT)) return null;
		
		for(String ignore : ignoredPackages) {
			if(runPackage.getName().startsWith(ignore)) return null;
		}
				
		Token token = NewRelic.getAgent().getTransaction().getToken();
		if(!token.isActive()) {
			token.expire();
			token = null;
			return null;
		}

		if(runnable instanceof RunnableScheduledFuture) {
			return new NRRunnableScheduledFuture<T>((RunnableScheduledFuture<T>) runnable, token);
		}
		
		if(runnable instanceof RunnableFuture) {
			return new NRRunnableFuture<T>((RunnableFuture<T>)runnable, token);
		}

		if(runnable instanceof Comparable) {
			return new NRComparableRunnable<T>(runnable, token);
		}
		return new NRRunnable(runnable,token);
	}
}
