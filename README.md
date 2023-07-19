# Cookies con Java EE / Jakarta
Sencilla aplicación con Java EE / Jakarta. Trabajamos el uso de cookies para un manejo de sesiones con servlets.

## ¿Qué es una cookie?
Una cookie es un pequeño archivo que se almacena en el navegador del usuario. Las cookies se utilizan para almacenar información que el servidor necesita para identificar al usuario en distintas interacciones con la web o almacenar información del propio usuario para su posterior uso. 

Las Cookies son ampliamente utilizadas en aplicaciones web para mantener estados de sesión, rastrear la actividad del usuario, ofrecer funcionalidades personalizadas y mejorar la usabilidad del sitio. Sin embargo, es importante tener en cuenta que las Cookies pueden plantear problemas de privacidad si contienen información sensible y se utilizan de manera inadecuada, por lo que es esencial asegurarse de que se implementen adecuadamente y se utilicen de manera responsable.

## Manejo de Login con Cookies
Para manejar un sencillo login con cookies, debemos de crear un servlet que nos permita validar el usuario y contraseña, para ello debemos de sobreescribir el método <b>doPost()</b> de nuestro servlet, como por ejemplo:
````java
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String user = req.getParameter("username");
    String pass = req.getParameter("password");
    
    if (user.equals(USER) && pass.equals(PASS)) {
        Cookie userCookie = new Cookie("username", user);
        resp.addCookie(userCookie);
        resp.setContentType("text/html;charset=UTF-8");
        resp.sendRedirect(req.getContextPath() + "/login.html");
    } else {
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "user or password incorrect");
    }
}
````
Si el usuario y contraseña son correctos, se crea una cookie con el nombre de `username` y se redirecciona a una página de bienvenida, en caso contrario se envía un error 401 (Unauthorized).

## Cerrar sesión con Cookies
Para cerrar sesión con cookies, debemos de crear un servlet que nos permita eliminar la cookie creada en el login, para ello debemos de sobreescribir el método <b>doGet()</b> de nuestro servlet, como por ejemplo:
````java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    LoginService loginServ = new LoginServiceImpl();
    Optional<String> userCookie = loginServ.getUsername(req);
    if (userCookie.isPresent()) {
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
    resp.sendRedirect(req.getContextPath() + "/login.html");
}
````