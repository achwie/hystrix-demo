<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>My orders</h1>

<c:if test="${not empty orders}">
  <table>
    <c:forEach items="${orders}" var="order">
      <tr>
        <th colspan="2">Ordered on <c:out value='${String.format("%TF", order.getOrderDate())}' /></th>
      </tr>
      <c:forEach items="${order.orderItems}" var="orderItem">
        <tr>
          <td><c:out value="${orderItem.productName}" /></td>
          <td><c:out value="${orderItem.quantity}" /></td>
        </tr>
      </c:forEach>
      <tr>
        <td colspan="2"><hr /></td>
      </tr>
    </c:forEach>
  </table>
</c:if>
<c:if test="${empty orders}">
  <p>There is no order history for your account.</p>
</c:if>

<a href="catalog">Back to catalog</a>
