package com.hospital.servlet;

import com.hospital.dao.DatabaseConnection;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet("/db-test")
public class DBTestServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>DB Test</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head><body class='container mt-5'>");
        out.println("<h2>MySQL 8 Connection Test</h2>");
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                out.println("<div class='alert alert-success'>");
                out.println("✅ <strong>Success!</strong> Connected to MySQL 8");
                out.println("</div>");
                
                DatabaseMetaData meta = conn.getMetaData();
                out.println("<div class='card mt-3'>");
                out.println("<div class='card-body'>");
                out.println("<h5 class='card-title'>Database Information</h5>");
                out.println("<p><strong>Product:</strong> " + meta.getDatabaseProductName() + "</p>");
                out.println("<p><strong>Version:</strong> " + meta.getDatabaseProductVersion() + "</p>");
                out.println("<p><strong>Driver:</strong> " + meta.getDriverName() + "</p>");
                out.println("<p><strong>URL:</strong> " + meta.getURL() + "</p>");
                out.println("</div></div>");
                
                conn.close();
            }
        } catch (Exception e) {
            out.println("<div class='alert alert-danger'>");
            out.println("❌ <strong>Error:</strong> " + e.getMessage());
            out.println("</div>");
            out.println("<div class='card mt-3'><div class='card-body'>");
            out.println("<h5>Troubleshooting Tips:</h5>");
            out.println("<ul>");
            out.println("<li>Check if MySQL 8 is running</li>");
            out.println("<li>Verify database name and credentials in application.properties</li>");
            out.println("<li>Ensure mysql-connector-java-8.0.x.jar is in lib/ folder</li>");
            out.println("<li>Check if hospital_management database exists</li>");
            out.println("</ul>");
            out.println("</div></div>");
        }
        
        out.println("</body></html>");
    }
}