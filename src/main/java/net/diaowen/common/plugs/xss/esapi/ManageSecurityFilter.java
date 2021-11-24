package net.diaowen.common.plugs.xss.esapi;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * XSS安全过滤器
 *
 * @author wjl
 * @date 2014-4-10 下午2:12:02
 */
public class ManageSecurityFilter implements Filter {

	private static final String FILTER_APPLIED = ManageSecurityFilter.class.getName() + ".FILTERED";

	private Set<String> excludePathRegex = new HashSet<String>();

	public void setExcludePathRegex(Set<String> excludePathRegex) {
		this.excludePathRegex = excludePathRegex;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)) {
			throw new ServletException("XSSFilter just supports HTTP requests");
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		for (String regex : excludePathRegex) {
			if (uri.matches(regex)) {
				chain.doFilter(request, response);
				return;
			}
		}
		// Apply Filter
		if (null != httpRequest.getAttribute(FILTER_APPLIED)) {
			chain.doFilter(request, response);
			return;
		}
		try {
			request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
			SecurityRequestWrapper requestWrapper = new SecurityRequestWrapper(httpRequest);
			chain.doFilter(requestWrapper, response);
		} finally {
			httpRequest.removeAttribute(FILTER_APPLIED);
		}
	}

	@Override
	public void destroy() {
	}
	
}
