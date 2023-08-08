<%@page import="com.shopping.model.NewProduct"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style type="text/css">
            npouter{
                display: flex;
                flex-direction: row;
                flex-grow: 1;
                min-height: 200px;
                border:1px solid lightgray;
                margin:8px;
                border-radius: 10px;
                background-color: white;
            }
            npouter:hover{
                box-shadow: 0 0 11px rgba(33,33,33,.4);
                border: 1px solid #bbb;
            }
            npleft{
                width: 200px;
            }
            npright{
                display: flex;
                flex-grow: 1;
                flex-direction: column;
            }
            nptop{
                min-height: 50px;
                border-bottom: 1px solid lightsteelblue;
            }
            npdetail{
                flex-grow: 1;
            }
            npbottom{
                height: 30px;
            }
            .bodymain{
                background-color: lightsteelblue;
            }
            .bodyrighttop{
                border-radius: 10px!important;
                margin-top: 8px;
            }
        </style>
    </head>
    <body>
        <%
            DemoService ds = new DemoService();
            List<NewProduct> np = ds.getNewProductList();
            for (int i = 0; i < np.size(); i++) {
        %>
    <npouter>
        <npleft>
            <form name="myform" id="myform" action="showProduct" method="post"><input type="hidden" name="hprodid" value="<%=np.get(i).getProdid()%>">
                <img onclick="javascript:document.myform[<%=i%>].submit();" src="<%=np.get(i).getPath()%><%=np.get(i).getFilename()%>" alt="" style="max-width: 200px;max-height: 200px;padding:4px; cursor: pointer; ">
            </form>
        </npleft>
        <npright>
            <nptop>
                <b><%=np.get(i).getProd_desc()%></b><br>
                <%=np.get(i).getMaincat()%>/<%=np.get(i).getSubcat()%>
            </nptop>
            <npdetail>
                <table border="0">
                    <tr><td>Variant</td><td>:</td><td style="padding-left: 4px;"><%=np.get(i).getVariant()%></td></tr>
                    <tr><td>Price</td><td>:</td><td style="padding-left: 4px;">&#x20b9;<%=np.get(i).getUnitpricestring()%></td></tr>
                    <tr><td>Discount</td><td>:</td><td style="padding-left: 4px;"><%=np.get(i).getDiscount()%>%</td></tr>
                    <tr><td>Seller</td><td>:</td><td style="padding-left: 4px;"><%=np.get(i).getSeller()%></td></tr>
                </table>
            </npdetail>
            <npbottom>
                <% if (session.getAttribute("UID") != null) { %>
                <img src="images/like.png" width="24" height="24" alt="like"/>
                <img src="images/dislike.png" width="24" height="24" alt="dislike"/>
                <% } %>
            </npbottom>
        </npright>
    </npouter>
    <%
        }
    %>
</body>
</html>
