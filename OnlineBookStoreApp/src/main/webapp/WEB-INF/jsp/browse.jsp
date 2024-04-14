
<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
</head>
<body>
<h1>Product List</h1>
<a href="<c:url value="/shop?action=viewCart" />">View Cart</a><br/><br/>

<c:forEach var="entry" items="${bookDatabase}">

    <a href="<c:url value="/shop">
               <c:param name="action" value="addToCart" />
               <c:param name="productId" value="${entry.id}" />
           </c:url>">

            ${entry.bookName}
    </a><br/>

</c:forEach>


</body>
</html>
