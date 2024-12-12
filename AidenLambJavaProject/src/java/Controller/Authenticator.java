/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author aiden
 */
public class Authenticator {
    public static void setEmployeeID(HttpServletRequest request, int id) {
        HttpSession session = request.getSession();
        session.setAttribute("authenticatedEmpId", id);
    }
    
    public static int getEmployeeID(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Object o = session.getAttribute("authenticatedEmpId");
        if(o == null) {
            return 0;
        }
        else {
             return Integer.parseInt(o.toString());
        }
    }
}
