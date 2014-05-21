package jidelna.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jidelna.beans.UserBean;

/**
 * Servlet obsluhující přesměrování v aplikaci. Obsluhuje levé menu a
 * formuláře obsažené na jednotlivých stránkách.
 *
 * @author Lukáš Janáček
 */
public class JidelnaServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	HttpSession session = request.getSession();
	
	//volitelné přesměrování, použito při potvrzení smazání a uložení uživatele
	if(session.getAttribute("redirect") != null) {
	    request.getRequestDispatcher((String)session.getAttribute("redirect")).forward(request, response);
	    session.removeAttribute("redirect");
	    
	//tlačítka levého menu    
	} else if (request.getParameter("menu") != null) {
	    session.removeAttribute("calendar");
	    response.sendRedirect("Obedy");    
	} else if (request.getParameter("credit") != null) {
	    request.getRequestDispatcher("WEB-INF/jsp/userCredit.jsp").forward(request, response);
	} else if (request.getParameter("editProfil") != null) {
	    session.setAttribute("edit", ((UserBean) session.getAttribute("user")).getId());
	    request.getRequestDispatcher("WEB-INF/jsp/userForm.jsp").forward(request, response);
	} else if (request.getParameter("home") != null) {
	    request.getRequestDispatcher("WEB-INF/jsp/homepage.jsp").forward(request, response);
	    
	//tlačítka z formulářů pro editaci a smazání uživatele    
	} else if(request.getParameter("newUser") != null) {
            request.getRequestDispatcher("WEB-INF/jsp/userForm.jsp").forward(request, response);
        } else if(request.getParameter("edit") != null) {
            session.setAttribute("edit", Integer.parseInt(request.getParameter("edit")));
            request.getRequestDispatcher("WEB-INF/jsp/userForm.jsp").forward(request, response);
        } else if (request.getParameter("remove") != null) {
	    session.setAttribute("remove", request.getParameter("remove"));
            request.getRequestDispatcher("WEB-INF/jsp/removeUser.jsp").forward(request, response);
        } else if (request.getParameter("saveUser") != null) {
            request.getRequestDispatcher("WEB-INF/jsp/userForm.jsp").forward(request, response);
        } else if (request.getParameter("yesRemove") != null) {
            request.getRequestDispatcher("WEB-INF/jsp/removeUser.jsp").forward(request, response);
	
	//přesměrování po odhlášení uživatele    
        } else if (request.getParameter("logout") != null) {
	   ((UserBean) session.getAttribute("user")).setLoggedIn(false);
           response.sendRedirect("index.jsp"); 
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
