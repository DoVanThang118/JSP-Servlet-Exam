<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container mt-3">
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">Employee</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/employee/index">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/employee/add">Add Employee</a>
                    </li>
                </ul>
                <form class="d-flex" role="search" action="/employee/search">
                    <input class="form-control me-2" type="search" name="keyword" placeholder="Search Name" aria-label="Search Name">
                    <input class="form-control me-2" type="search" name="code" placeholder="Search Code" aria-label="Search Code">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>
</div>
<div class="container mt-3">
    <table  class="table table-striped">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Birthday</th>
            <th scope="col">Address</th>
            <th scope="col">Position</th>
            <th scope="col">Department</th>
            <th scope="col">Action</th>
        </tr>
        <c:forEach var="employeeList" items="${employeeList}">
            <tr>
                <td scope="row">
                        ${employeeList.fullname}
                </td>
                <td>
                    <fmt:formatDate value="${employeeList.birthday}" pattern="dd/MM/yyyy" />
                </td>
                <td>
                        ${employeeList.address}
                </td>
                <td>
                        ${employeeList.position}
                </td>
                <td>
                        ${employeeList.department}
                </td>
                <td>
                    <div class="btn-toolbar" role="toolbar" aria-label="Toolbar with button groups">
                        <div class="btn-group me-2" role="group" aria-label="First group">
                            <form action="/employee/delete/?id=<c:out value='${employeeList.id}' />" method="post">
                                <input type="hidden" name="id" value="${employeeList.id}">
                                <input class="btn btn-outline-danger" type="submit" value="Delete">
                            </form>
                        </div>
                        <div class="btn-group me-2" role="group" aria-label="Second group">
                            <form action="/employee/edit/?id=<c:out value='${employeeList.id}' />" method="post">
                                <input type="hidden" name="id" value="${employeeList.id}">
                                <input class="btn btn-outline-warning" type="submit" value="Update">
                            </form>
                        </div>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>



<%--    <nav aria-label="Page navigation example">--%>
<%--        <ul class="pagination justify-content-center">--%>
<%--            <c:if test="${currentPage > 1}"><li class="page-item"></c:if>--%>
<%--            <c:if test="${currentPage <= 1}"><li class="page-item disabled"></c:if>--%>
<%--            <a class="page-link" href="/student/index?page=${currentPage - 1}">Previous</a>--%>
<%--        </li>--%>

<%--            <c:if test="${currentPage >= totalPages}"><li class="page-item disabled"></c:if>--%>
<%--            <c:if test="${currentPage < totalPages}"><li class="page-item"></c:if>--%>
<%--            <a class="page-link" href="/student/index?page=${currentPage + 1}">Next</a>--%>
<%--        </li>--%>
<%--        </ul>--%>
<%--    </nav>--%>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>