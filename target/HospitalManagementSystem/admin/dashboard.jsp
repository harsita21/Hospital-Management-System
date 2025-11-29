<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Hospital MS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .sidebar {
            background: linear-gradient(180deg, #0d6efd 0%, #004aad 100%);
            min-height: calc(100vh - 56px);
        }
        .sidebar .nav-link {
            color: white;
            padding: 12px 20px;
            margin: 5px 0;
            border-radius: 5px;
        }
        .sidebar .nav-link:hover, .sidebar .nav-link.active {
            background-color: rgba(255,255,255,0.2);
        }
        .dashboard-card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            transition: transform 0.3s;
        }
        .dashboard-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/dashboard">
                <i class="fas fa-hospital me-2"></i>Hospital Management System
            </a>
            <div class="navbar-nav ms-auto">
                <span class="navbar-text me-3">
                    <i class="fas fa-user me-1"></i>${sessionScope.username} (Admin)
                </span>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">
                    <i class="fas fa-sign-out-alt me-1"></i>Logout
                </a>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block sidebar">
                <div class="position-sticky pt-3">
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link active" href="${pageContext.request.contextPath}/admin/dashboard">
                                <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/doctors">
                                <i class="fas fa-user-md me-2"></i>Manage Doctors
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/patients">
                                <i class="fas fa-users me-2"></i>Manage Patients
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/appointments">
                                <i class="fas fa-calendar-check me-2"></i>Appointments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/reports">
                                <i class="fas fa-chart-bar me-2"></i>Reports
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
                    <h1 class="h2"><i class="fas fa-tachometer-alt me-2"></i>Admin Dashboard</h1>
                </div>

                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show">
                        ${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <!-- Statistics Cards -->
                <div class="row">
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card dashboard-card border-primary">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col mr-2">
                                        <div class="h2 text-primary">${doctorsCount}</div>
                                        <div class="small text-uppercase text-muted">Total Doctors</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-user-md fa-2x text-primary"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card dashboard-card border-success">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col mr-2">
                                        <div class="h2 text-success">${patientsCount}</div>
                                        <div class="small text-uppercase text-muted">Total Patients</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-users fa-2x text-success"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card dashboard-card border-warning">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col mr-2">
                                        <div class="h2 text-warning">${appointmentsCount}</div>
                                        <div class="small text-uppercase text-muted">Total Appointments</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar-check fa-2x text-warning"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card dashboard-card border-info">
                            <div class="card-body">
                                <div class="row align-items-center">
                                    <div class="col mr-2">
                                        <div class="h2 text-info">${todayAppointments}</div>
                                        <div class="small text-uppercase text-muted">Today's Appointments</div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar-day fa-2x text-info"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Quick Actions -->
                <div class="row mt-4">
                    <div class="col-md-4 mb-3">
                        <div class="card dashboard-card">
                            <div class="card-body text-center py-4">
                                <i class="fas fa-user-md fa-3x text-primary mb-3"></i>
                                <h5>Manage Doctors</h5>
                                <p class="text-muted">Add, edit, or remove doctors</p>
                                <a href="${pageContext.request.contextPath}/admin/doctors" class="btn btn-primary">Go to Doctors</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-3">
                        <div class="card dashboard-card">
                            <div class="card-body text-center py-4">
                                <i class="fas fa-users fa-3x text-success mb-3"></i>
                                <h5>Manage Patients</h5>
                                <p class="text-muted">View and manage patient records</p>
                                <a href="${pageContext.request.contextPath}/admin/patients" class="btn btn-success">Go to Patients</a>
                            </div>
                        </div>
                    </div>

                    <div class="col-md-4 mb-3">
                        <div class="card dashboard-card">
                            <div class="card-body text-center py-4">
                                <i class="fas fa-calendar-check fa-3x text-warning mb-3"></i>
                                <h5>Appointments</h5>
                                <p class="text-muted">Manage all appointments</p>
                                <a href="${pageContext.request.contextPath}/admin/appointments" class="btn btn-warning">View Appointments</a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Recent Activity -->
                <div class="row mt-4">
                    <div class="col-12">
                        <div class="card dashboard-card">
                            <div class="card-header">
                                <h5 class="card-title mb-0">
                                    <i class="fas fa-clock me-2"></i>Recent Appointments
                                </h5>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${not empty recentAppointments}">
                                        <div class="table-responsive">
                                            <table class="table table-hover">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Patient</th>
                                                        <th>Doctor</th>
                                                        <th>Date & Time</th>
                                                        <th>Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="appointment" items="${recentAppointments}">
                                                        <tr>
                                                            <td>#${appointment.id}</td>
                                                            <td>${appointment.patientName}</td>
                                                            <td>Dr. ${appointment.doctorName}</td>
                                                            <td>
                                                                ${appointment.appointmentDate} 
                                                                at ${appointment.appointmentTime}
                                                            </td>
                                                            <td>
                                                                <c:choose>
                                                                    <c:when test="${appointment.status == 'Booked'}">
                                                                        <span class="badge bg-warning">Booked</span>
                                                                    </c:when>
                                                                    <c:when test="${appointment.status == 'Completed'}">
                                                                        <span class="badge bg-success">Completed</span>
                                                                    </c:when>
                                                                    <c:when test="${appointment.status == 'Cancelled'}">
                                                                        <span class="badge bg-danger">Cancelled</span>
                                                                    </c:when>
                                                                </c:choose>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="text-center py-4">
                                            <i class="fas fa-calendar-times fa-3x text-muted mb-3"></i>
                                            <h5>No Recent Appointments</h5>
                                            <p class="text-muted">No appointments have been booked yet.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>