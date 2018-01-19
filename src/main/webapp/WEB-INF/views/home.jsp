<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>
    Hello world!
</h1>
<input type="text">
<button id="submit" type="button">a</button>
</body>
<script
        src="https://code.jquery.com/jquery-3.2.1.js"
        integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
        crossorigin="anonymous"></script>
<script>
    $(function () {
        $('#submit').click(function () {
            var username = $('input').val();
            console.log(username);
            $.post("/TxCourse/home", {username: username}, function (r) {
                console.log(r);
            })
        })
    })
</script>
</html>
