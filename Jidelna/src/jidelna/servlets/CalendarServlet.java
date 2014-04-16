package jidelna.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jidelna.beans.DayMenuBean;
import jidelna.beans.UserBean;
import jidelna.calendar.CalendarMonth;
import jidelna.connection.DataRepository;
import jidelna.connection.DataRepositoryImpl;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        HttpSession session = request.getSession();
        session.setAttribute("lastURI", request.getRequestURI());
        
        DataRepository repository = new DataRepositoryImpl();
        List<DayMenuBean> menus = repository.getMenuDays();
        if (menus == null) {
            menus = new ArrayList<DayMenuBean>();
        }

        UserBean user = (UserBean) session.getAttribute("user");

        Locale locale = new Locale("cs", "CZ");
        Calendar calendar = (Calendar) session.getAttribute("calendar");
        
        if (calendar == null) {
            calendar = Calendar.getInstance(locale);
        }

        int day = 0;
        if(request.getParameter("menuDay") != null) {
            day = Integer.parseInt(request.getParameter("menuDay"));
        }
        if(day == 0) {
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, day);
        }
        
        if (request.getParameter("before") != null) {
            request.setAttribute("before", true);
            calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH)) - 1);
        }

        if (request.getParameter("after") != null) {
            request.setAttribute("after", true);
            calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH)) + 1);
        }
        session.setAttribute("calendar", calendar);
        
        CalendarMonth month = new CalendarMonth(calendar);
        month.setAdmin(user.isAdmin());
        month.setMenus(menus);
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        PrintWriter out = null;
        try {
            out = response.getWriter();
            request.getRequestDispatcher("header.jsp").include(request, response);
            out.println("<html><head></head><body>");
            out.println("<div>");
            out.println("<form action=\"\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"before\" value=\"<\"/>");
            out.println("</form>");
            out.println("<label>");
            out.println(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
            out.println(" " + calendar.get(Calendar.YEAR));
            out.println("</label>");
            out.println("<form action=\"\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"after\" value=\">\"/>");
            out.println("</form>");
            out.println("</div>");
            out.println("<form action=\"\" method=\"post\">");
            
            out.println(month.toString());
            
            out.println("Menu dne: " +day);
            out.println(" "+calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
            out.println(" " + calendar.get(Calendar.YEAR));
            
            calendar.set(Calendar.DAY_OF_MONTH, day);
            request.getRequestDispatcher("obedy.jsp").include(request, response);
            
            out.println("</form>");
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
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

}
