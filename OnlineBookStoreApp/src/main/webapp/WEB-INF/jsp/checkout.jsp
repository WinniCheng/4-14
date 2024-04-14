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
<h1>Checkout success</h1>
<p>Your purchase details:</p>
<br>

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
            </tr>
            </thead>
            <tbody>
            <c:forEach var="cartItem" items="${cart}">
                <tr>
                    <td>${products[cartItem.key]}</td>
                    <td>${prices[cartItem.key]}</td>
                    <td>${cartItem.value}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<p>Total: $${totalPrice}</p>
<a href="<c:url value="/book" />" class="button">Return to Book List</a>



<script>
</script>
</body>
</html>
