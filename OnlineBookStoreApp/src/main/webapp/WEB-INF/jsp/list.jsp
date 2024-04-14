<!DOCTYPE html>
<html>
<head>
    <title>Online Book Store</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            padding: 7px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        h2 {
            margin-top: 18px;
        }
        a.button {
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
<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Logout" class="button"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <br />
</security:authorize>

<security:authorize access="not (hasRole('ADMIN') or hasRole('USER'))">
    <a href="<c:url value="/login" />" class="button">Login</a>
    <a href="<c:url value="/user/register" />" class="button">Create Account</a><br/><br/>
    <i>Login to use more action.</i>
</security:authorize>

<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <a href="<c:url value="/shop?action=viewCart" />" class="button">View Cart</a>
</security:authorize>

<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <a href="<c:url value="/user/edit/${pageContext.request.userPrincipal.name}" />" class="button">Update Personal Information</a>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/user" />" class="button">Manage User Accounts</a><br/>
</security:authorize>

<h2>Online Book Store - Book lists</h2>

<security:authorize access="hasRole('ADMIN')">
    <a href="<c:url value="/book/create" />" class="button">Create a Book</a><br/><br/>
</security:authorize>

<c:choose>
    <c:when test="${fn:length(bookDatabase) == 0}">
        <i>There are no books in the system.</i>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>Book ID</th>
                <th>Book Name</th>
                <th>Author</th>
                <th>Price</th>
                <th>Availability to buy</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${bookDatabase}" var="entry">
                <tr>
                    <td>${entry.id}</td>
                    <td>
                        <a href="<c:url value="/book/view/${entry.id}" />">
                            <c:out value="${entry.bookName}"/>
                        </a>
                    </td>
                    <td><c:out value="${entry.author}"/></td>
                    <td>$<c:out value="${entry.price}"/></td>
                    <td><c:out value="${entry.availability}"/></td>
                    <td>
                        <security:authorize access="not (hasRole('ADMIN') or hasRole('USER'))">
                            <i>Login to use</i>
                        </security:authorize>

                        <security:authorize access="hasRole('ADMIN') or hasRole('USER')">
                            [<a href="<c:url value="/shop">">
                            <c:param name="action" value="addToCart" />
                            <c:param name="productId" value="${entry.id}" />
                            </c:url>">Buy</a>]
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            [<a href="<c:url value="/book/delete/${entry.id}" />">Delete</a>]
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            [<a href="<c:url value="/book/edit/${entry.id}" />">Edit</a>]
                        </security:authorize>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
