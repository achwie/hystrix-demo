<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Product Catalog</h1>
<c:if test="${user.loggedIn}">
  <div>You are logged in as <c:out value="${user.userName}" />. <a href="logout">Log out</a></div>
</c:if>
<c:if test="${not user.loggedIn}">
  <div>You are not logged in. <a href="login">Log in</a></div>
</c:if>
<div><a href="view-cart">Items in cart: <c:out value="${cart.totalItemCount}" /></a></div>

<table>
  <tr>
    <th>Name</th>
    <th>Stock</th>
    <th>Action</th>
  </tr>
  <c:forEach items="${products}" var="product">
    <tr>
      <td><c:out value="${product.name}" /></td>
      <td><c:out value="${product.stockQuantity}" /></td>
      <td>
        <form action="add-to-cart" method="POST">
          <input type="hidden" name="productId" value="${product.id}" />
          <input type="hidden" name="quantity" value="1" size="2"/> <button type="submit">Add to Cart</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<c:if test="${user.loggedIn}">
  <div><a href="my-orders">View my orders</a></div>
</c:if>
