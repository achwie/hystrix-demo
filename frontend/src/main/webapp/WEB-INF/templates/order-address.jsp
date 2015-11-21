<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Shipping address</h1>

<form action="place-order" method="POST">
  <table>
    <tr>
      <td>Name:</td>
      <td><input name="name" /></td>
    </tr>
    <tr>
      <td>Address:</td>
      <td><input name="address" /></td>
    </tr>
    <tr>
      <td>City:</td>
      <td><input name="city" /></td>
    </tr>
    <tr>
      <td>ZIP:</td>
      <td><input name="zip" /></td>
    </tr>
    <tr>
      <td>Country:</td>
      <td><input name="country" /></td>
    </tr>
  </table>
  <input type="submit" value="Place order" />
</form>