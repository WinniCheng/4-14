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
            padding: 8px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        h2 {
            margin-top: 20px;
        }
        img {
            max-width: 300px;
            max-height: 300px;
        }
        a.button {
            display: inline-block;
            padding: 8px 12px;
            background-color: #1e69c6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    <c:url var="logoutUrl" value="/logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Logout" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>

<security:authorize access="not (hasRole('ADMIN') or hasRole('USER'))">
    <a href="<c:url value="/login" />" class="button">Login</a>
    <a href="<c:url value="/user/register" />" class="button">Create Account</a><br/><br/>
</security:authorize>

<h2>Book #${bookId}: <c:out value="${book.bookName}"/></h2>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/book/edit/${book.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/book/delete/${book.id}" />">Delete</a>]<br/><br/>
</security:authorize>

<table>
    <tr>
        <td><b>Cover photo:</b></td>
    </tr>
    <tr>
        <td>
            <c:if test="${empty book.attachments}">
                No cover photo
            </c:if>
            <c:if test="${!empty book.attachments}">
                <c:forEach items="${book.attachments}" var="attachment" varStatus="status">
                    <img src="<c:url value="/book/${bookId}/attachment/${attachment.id}" />"/>
                </c:forEach>
            </c:if>
        </td>
    </tr>
</table>

<table>
    <tr>
        <td><b>Book name:</b></td>
        <td><c:out value="${book.bookName}"/></td>
    </tr>
    <tr>
        <td><b>Author:</b></td>
        <td><c:out value="${book.author}"/></td>
    </tr>
    <tr>
        <td><b>Price:</b></td>
        <td>$<c:out value="${book.price}"/></td>
    </tr>
    <tr>
        <td><b>Availability to buy:</b></td>
        <td><c:out value="${book.availability}"/></td>
    </tr>
    <tr>
        <td><b>Description:</b></td>
        <td><c:out value="${book.description}"/></td>
    </tr>
    <tr>
        <security:authorize access="hasRole('ADMIN') or hasRole('USER')">
            <td><a href="<c:url value="/ticket" />">Comments</a></td>
        </security:authorize>

        <security:authorize access="not (hasRole('ADMIN') or hasRole('USER'))">
            <td><i>Login to comment</i></td>
        </security:authorize>

    </tr>
</table>

</br>

<a href="<c:url value="/book" />">Return to list books</a>
</body>
</html>
