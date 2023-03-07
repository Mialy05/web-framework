package etu1834.framework.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * FrontServlet
 */
@WebServlet(name="FrontServlet", urlPatterns = "/*")
public class FrontServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.getWriter().println("okee");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}