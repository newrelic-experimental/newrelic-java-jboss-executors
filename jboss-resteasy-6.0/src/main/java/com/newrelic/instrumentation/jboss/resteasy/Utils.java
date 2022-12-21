package com.newrelic.instrumentation.jboss.resteasy;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.spi.metadata.ResourceMethod;

import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.UriInfo;

public class Utils {

	public static void addAttribute(Map<String, Object> attributes, String key, Object value) {
		if(attributes != null && key != null && !key.isEmpty() && value != null) {
			attributes.put(key, value);
		}
	}

	public static void addURIInfo(Map<String,Object> attributes, UriInfo uriInfo) {
		if(uriInfo != null) {
			addAttribute(attributes, "URIInfo-AbsolutePath", uriInfo.getAbsolutePath());
			addAttribute(attributes, "URIInfo-BaseURI", uriInfo.getBaseUri());
			addAttribute(attributes, "URIInfo-Path", uriInfo.getPath());
			addAttribute(attributes, "URIInfo-RequestUri", uriInfo.getRequestUri());
			List<String> matchedUris = uriInfo.getMatchedURIs();
			if(matchedUris != null) {
				int count = 1;
				for(String matched : matchedUris) {
					addAttribute(attributes, "URIInfo-MatchedUri-"+count, matched);
					count++;
				}
			}
			List<PathSegment> segments = uriInfo.getPathSegments();
			if(segments != null) {
				int count = 1;
				for(PathSegment segment : segments) {
					addAttribute(attributes, "URIInfo-PathSegment-"+count, segment.getPath());
					count++;
				}
			}
		}
	}
	
	public static void addResourceMethod(Map<String,Object> attributes,ResourceMethod method) {
		if(method != null) {
			addAttribute(attributes	,"ResourceMethod-FullPath", method.getFullpath());
			addAttribute(attributes	,"ResourceMethod-Path", method.getPath());
			addAttribute(attributes	,"ResourceMethod-ResourceClass", method.getResourceClass());
			Method theMethod = method.getMethod();
			if(theMethod != null) {
				addAttribute(attributes	,"ResourceMethod-Method-Name", theMethod.getName());
				addAttribute(attributes	,"ResourceMethod-Method-DeclaringClass", theMethod.getDeclaringClass());

			}
		}
	}
}
