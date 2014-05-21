package jidelna.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jidelna.beans.UserBean;

/**
 * Filtr pro nastavení kódování dotazů a kontrolu přihlášení uživatele.
 */
@WebFilter("/")
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

	    HttpServletRequest req = (HttpServletRequest) request;
            req.setCharacterEncoding("UTF-8");
                
	    HttpServletResponse res = (HttpServletResponse) response;
	    res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    res.setHeader("Pragma", "no-cache");
	    res.setDateHeader("Expires", 0);

	    UserBean user = (UserBean) req.getSession().getAttribute("user");
		
	    if (req.getRequestURI().endsWith("index.jsp") || req.getRequestURI().endsWith("/") || req.getRequestURI().endsWith("css")) {
		chain.doFilter(request, response);
	    } else {
                if (user != null){
                    if(user.isLoggedIn()) {
	     	        chain.doFilter(request, response);
	            } else {
                        res.sendRedirect("index.jsp");
                    } 
	        } else {
                    res.sendRedirect("index.jsp");
                }
            }
        }

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
