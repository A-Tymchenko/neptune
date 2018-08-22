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
    <h1>Provider</h1>
    <table class="table">
        <c:forEach var="resultThree" items="${requestScope.resultThree}">
            <tr>
                <td><c:out value="${resultThree}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>
<div class="container">
    <div class="row">
        <div class="col-lg-8 mx-auto">
            <form class="form-horizontal" action="/providerform" method="post" modelAttribute="provider">
                <div class="form-group">
                    <div class="col-sm-10">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="name" placeholder="Name" name="name">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="address" placeholder="Address"
                               name="address">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="telephone" placeholder="Telephone"
                               name="telephone">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="country" placeholder="Country"
                               name="country">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default" name="saveEntity" value="saveProvider">Sign in
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="jumbotron text-center">
    <a href="./allproviders"><h1>Get All Providers</h1></a>
</div>
</body>
</html>


