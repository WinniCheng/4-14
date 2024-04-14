<!DOCTYPE html>
<html>
<head>
    <title>View Cart</title>
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
        h1 {
            margin-top: 20px;
        }
        a.button {
            display: inline-block;
            padding: 8px 12px;
            background-color: #1e69c6;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .empty-cart-message {
            margin-top: 20px;
            font-style: italic;
        }
    </style>
</head>
<body>

<br>
<a href="<c:url value="/shop?action=emptyCart" />" class="button">Empty Cart</a>
<h1>View Cart</h1>

<c:choose>
    <c:when test="${empty cart}">
        <p class="empty-cart-message">Your cart is empty</p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
            <tr>
                <th>Book Name</th>
                <th>Unit price</th>
                <th>Quantity</th>
                <th>Add/Remove From Cart</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cartItem" items="${cart}">
                <tr>
                    <td>${products[cartItem.key]}</td>
                    <td>$${prices[cartItem.key]}</td>
                    <td>${cartItem.value}</td>
                    <td>
                        [<a href="<c:url value="/shop">
                          <c:param name="action" value="addToCart" />
                           <c:param name="productId" value="${cartItem.key}" /></c:url>">Add</a>]

                        [<a href="<c:url value="/shop">
                          <c:param name="action" value="deleteFromCart" />
                           <c:param name="productId" value="${cartItem.key}" /></c:url>">Remove</a>]
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<br><br>
<a href="<c:url value="/book" />" class="button">Return to Book List</a>
<a href="<c:url value="/checkout" />" class="button">Checkout</a>


<script>
</script>
</body>
</html>
