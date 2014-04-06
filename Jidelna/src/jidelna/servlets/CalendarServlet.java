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
import jidelna.beans.DayMenuBean;
import jidelna.beans.DaysMenuBean;
import jidelna.beans.UserBean;
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
	DaysMenuBean menus = (DaysMenuBean)request.getServletContext().getAttribute("menus");
	DayMenuBean todayMenu = null;
	
	UserBean user = (UserBean)session.getAttribute("user");
	
	
	Locale locale = new Locale("cs","CZ");
    	Calendar calendar = (Calendar)session.getAttribute("calendar");
    	
	if(calendar == null) {
	    calendar = Calendar.getInstance(locale);
	}
	
	if(request.getParameter("before") != null) {
	    request.setAttribute("before", true);
	    calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH))-1);   
	}
	
	if(request.getParameter("after") != null) {
	    request.setAttribute("after", true);
	    calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH))+1);
	}
	session.setAttribute("calendar", calendar);
	CalendarMonth month = new CalendarMonth(calendar);
        month.setAdmin(user.isAdmin());
	if(menus != null) {
	    month.setMenus(menus);
	    if(request.getParameter("menuDay") != null) {
		String id = request.getParameter("menuDay")+calendar.get(Calendar.MONTH)+calendar.get(Calendar.YEAR);
		for(DayMenuBean dayMenu : menus.getDays()) {
		    if(dayMenu.getId().equals(id)) {
			todayMenu = dayMenu;
		    }
		}
	    }
	}
	
    	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
	response.setDateHeader("Expires", 0); // Proxies.
    	PrintWriter out = null;
    	try {
    		out = response.getWriter();
    		out.println("<html><head></head><body>");
		out.println("<div>");
		out.println("<form action=\"\" method=\"post\">");
		out.println("<input type=\"submit\" name=\"before\" value=\"<\"/>");
		out.println("</form>");
		out.println("<label>");
		out.println(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
		out.println(" "+calendar.get(Calendar.YEAR));
		out.println("</label>");
		out.println("<form action=\"\" method=\"post\">");
		out.println("<input type=\"submit\" name=\"after\" value=\">\"/>");
		out.println("</form>");
		out.println("</div>");
		
		out.println(menus.getDays().get(0).getCalendar().getTime().toString());
		out.println("<form action=\"\" method=\"post\">");
		out.println(month.toString());
		out.println("</form>");
		
		if(todayMenu != null) {
		    out.println(todayMenu.getMenu1()+", cena: "+todayMenu.getMenu1price());
		    out.println(todayMenu.getMenu2()+", cena: "+todayMenu.getMenu2price());
		}
		
		out.println("<form action=\"homepage.jsp\" method=\"post\">");
		out.println("<input type=\"submit\" name=\"back\" value=\"Zpět\"/>");
		out.println("</form>");
                
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
