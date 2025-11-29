<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html><head><title>Manage Appointments</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<style>.sidebar{background:linear-gradient(180deg, #0d6efd 0%, #004aad 100%);min-height:calc(100vh - 56px)}.sidebar .nav-link{color:white;padding:12px 20px;margin:5px 0;border-radius:5px}.sidebar .nav-link:hover,.sidebar .nav-link.active{background-color:rgba(255,255,255,0.2)}</style>
</head><body>
<jsp:include page="_adminNav.jsp" />
<div class="container-fluid"><div class="row">
<jsp:include page="_adminSidebar.jsp">
    <jsp:param name="activePage" value="appointments"/>
</jsp:include>
<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
    <h1 class="h2"><i class="fas fa-calendar-check me-2"></i>All Appointments</h1>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
    <div class="card shadow-sm"><div class="card-body"><div class="table-responsive">
    <table class="table table-hover">
        <thead class="table-light">
            <tr><th>ID</th><th>Patient</th><th>Doctor</th><th>Date & Time</th><th>Status</th></tr>
        </thead>
        <tbody>
            <c:forEach var="appt" items="${appointmentList}">
                <tr>
                    <td>${appt.id}</td>
                    <td>${appt.patientName}</td>
                    <td>Dr. ${appt.doctorName}</td>
                    <td>${appt.appointmentDate} at ${appt.appointmentTime}</td>
                    <td>
                        <c:choose>
                            <c:when test="${appt.status == 'Booked'}"><span class="badge bg-warning">Booked</span></c:when>
                            <c:when test="${appt.status == 'Completed'}"><span class="badge bg-success">Completed</span></c:when>
                            <c:when test="${appt.status == 'Cancelled'}"><span class="badge bg-danger">Cancelled</span></c:when>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty appointmentList}"><td colspan="5" class="text-center text-muted">No appointments found.</td></c:if>
        </tbody>
    </table>
    </div></div></div>
</main>
</div></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>