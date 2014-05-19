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
import jidelna.beans.UserMenuBean;
import jidelna.calendar.CalendarMonth;
import jidelna.connection.DataRepository;
import jidelna.connection.DataRepositoryImpl;

/**
 * Servlet implementation class CalendarServlet
 */
@WebServlet("/CalendarServlet")
public class CalendarServlet extends HttpServlet {

    protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        HttpSession session = request.getSession();
        
        DataRepository repository = new DataRepositoryImpl();
        
         if (request.getParameter("saveDay") != null) {
            DayMenuBean newMenu = (DayMenuBean)session.getAttribute("newDayMenu");
            if(newMenu != null) { 
                String menu1 = request.getParameter("menu1");
                if(menu1 != null) {
                    newMenu.setMenu1(menu1);
                }
                String price1 = request.getParameter("price1");
                if(price1 != null) {
                    newMenu.setPrice1(Integer.parseInt(price1));
                }
                String menu2 = request.getParameter("menu2");
                if(menu2 != null) {
                    newMenu.setMenu2(menu2);
                }
                String price2 = request.getParameter("price2");
                if(price2 != null) {
                    newMenu.setPrice2(Integer.parseInt(price2));
                }

                repository.addMenuDay(newMenu);
            }
         }
        
        List<DayMenuBean> menus = repository.getMenuDays();
        if (menus == null) {
            menus = new ArrayList<DayMenuBean>();
        }

        
        UserBean user = (UserBean) session.getAttribute("user");

        DayMenuBean menu = (DayMenuBean)session.getAttribute("dailyMenu");
        if(menu != null) {
        
            if(request.getParameter("confirm") != null) {
                    int selection = Integer.parseInt(request.getParameter("selection"));
                    
                    UserBean repoUser = repository.getUserById(user.getId());
                    if(repoUser != null) {
                        user = repoUser;
                        UserMenuBean userMenu = new UserMenuBean();
                        userMenu.setUser(user);
                        userMenu.setDayMenu(menu);
                        userMenu.setSelection(selection);
                        repository.addUserMenu(userMenu);
                        
                    }
                }
            }
        
        
        List<UserMenuBean> userMenus = repository.getUserMenusByUser(user);
        
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
        if(userMenus != null) {
            month.setUserMenus(userMenus);
        }    
        month.setMenus(menus);
        
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.
        PrintWriter out = null;
        try {
            out = response.getWriter();
            request.getRequestDispatcher("header.jsp").include(request, response);
            out.println("<div id=\"container\">");
	    request.getRequestDispatcher("menu.jsp").include(request, response);
	    out.println("<div id=\"content\">");
	    
            out.println("<div id=\"calendar\">");
            
            out.println("<form id=\"monthSelect\" action=\"\" method=\"post\">");
            out.println("<input type=\"submit\" name=\"before\" value=\"<\"/>");
            out.println("<label>");
            out.println(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
            out.println(" " + calendar.get(Calendar.YEAR));
            out.println("</label>");
            out.println("<input type=\"submit\" name=\"after\" value=\">\"/>");
            out.println("</form>");
	    
            out.println("<form id=\"daySelect\" action=\"\" method=\"post\">");
            out.println(month.toString());
            out.println("</div>");
            
            calendar.set(Calendar.DAY_OF_MONTH, day);
            request.getRequestDispatcher("obedy.jsp").include(request, response);
            
            out.println("</div></div>");
	    request.getRequestDispatcher("footer.jsp").include(request, response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doService(request, response);
    }
}
