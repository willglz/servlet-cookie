<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login Form</title>
</head>
<body>
<h1>Login Form</h1>
<form action="/servlet-cookie/login" method="post">
    <div>
        <label for="username">Username</label>
        <input type="text" id="username" name="username" placeholder="Username" />
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" id="password" name="password" placeholder="Password" />
    </div>
    <div>
        <button type="submit">Login</button>
    </div>
</form>
</body>
</html>