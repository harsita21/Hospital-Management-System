<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html><head><title>My Appointments</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head><body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/patient/dashboard"><i class="fas fa-hospital me-2"></i>Patient Dashboard</a>
        <div class="navbar-nav ms-auto">
            <span class="navbar-text me-3"><i class="fas fa-user me-1"></i>Welcome, ${sessionScope.patientName}</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm"><i class="fas fa-sign-out-alt me-1"></i>Logout</a>
        </div>
    </div>
</nav>
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h2"><i class="fas fa-calendar-alt me-2"></i>My Appointments</h1>
        <a href="${pageContext.request.contextPath}/patient/book-appointment" class="btn btn-primary"><i class="fas fa-plus me-1"></i> Book New</a>
    </div>
    <c:if test="${not empty sessionScope.success}">
        <div class="alert alert-success">${sessionScope.success}</div>
        <c:remove var="success" scope="session"/>
    </c:if>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger">${sessionScope.error}</div>
        <c:remove var="error" scope="session"/>
    </c:if>
    <div class="card shadow-sm"><div class="card-body"><div class="table-responsive">
    <table class="table table-hover">
        <thead class="table-light">
            <tr><th>ID</th><th>Doctor</th><th>Date</th><th>Time</th><th>Reason</th><th>Status</th></tr>
        </thead>
        <tbody>
            <c:forEach var="appt" items="${appointmentList}">
                <tr>
                    <td>${appt.id}</td>
                    <td>Dr. ${appt.doctorName}</td>
                    <td>${appt.appointmentDate}</td>
                    <td>${appt.appointmentTime}</td>
                    <td>${appt.reason}</td>
                    <td>
                        <c:choose>
                            <c:when test="${appt.status == 'Booked'}"><span class="badge bg-warning">Booked</span></c:when>
                            <c:when test="${appt.status == 'Completed'}"><span class="badge bg-success">Completed</span></c:when>
                            <c:when test="${appt.status == 'Cancelled'}"><span class="badge bg-danger">Cancelled</span></c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty appointmentList}"><td colspan="6" class="text-center text-muted">You have no appointments.</td></c:if>
        </tbody>
    </table>
    </div></div></div>
    <div class="text-center mt-3"><a href="${pageContext.request.contextPath}/patient/dashboard">&larr; Back to Dashboard</a></div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>