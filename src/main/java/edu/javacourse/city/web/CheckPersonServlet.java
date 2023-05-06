package edu.javacourse.city.web;

import edu.javacourse.city.dao.PersonCheckDao;
import edu.javacourse.city.dao.PoolConnectionBuilder;
import edu.javacourse.city.domain.PersonRequest;
import edu.javacourse.city.domain.PersonResponse;
import edu.javacourse.city.exception.PersonCheckException;
//import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebServlet(name = "CheckPersonServlet", urlPatterns = {"/checkPerson"})
public class CheckPersonServlet extends HttpServlet {
    //private static final Logger logger = (Logger) LoggerFactory.getLogger(CheckPersonServlet.class);
    private PersonCheckDao dao;

    @Override
    public void init() throws ServletException {
        //logger.info("Servlet is created");
        dao = new PersonCheckDao();
        dao.setConnectionBuilder(new PoolConnectionBuilder());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       // resp.getWriter().println("Get CheckPerson - called");
        req.setCharacterEncoding("UTF-8");
        String surname = req.getParameter("surname");
        PersonRequest pr = new PersonRequest();
        pr.setSurName(surname);
        pr.setGivenName("pavel");
        pr.setPatronymic("nikolaevich");
        pr.setDateOfBirth(LocalDate.of(1995,3,18));
        pr.setStreetCode(1);
        pr.setBuilding("10");
        pr.setExtension("2");
        pr.setApartment("121");


        try {
            PersonResponse ps = dao.checkPerson(pr);
            if (ps.isRegistered()){
                resp.getWriter().write("Registered");
            }else {
                resp.getWriter().write("Not registered");
            }
        } catch (PersonCheckException ex) {
            throw new RuntimeException(ex);
        }
    }
}
