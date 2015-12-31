<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Product Catalog</h1>
<c:if test="${user.loggedIn}">
  <div id="login-status">You are logged in as <c:out value="${user.userName}" />. <a id="logout-link" href="logout">Log out</a></div>
</c:if>
<c:if test="${not user.loggedIn}">
  <div id="login-status">You are not logged in. <a id="login-link" href="login">Log in</a></div>
</c:if>
<div><a id="cart-link" href="view-cart">Items in cart: <c:out value="${cart.totalItemCount}" /></a></div>

<table>
  <tr>
    <th>Name</th>
    <th>Stock</th>
    <th>Action</th>
  </tr>
  <c:forEach items="${catalogItems}" var="catalogItem">
    <tr>
      <td><c:out value="${catalogItem.name}" /></td>
      <td id="stock-count-${catalogItem.id}"><c:out value="${catalogItem.stockQuantity}" /></td>
      <td>
        <form action="add-to-cart" method="POST" id="add-item-${catalogItem.id}">
          <input type="hidden" name="productId" value="${catalogItem.id}" />
          <input type="hidden" name="quantity" value="1" size="2"/> <button type="submit">Add to Cart</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<c:if test="${user.loggedIn}">
  <div><a id="order-history-link" href="my-orders">View my orders</a></div>
</c:if>
