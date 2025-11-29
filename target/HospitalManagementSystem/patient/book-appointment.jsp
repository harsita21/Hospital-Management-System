<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Book Appointment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
</head>
<body>

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/patient/dashboard">
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
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-body p-4">
                        <h2 class="h4 mb-4"><i class="fas fa-calendar-plus me-2"></i>Book a New Appointment</h2>

                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if>

                        <form method="POST" action="${pageContext.request.contextPath}/patient/book-appointment">
                            <div class="mb-3">
                                <label for="doctorId" class="form-label">Doctor</label>
                                <select class="form-select" id="doctorId" name="doctorId" required>
                                    <option value="">Select a doctor...</option>
                                    <c:forEach var="doc" items="${doctorList}">
                                        <option value="${doc.getDoctorId()}">
                                            Dr. ${doc.getName()} (${doc.getSpecialization()})
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label for="appointmentDate" class="form-label">Date</label>
                                    <input type="date" class="form-control" id="appointmentDate" name="appointmentDate" required>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <label for="appointmentTime" class="form-label">Time</label>
                                    <input type="time" class="form-control" id="appointmentTime" name="appointmentTime" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="reason" class="form-label">Reason for Visit</label>
                                <textarea class="form-control" id="reason" name="reason" rows="3" placeholder="e.g., Annual checkup, chest pain..."></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary w-100">Book Appointment</button>
                        </form>
                    </div>
                </div>
                <div class="text-center mt-3">
                    <a href="${pageContext.request.contextPath}/patient/dashboard">&larr; Back to Dashboard</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Set minimum date for appointment to today
        const today = new Date().toISOString().split('T')[0];
        document.getElementById('appointmentDate').setAttribute('min', today);
    </script>
</body>
</html>