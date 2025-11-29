<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Patient Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">
                <i class="fas fa-hospital me-2"></i>Patient Dashboard
            </a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">
                    <i class="fas fa-user me-1"></i>Welcome, ${sessionScope.patientName}
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                    <i class="fas fa-sign-out-alt me-1"></i>Logout
                </a>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h1 class="h2">Welcome, ${sessionScope.patientName}!</h1>
        <p class="lead text-muted">Manage your health and appointments all in one place.</p>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        
        <div class="row mt-4">
            <div class="col-md-6">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="fas fa-calendar-plus fa-3x text-primary mb-3"></i>
                        <h5 class="card-title">Book an Appointment</h5>
                        <p class="card-text">Find a doctor and book a new appointment.</p>
                        <a href="${pageContext.request.contextPath}/patient/book-appointment" class="btn btn-primary mt-auto">Book Now</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card h-100 shadow-sm">
                    <div class="card-body text-center d-flex flex-column justify-content-center align-items-center">
                        <i class="fas fa-calendar-alt fa-3x text-success mb-3"></i>
                        <h5 class="card-title">My Appointments</h5>
                        <p class="card-text">View your upcoming and past appointments.</p>
                        <a href="${pageContext.request.contextPath}/patient/my-appointments" class="btn btn-success mt-auto">View History</a>
                    </div>
                </div>
            </div>
        </div>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>