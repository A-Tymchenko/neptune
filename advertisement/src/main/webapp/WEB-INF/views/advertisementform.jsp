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
    <h1>Advertisement</h1>
    <table class="table">
        <c:forEach var="resultOne" items="${requestScope.resultOne}">
            <tr>
                <td><c:out value="${resultOne}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

<div class="container">
    <div class="row">
        <div class="col-lg-12 mx-auto">
            <form class="form-horizontal" action="/saveadvertisement" method="post" modelAttribute="advertisement">
                <div class="form-group">
                    <div class="col-sm-10">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="title" placeholder="Title" name="title">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="context" placeholder="Context"
                               name="context">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="imageUrl" placeholder="ImageUrl"
                               name="imageUrl">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="language" placeholder="Language"
                               name="language">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" name="saveEntity" value="saveAdvert">Sign in
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="jumbotron text-center">
    <a href="./alladvertisementget"><h1>Get All Advertisement</h1></a>
</div>
</body>
</html>