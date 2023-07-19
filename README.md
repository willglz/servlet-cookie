# Cabeceras HTTP con Java EE / Jakarta
Sencilla aplicación con Java EE / Jakarta. Trabajamos principalmente con Headers para mostrar información de interés y exportamos datos a Excel y JSON.

## Headers o Cabeceras mas importantes
Por medio del método <b>doGet()</b>, que recibe dos parámetros de tipo <b>HttpServletRequest</b> y <b>HttpServletResponse</b> podemos obtener información de los header o cabeceras.
```java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    resp.setContentType("text/html;charset=UTF-8");
    String methodHttp = req.getMethod();
    String requestUri = req.getRequestURI();
    String requestUrl = req.getRequestURL().toString();
    String contextPath = req.getContextPath();
    String servletPath = req.getServletPath();
    String ip = req.getLocalAddr();
    String port = String.valueOf(req.getLocalPort());
    String scheme = req.getScheme();
    String host = req.getHeader("host");
    String url2 = scheme + "://" + host + contextPath + servletPath;
    String url3 = scheme + "://" + ip + ":" + port + contextPath + servletPath;
    String ipClient = req.getRemoteAddr();
}
```
Con el código anterior podemos visualizar mucha información acerca del método que se utiliza, si es POST o GET, así como información de la URL del Servlet.

## Exportando a Excel
Para exportar datos a un archivo Excel, es decir, con extensión <b>.xls</b> podemos hacerlo desde el mismo servlet y con un condicional verificamos si mostraremos una página web o exportaremos a un archivo <b>.xls</b>
````java
ProductService productService = new ProductServiceImpl();
List<Product> productList = productService.showProducts();
resp.setContentType("text/html;charset=UTF-8");
String servletPath = req.getServletPath();
boolean isXls = servletPath.endsWith(".xls");
if (isXls){
    resp.setContentType("application/vnd.ms-excel");
    resp.setHeader("Content-Disposition", "attachment;filename=products.xls");
}
````
Con el anterior código cargamos un listado de productos y con un <b>if</b> verificamos el servletPath que hemos escogido y así nos redireccionará a una página web o a un archivo .xls para descargar

## Listar como JSON
Para exportar datos a un archivo JSON necesitamos agregar una dependencia a nuestro <b>pom.xml</b>
````xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.1</version>
</dependency>
````
Ahora, creamos un nuevo servlet y el método <b>doGet()</b> colocamos las siguientes lineas para poder exportar a JSON
````java
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ProductService productService = new ProductServiceImpl();
    List<Product> products = productService.showProducts();
    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(products);
    resp.setContentType("application/json");
    resp.getWriter().write(json);
}
````

## Recibir datos JSON y mostrarlos en HTML
Ahora haremos lo contrario, con el método <b>doPost()</b> vamos a recibir desde un cliente datos en formato JSON y los vamos a mostrar en pantalla, para ello debemos de sobreescribir el método <b>doPost()</b> en nuestro Servlet
````java
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletInputStream jsonStream = req.getInputStream();
    ObjectMapper mapper = new ObjectMapper();
    Product product = mapper.readValue(jsonStream, Product.class);
    resp.setContentType("text/html;charset=UTF-8");
    try(PrintWriter out = resp.getWriter()){
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta http-equiv='X-UA-Compatible' content='IE=edge'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Products details from JSON</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Products details from JSON</h1>");
        out.println("<ul>");
        out.println("<li>" + product.getId() + "</li>");
        out.println("<li>" + product.getName() + "</li>");
        out.println("<li>" + product.getBrand() + "</li>");
        out.println("<li>" + product.getPrice() + "</li>");
        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
    }
}
````
Con el código anterior podremos mostrar los datos de un objeto que ha sido enviado desde un cliente. Para ello podemos usar Postman o Insomnia como clientes y así enviar datos a nuestra URL de nuestro servlet para probar su funcionamiento.