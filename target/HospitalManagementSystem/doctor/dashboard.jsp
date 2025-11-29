<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Doctor Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-user-md me-2"></i>Doctor Dashboard
            </a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">
                    <i class="fas fa-user me-1"></i>Welcome, ${sessionScope.doctorName}
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                    <i class="fas fa-sign-out-alt me-1"></i>Logout
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="h2">Welcome, Dr. ${sessionScope.doctorName}!</h1>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <div class="card shadow-sm mt-4">
            <div class="card-header">
                <h5 class="mb-0">My Appointments</h5>
            </div>
            <div class="card-body">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Patient</th>
                            <th>Date</th>
                            <th>Time</th>
                            <th>Reason</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="appt" items="${myAppointments}">
                            <tr>
                                <td>${appt.id}</td>
                                <td>${appt.patientName}</td>
                                <td>${appt.appointmentDate}</td>
                                <td>${appt.appointmentTime}</td>
                                <td>${appt.reason}</td>
                                <td><span class="badge bg-warning">${appt.status}</span></td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty myAppointments}">
                            <tr>
                                <td colspan="6" class="text-center text-muted">You have no appointments.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>