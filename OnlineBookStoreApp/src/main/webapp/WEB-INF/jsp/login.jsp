<!DOCTYPE html>
<html>
<head>
    <title>Online Book Store</title>
    <style>
        .button {
            border-radius: 4px;
            background-color: #1e69c6;
            color: white;
            display: inline-block;
            padding: 8px 12px;
            text-decoration: none;
        }
    </style>
</head>
<body>
<c:if test="${param.error != null}">
    <p>Login failed.</p>
</c:if>
<c:if test="${param.logout != null}">
    <p>You have logged out.</p>
</c:if>
<h2>Online Book Store Login</h2>
<form action="login" method="POST">
    <label for="username">Username:</label><br/>
    <input type="text" id="username" name="username"/><br/><br/>

    <label for="password">Password:</label><br/>
    <input type="password" id="password" name="password"/><br/><br/>

    <input type="checkbox" id="remember-me" name="remember-me"/>
    <label for="remember-me">Remember me</label><br/><br/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Log In" class="button"/>
</form>
<br/><br/>
<a href="<c:url value="/book" />">Return to list books</a>

</body>
</html>
