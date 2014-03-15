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

import jidelna.calendar.model.CalendarMonth;

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
    	Calendar calendar = Calendar.getInstance(new Locale("cs","CZ"));
    	
    	response.setCharacterEncoding("UTF-8");
    	//response.setContentType("html/text");
    	PrintWriter out = null;
    	try {
    		out = response.getWriter();
    		out.println("<html><head></head><body>");
    		
			
			out.println(calendar.getTime().toString());
			out.println(calendar.get(Calendar.DAY_OF_MONTH));
			out.println(calendar.get(Calendar.MONTH));
			out.println(calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
			out.println(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			
			CalendarMonth month = new CalendarMonth(Calendar.MONTH);
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
