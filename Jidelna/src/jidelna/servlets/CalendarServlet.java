package jidelna.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jidelna.calendar.CalendarMonth;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarServlet() {
        super();
    }

    
    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException {
	
	HttpSession session = request.getSession();
	
	Locale locale = new Locale("cs","CZ");
    	Calendar calendar = (Calendar)session.getAttribute("calendar");
    	
	if(calendar == null) {
	    calendar = Calendar.getInstance(locale);
	}
	
	if(request.getParameter("before") != null) {
	    calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH))-1);
	}
	if(request.getParameter("after") != null) {
	    calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH))+1);
	}
	session.setAttribute("calendar", calendar);
	CalendarMonth month = new CalendarMonth(calendar);
	
    	response.setContentType("text/html;charset=UTF-8");
    	PrintWriter out = null;
    	try {
    		out = response.getWriter();
    		out.println("<html><head></head><body>");
		out.println("<div>");
		out.println("<form action=\"\">");
		out.println("<input type=\"submit\" name=\"before\" value=\"<\"/>");
		out.println("</form>");
		out.println("<label>");
		out.println(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
		out.println(" "+calendar.get(Calendar.YEAR));
		out.println("</label>");
		out.println("<form action=\"\">");
		out.println("<input type=\"submit\" name=\"after\" value=\">\"/>");
		out.println("</form>");
		out.println("</div>");
		
		
		out.println(month.toString());
			
		out.println("</body></html>");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
    	
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

}
