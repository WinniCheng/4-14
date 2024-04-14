<!DOCTYPE html>
<html>
<head><title>Online Book Store</title></head>

<body>

<h2>Comment #${ticketId}: <c:out value="${ticket.subject}"/></h2>

<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/ticket/delete/${ticket.id}" />">Delete</a>]
</security:authorize>
<br/><br/>
<i>Customer Name - <c:out value="${ticket.customerName}"/></i><br/><br/>
<c:out value="${ticket.body}"/><br/><br/>


<a href="<c:url value="/ticket" />">Return to comment list</a>
</body>
</html>
