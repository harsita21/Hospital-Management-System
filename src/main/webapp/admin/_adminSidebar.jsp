<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="col-md-3 col-lg-2 d-md-block sidebar">
    <div class="position-sticky pt-3">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link ${param.activePage == 'dashboard' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/dashboard">
                    <i class="fas fa-tachometer-alt me-2"></i>Dashboard
                </a>
            </li>
            <li class="nav-item">
                 <a class="nav-link ${param.activePage == 'doctors' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/doctors">
                    <i class="fas fa-user-md me-2"></i>Manage Doctors
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param.activePage == 'patients' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/patients">
                    <i class="fas fa-users me-2"></i>Manage Patients
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param.activePage == 'appointments' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/appointments">
                    <i class="fas fa-calendar-check me-2"></i>Appointments
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${param.activePage == 'reports' ? 'active' : ''}" href="${pageContext.request.contextPath}/admin/reports">
                    <i class="fas fa-chart-bar me-2"></i>Reports
                </a>
            </li>
        </ul>
    </div>
</nav>