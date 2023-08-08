<%@page import="com.shopping.model.NewProduct"%>
<%@page import="java.util.List"%>
<%@page import="com.shopping.service.DemoService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html 
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
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
    <outer>
        <cheader>
            <jsp:include page="pgheader.jsp" flush="true" />
        </cheader>
        <cmenu>
            <jsp:include page="pgmenu.jsp" flush="true" />
        </cmenu>
        <cbody>
            <div class="bodyleft">
                <jsp:include page="leftmenu.jsp" flush="true"/>
            </div>
            <div class="bodymain">
                <div style="color: white; text-align: center;font-weight: 600;font-size: 22px;">Smart Phones</div>
                <%
                    DemoService ds = new DemoService();
                    List<NewProduct> np = ds.getMobileProductList();
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
                                <tr><td>Discount</td><td>:</td><td style="padding-left: 4px; color: crimson; font-weight: 600; font-size: 18px;"><%=np.get(i).getDiscount()%>%</td></tr>
                                <tr><td>Selling Price</td><td>:</td><td style="padding-left: 4px; font-weight: 600; font-size: 18px;">&#x20b9;<%=np.get(i).getUnitpricestring()%></td></tr>
                                <tr><td>Seller</td><td>:</td><td style="padding-left: 4px;"><%=np.get(i).getSeller()%></td></tr>
                            </table>
                        </npdetail>
                        <npbottom>
                            <!--< % if (session.getAttribute("UID") != null) { %>
                            <img src="images/like.png" width="24" height="24" alt="like"/>
                            <img src="images/dislike.png" width="24" height="24" alt="dislike"/>
                            < % } %>-->
                        </npbottom>
                    </npright>
                </npouter>
                <%
                    }
                %>
            </div>
            <div class="bodyright">
                <div class="bodyrighttop card text-bg-info mb-1">                    
                    <jsp:include page="userdet.jsp" flush="true" />
                </div>
                <div class="bodyrightbottom">

                </div>
            </div>
        </cbody>
        <footer>
            <jsp:include page="footer.jsp" flush="true" />
        </footer>
    </outer>
</body>
</html>
