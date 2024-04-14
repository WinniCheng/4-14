<!DOCTYPE html>
<html>
<head><title>Online Book Store User Management</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Logout" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


<h2>Manage User Accounts</h2>

<a href="<c:url value="/user/create" />">Create a User</a><br /><br />

<c:choose>
    <c:when test="${fn:length(bookUsers) == 0}">
        <i>There are no users in the system.</i>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Username</th><th>Password</th><th>Roles</th><th>Action</th>
            </tr>
            <c:forEach items="${bookUsers}" var="user">
                <tr>
                    <td>${user.username}</td>
                    <td>${fn:substringAfter(user.password, '{noop}')}</td>
                    <td>
                        <c:forEach items="${user.roles}" var="role" varStatus="status">
                            <c:if test="${!status.first}">, </c:if>
                            ${role.role}
                        </c:forEach>
                    </td>
                    <td>
                        [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                        [<a href="<c:url value="/user/edit/${user.username}" />">Edit</a>]
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<br /><br />

<a href="<c:url value="/book" />">Return to list books</a>

</body>
</html>
