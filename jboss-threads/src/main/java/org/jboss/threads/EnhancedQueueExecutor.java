package org.jboss.threads;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.jboss.threads.NRRunnable;
import com.newrelic.instrumentation.jboss.threads.Utils;

@Weave
public abstract class EnhancedQueueExecutor {

	
	public void execute(Runnable runnable) {
		NRRunnable wrapper = Utils.getWrapper(runnable);
		if(wrapper != null) {
			runnable = wrapper;
		}
		
		Weaver.callOriginal();
	}
}
