package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("""
            <html>
            <body>
                <h1>Hello Network!</h1>
                <p>Method: GET</p>
                <p>URI: %s</p>
                <p>Remote Addr: %s</p>
                <p>User-Agent: %s</p>
            </body>
            </html>
            """.formatted(req.getRequestURI(), req.getRemoteAddr(), req.getHeader("User-Agent")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().write("""
            <html>
            <body>
                <h1>Hello Network!</h1>
                <p>Method: POST</p>
                <p>URI: %s</p>
            </body>
            </html>
            """.formatted(req.getRequestURI()));
    }
}
