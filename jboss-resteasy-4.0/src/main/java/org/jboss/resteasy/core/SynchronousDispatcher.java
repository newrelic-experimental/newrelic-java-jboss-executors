package org.jboss.resteasy.core;

import java.util.HashMap;
import java.util.function.Consumer;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.jboss.resteasy.spi.ResourceInvoker;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.jboss.resteasy.Utils;

@Weave
public abstract class SynchronousDispatcher {
	
	@Trace(dispatcher = true)
	public Response internalInvocation(HttpRequest request, HttpResponse response, Object entity) {
		UriInfo info = request.getUri();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addURIInfo(attributes, info);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public Response execute(HttpRequest request, HttpResponse response, ResourceInvoker invoker) {
		UriInfo info = request.getUri();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addURIInfo(attributes, info);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		return Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void asynchronousDelivery(HttpRequest request, HttpResponse response, Response jaxrsResponse, Consumer<Throwable> onComplete) {
		UriInfo info = request.getUri();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addURIInfo(attributes, info);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
	
	@Trace(dispatcher = true)
	public void invoke(HttpRequest request, HttpResponse response, ResourceInvoker invoker) {
		UriInfo info = request.getUri();
		HashMap<String, Object> attributes = new HashMap<String, Object>();
		Utils.addURIInfo(attributes, info);
		NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		Weaver.callOriginal();
	}
}
