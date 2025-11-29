<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
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