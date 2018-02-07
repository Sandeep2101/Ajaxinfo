<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Books Store Application</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
           $.get('book',function(responseJson) {
    
            if(responseJson!=null){
                $("#countrytable").find("tr:gt(0)").remove();
                var table1 = $("#countrytable");
                $.each(responseJson, function(key,value) { 
                     var rowNew = $("<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>");
                        rowNew.children().eq(0).text(value['id']); 
                        rowNew.children().eq(1).text(value['title']); 
                        rowNew.children().eq(2).text(value['author']); 
                        rowNew.children().eq(3).text(value['price']); 
rowNew.children().eq(4).append('<a href="/book/edit?id='+value['id']+'">edit</a>');
rowNew.children().eq(5).append('<a href="/book/delete?id='+value['id']+'">delete</a>');
                        rowNew.appendTo(table1);
                });
                }
            });
            $("#tablediv").show();          

});
</script>
</head>
<body>
    <center>
        <h1>Books Management</h1>        
    </center>
    <div align="center">
        <c:if test="${book != null}">
            <form action="edit" method="post">
        </c:if>
        <c:if test="${book == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${book != null}">
                        Edit Book
                    </c:if>
                    <c:if test="${book == null}">
                        Add New Book
                    </c:if>
                </h2>
            </caption>
                <c:if test="${book != null}">
                    <input type="hidden" name="id"  value="<c:out value='${book.id}' />" />
                </c:if>           
            <tr>
                <th>Title: </th>
                <td>
                    <input type="text" name="title" size="45"
                            value="<c:out value='${book.title}' />"
                        />
                </td>
            </tr>
            <tr>
                <th>Author: </th>
                <td>
                    <input type="text" name="author" size="45"
                            value="<c:out value='${book.author}' />"
                    />
                </td>
            </tr>
            <tr>
                <th>Price: </th>
                <td>
                    <input type="text" name="price" size="5"
                            value="<c:out value='${book.price}' />"
                    />
                </td>
            </tr>
            <tr>
             <c:if test="${book != null}">
                        <td colspan="2" align="center">
                    <Button type="save" formaction="/book/update">Submit</Button>
                </td>
                    </c:if>
                    <c:if test="${book == null}">
                        <td colspan="2" align="center">
                    <Button type="save" formaction="/book/insert">Save</Button>
                </td>
                    </c:if>
                
            </tr>
        </table>
        </form>
    </div>

       <c:if test="${book == null}">
                           
    <div id="tablediv">
<table cellspacing="0" id="countrytable"> 
    <tr> 
        <th scope="col">id</th> 
        <th scope="col">title</th> 
        <th scope="col">author</th>          
        <th scope="col">price</th>          
    </tr> 
</table>
</div>
 </c:if>   
</body>
</html>