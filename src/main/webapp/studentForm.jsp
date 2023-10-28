<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <c:if test="${existingStudent == null}">Add Student</c:if>
        <c:if test="${existingStudent != null}">Edit Student</c:if>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
          crossorigin="anonymous">
</head>
<body>
<div class="container mt-3">
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <c:if test="${existingStudent == null}">Add Student</c:if>
                <c:if test="${existingStudent != null}">Edit Student</c:if>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" aria-current="page" href="/student/index">Home</a>
                    </li>
                </ul>
                <form class="d-flex" role="search">
                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Search</button>
                </form>
            </div>
        </div>
    </nav>
</div>
<div class="container mt-3">

    <c:if test="${existingStudent == null}"><form action="/student/create" method="post" ></c:if>
    <c:if test="${existingStudent != null}"><form action="/student/update" method="post" ></c:if>
        <input type="hidden" name="id" class="form-control" value="<c:out value='${existingStudent.id}' />">
        <div class="col-md-4">
            <label for="name" class="form-label">Name:</label>
            <input type="text" name="name" class="form-control" id="name" value="<c:out value='${existingStudent.name}' />" required>
        </div>
        <div class="col-md-4">
            <label for="code" class="form-label">Code:</label>
            <input type="text" name="code" class="form-control" id="code" value="<c:out value='${existingStudent.code}' />" required>
        </div>
        <div class="col-md-4">
            <label for="birthday" class="form-label">Birthday:</label>
            <input type="date" name='birthday' class="form-control" id="birthday" value="<fmt:formatDate value="${date}" pattern="yyyy-MM-dd" />" required>
        </div>
        <div class="col-12 mt-3">
            <button class="btn btn-outline-success" type="submit">Submit</button>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>
