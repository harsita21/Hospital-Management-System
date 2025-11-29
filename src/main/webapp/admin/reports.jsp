<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html><head><title>Reports</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
<style>.sidebar{background:linear-gradient(180deg, #0d6efd 0%, #004aad 100%);min-height:calc(100vh - 56px)}.sidebar .nav-link{color:white;padding:12px 20px;margin:5px 0;border-radius:5px}.sidebar .nav-link:hover,.sidebar .nav-link.active{background-color:rgba(255,255,255,0.2)}</style>
</head><body>
<jsp:include page="_adminNav.jsp" />
<div class="container-fluid"><div class="row">
<jsp:include page="_adminSidebar.jsp">
    <jsp:param name="activePage" value="reports"/>
</jsp:include>
<main class="col-md-9 ms-sm-auto col-lg-10 px-md-4 py-4">
    <h1 class="h2"><i class="fas fa-chart-bar me-2"></i>Reports</h1>
    <div class="alert alert-info mt-4">This page is a placeholder for future reports.</div>
</main>
</div></div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>