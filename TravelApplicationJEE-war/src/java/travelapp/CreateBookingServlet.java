package travelapp;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import travelapp.hotel.BookingConstraints;
import travelapp.hotel.BookingException;
import travelapp.hotel.entity.Booking;
import travelapp.session.BookingSession;

@SuppressWarnings("serial")
public class CreateBookingServlet extends HttpServlet {
    
    @EJB
    private BookingSession bookingSession;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

	try {
            // TODO we gaan ervan uit dat alles ingevuld is
            String hotel = req.getParameter("hotel");
            String guest = req.getParameter("guest");
            DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            Date startDate = formatter.parse(req.getParameter("startdate"));
            Date endDate = formatter.parse(req.getParameter("enddate"));
            int nbOfBeds = Integer.parseInt(req.getParameter("beds"));
            boolean smokingAllowed = Boolean.parseBoolean(req.getParameter("smoking"));
            double maxPrice = Double.parseDouble(req.getParameter("price"));
	
            BookingConstraints constraints = new BookingConstraints(startDate, endDate, nbOfBeds, maxPrice, smokingAllowed);
            Booking b = bookingSession.createBooking(hotel, constraints, guest);
            
            HttpSession session = req.getSession();
            PlannedTrip trip = (PlannedTrip)session.getAttribute("trip");
	    // If the user has no reservation list, create a new one
	    if(trip == null) {
	    	trip = new PlannedTrip();
	    }
	    trip.add(b);
	    session.setAttribute("trip", trip);
	} catch (ParseException e) {
            System.err.println(e.getMessage());
	} catch (BookingException e) {
            req.setAttribute("msg", e.getMessage());
            try {
		req.getRequestDispatcher("output.jsp").forward(req, resp);
		return;
            } catch (ServletException ex) {
		// redirect to home page
		resp.sendRedirect("index.jsp");
		return;
            }
	}
	resp.sendRedirect("index.jsp");
    }
}
