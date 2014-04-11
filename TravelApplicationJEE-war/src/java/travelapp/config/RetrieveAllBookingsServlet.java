/**
 *     Copyright (c) KU Leuven Research and Development - iMinds-DistriNet
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     Administrative Contact: dnet-project-office@cs.kuleuven.be
 *     Technical Contact: stefan.walraven@cs.kuleuven.be
 */
package travelapp.config;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import travelapp.hotel.entity.Booking;
import travelapp.session.ManagerSession;

@SuppressWarnings("serial")
public class RetrieveAllBookingsServlet extends HttpServlet {
    
    @EJB
    private ManagerSession managerSession;
	
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        List<Booking> bookings = managerSession.getBookings();
	req.setAttribute("bookings", bookings);
		
	try {
            req.getRequestDispatcher("bookings.jsp").forward(req, resp);
	} catch (ServletException e) {
            // redirect to home page
            resp.sendRedirect("index.jsp");
	}
    }
}
