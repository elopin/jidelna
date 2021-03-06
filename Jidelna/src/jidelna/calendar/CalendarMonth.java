package jidelna.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import jidelna.beans.DayMenuBean;
import jidelna.beans.UserMenuBean;

/**
 * Třída představující měsíc v kalendáři.
 * 
 * @author Lukáš Janáček
 */
public class CalendarMonth {

    private List<CalendarDay> days;
    private Calendar calendar;

    private List<UserMenuBean> userMenus;
    
    private boolean admin;
    
    public CalendarMonth(Calendar calendar) {
	this.calendar = calendar;
	this.days = new ArrayList<CalendarDay>();
	for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
	    calendar.set(Calendar.DAY_OF_MONTH, i);
	    days.add(new CalendarDay(calendar));
	}

    }

    /**
     * Nastaví měsíční denní menu. Podle stavu denního menu nastaví barvy 
     * tlačítek, představující jednotlivé dny v kalendáři.
     * @param menus 
     */
    public void setMenus(List<DayMenuBean> menus) {
	for (CalendarDay calDay : days) {
            calDay.setAdmin(admin);
	    for (DayMenuBean menuBean : menus) {
		if (calendar.get(Calendar.YEAR) == menuBean.getYear()) {
		    if (calendar.get(Calendar.MONTH) == menuBean.getMonth()) {
			if (calDay.getDayInMonth() == menuBean.getDay()) {
			    calDay.setMenuBean(menuBean);
                            calDay.setAdmin(admin);
                            calDay.setUserSelect(checkUserSelection(menuBean));
			}
		    }
		}
	    }
	}
    }
    
    public boolean isAdmin() {
        return admin;
    }
    
    public void setAdmin(boolean isAdmin) {
        this.admin = isAdmin;
    }

    /**
     * Metoda vrací textový řetězec představující html komponentu představující
     * měsíc se dny pro výběr denního menu.
     * @return 
     */
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("<form action=\"\" method=\"post\">");
	sb.append("<table>");
	sb.append("<tr><th>Po</th><th>Út</th><th>St</th><th>Čt</th><th>Pá</th><th>So</th><th>Ne</th></tr><tr>");

	Calendar calendar = Calendar.getInstance();
	calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
	    if (calendar.get(Calendar.DAY_OF_WEEK) == days.get(0).getDayInWeek()) {
		break;
	    }

	    sb.append("<td></td>");

	    calendar.add(Calendar.DAY_OF_WEEK, 1);
	}
	for (int i = 0; i < days.size(); i++) {
	    sb.append("<td>");
	    sb.append(days.get(i).toString());
	    sb.append("</td>");

	    if (days.get(i).getDayInWeek() == Calendar.SUNDAY) {
		sb.append("</tr><tr>");
	    }
	}
	sb.append("</tr></table></form>"); 
	return sb.toString();
    }

    public void setUserMenus(List<UserMenuBean> userMenus) {
        this.userMenus = userMenus;
    }

    private boolean checkUserSelection(DayMenuBean menuBean) {  
        if(userMenus == null) {
            return false;
        }
        if(userMenus.isEmpty()) {
            return false;
        }
        
        for(UserMenuBean userMenuBean : userMenus) {
            if(userMenuBean.getDayMenu().getId() == menuBean.getId()) {
                return true;
            }
        }
        return false;
    }
}
