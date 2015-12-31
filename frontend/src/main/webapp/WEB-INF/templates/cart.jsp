<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Cart</h1>

<c:if test="${cart.isEmpty()}">
  <p>Your cart is empty.</p>
  <a href="catalog">Back to catalog</a>
</c:if>
<c:if test="${not cart.isEmpty()}">
  <table>
    <tr>
      <th>Name</th>
      <th>Quantity</th>
    </tr>
    <c:forEach items="${cart.items}" var="cartItem">
      <tr>
        <td><c:out value="${cartItem.productName}" /></td>
        <td><c:out value="${cartItem.quantity}" /></td>
      </tr>
    </c:forEach>
  </table>

  <c:if test="${user.loggedIn}">
    <a id="shipping-address-link" href="order-address">Enter shipping address</a>
  </c:if>
  <c:if test="${not user.loggedIn}">
    <a id="login-link" href="login">Login to place order</a>
  </c:if>
</c:if>