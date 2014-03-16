/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jidelna.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author elopin
 */
public class DaysMenuBean {
    private List<DayMenuBean> days = new ArrayList<DayMenuBean>();

	public List<DayMenuBean> getDays() {
		if(days.isEmpty()) {
			DayMenuBean today = new DayMenuBean();
			Calendar nextDay = Calendar.getInstance(TimeZone.getDefault(), new Locale("cs","CZ"));
			nextDay.add(Calendar.DAY_OF_MONTH, 1);
			today.setCalendar(nextDay);
			today.setMenu1("Rizoto");
			today.setMenu1price(35);
			today.setMenu2("Kuře");
			today.setMenu2price(45);
			days.add(today);
			
			DayMenuBean dayAfter = new DayMenuBean();
			Calendar dayAfCal = Calendar.getInstance(TimeZone.getDefault(), new Locale("cs","CZ"));
			dayAfCal.add(Calendar.DAY_OF_MONTH, 2);
			dayAfter.setCalendar(dayAfCal);
			dayAfter.setMenu1("Guláš");
			dayAfter.setMenu1price(45);
			dayAfter.setMenu2("Špagety");
			dayAfter.setMenu2price(28);
			days.add(dayAfter);
		}
		return days;
	}

	public void setDays(List<DayMenuBean> days) {
		this.days = days;
	}
}
