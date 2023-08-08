<%
    if (session.getAttribute("UID") == null) {
%>
<div class="card-header">User Details</div>
<Div class="card-body">
    <a href="login">Click here</a> to login
</Div>
<%
} else {
%>
<div class="card-header">Welcome <b><%=session.getAttribute("UID")%></b></div>
<Div class="card-body">
    Name: <%=session.getAttribute("USERNAME")%><br/>
    Email: <%=session.getAttribute("USEREMAIL")%><br/>
    DoB: <%=session.getAttribute("USERDOB")%><br/>
    Type: <%=session.getAttribute("USERTYPE").toString().equalsIgnoreCase("C") ? "Customer" : session.getAttribute("USERTYPE").toString().equalsIgnoreCase("A") ? "Admin" : "Seller"%>
    
</div>
<%
    }
%>
