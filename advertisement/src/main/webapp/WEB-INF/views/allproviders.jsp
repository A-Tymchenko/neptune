<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Advert Project</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
</head>
<body>

<div class="jumbotron text-center">
    <h1>All Providers</h1>
</div>
<div class="container">
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <table class="table">
                <thead class="thead-green">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Address</th>
                    <th scope="col">Telephone</th>
                    <th scope="col">Country</th>
                </tr>
                </thead>
                <c:forEach var="provider" items="${allproviders}">
                    <tr>
                        <td><c:out value="${provider.name}"/></td>
                        <td><c:out value="${provider.address}"/></td>
                        <td><c:out value="${provider.telephone}"/></td>
                        <td><c:out value="${provider.country}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
</body>
</html>