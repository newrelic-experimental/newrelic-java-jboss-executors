package org.jboss.resteasy.core;

import java.util.HashMap;
import java.util.concurrent.CompletionStage;

import org.jboss.resteasy.specimpl.BuiltResponse;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.metadata.ResourceMethod;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.jboss.resteasy.Utils;

@Weave
public abstract class ResourceMethodInvoker {
	
	protected ResourceMethod method = Weaver.callOriginal();

	@Trace
	public CompletionStage<BuiltResponse> invoke(HttpRequest request, HttpResponse response, Object target) {
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addResourceMethod(attributes, method);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		String fullPath = method != null ? method.getFullpath() : null;
		if(fullPath != null) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.CUSTOM_LOW, true, "RestEast",fullPath);
		}
		return Weaver.callOriginal();
	}
}
