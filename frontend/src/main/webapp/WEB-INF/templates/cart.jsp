<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Cart</h1>

<table>
  <tr>
    <th>Name</th>
    <th>Quantity</th>
  </tr>
  <c:forEach items="${cart.items}" var="cartItem">
    <tr>
      <td><c:out value="${cartItem.product.name}" /></td>
      <td><c:out value="${cartItem.quantity}" /></td>
    </tr>
  </c:forEach>
</table>
<a href="order-address">Enter shipping address</a>
