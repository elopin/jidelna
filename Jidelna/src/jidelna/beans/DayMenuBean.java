/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jidelna.beans;

import java.util.Calendar;

/**
 *
 * @author elopin
 */
public class DayMenuBean {
    
    private String id;
    
    private Calendar calendar;
    private String menu1;
    private int menu1price;
    private String menu2;
    private int menu2price;

    public String getId() {
	return id;
    }
    
    public Calendar getCalendar() {
	return calendar;
    }

    public void setCalendar(Calendar calendar) {
	this.calendar = calendar;
	StringBuilder idsb = new StringBuilder();
	idsb.append(calendar.get(Calendar.DAY_OF_MONTH));
	idsb.append(calendar.get(Calendar.MONTH));
	idsb.append(calendar.get(Calendar.YEAR));
	this.id = idsb.toString();
    }

    public String getMenu1() {
	return menu1;
    }

    public void setMenu1(String menu1) {
	this.menu1 = menu1;
    }

    public int getMenu1price() {
	return menu1price;
    }

    public void setMenu1price(int menu1price) {
	this.menu1price = menu1price;
    }

    public String getMenu2() {
	return menu2;
    }

    public void setMenu2(String menu2) {
	this.menu2 = menu2;
    }

    public int getMenu2price() {
	return menu2price;
    }

    public void setMenu2price(int menu2price) {
	this.menu2price = menu2price;
    }
    
    
}
