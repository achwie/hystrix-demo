<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Login</h1>

<form action="login" method="POST">
  <input type="hidden" name="referrer" value="<c:out value="${referrer}" />" />
  <table>
    <tr>
      <th>Username:</th>
      <th><input name="username" /></th>
    </tr>
    <tr>
      <th>Password:</th>
      <th><input type="password" name="password" /></th>
    </tr>
  </table>
  <input type="submit" value="Login" />
</form>