<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html><head><title>Manage Doctors</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<style>.sidebar{background:linear-gradient(180deg, #0d6efd 0%, #004aad 100%);min-height:calc(100vh - 56px)}.sidebar .nav-link{color:white;padding:12px 20px;margin:5px 0;border-radius:5px}.sidebar .nav-link:hover,.sidebar .nav-link.active{background-color:rgba(255,255,255,0.2)}</style>
</head><body>
<jsp:include page="_adminNav.jsp" />
<div class="container-fluid"><div class="row">
<jsp:include page="_adminSidebar.jsp">
    <jsp:param name="activePage" value="doctors"/>
</jsp:include>
<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
    <h1 class="h2"><i class="fas fa-user-md me-2"></i>Manage Doctors</h1>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
    <div class="card shadow-sm"><div class="card-body"><div class="table-responsive">
    <table class="table table-hover">
        <thead class="table-light">
            <tr><th>ID (User)</th><th>Name</th><th>Username</th><th>Specialization</th><th>Department</th></tr>
        </thead>
        <tbody>
            <c:forEach var="doc" items="${doctorList}">
                <tr>
                    <td>${doc.id}</td>
                    <td>${doc.name}</td>
                    <td>${doc.username}</td>
                    <td>${doc.specialization}</td>
                    <td>${doc.departmentName}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty doctorList}"><td colspan="5" class="text-center text-muted">No doctors found.</td></c:if>
        </tbody>
    </table>
    </div></div></div>
</main>
</div></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>