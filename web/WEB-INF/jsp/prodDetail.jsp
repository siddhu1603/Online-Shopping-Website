<%@page import="com.shopping.model.FullProductDetail"%>
<%@page import="com.shopping.service.DemoService"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
        <style type="text/css">
            #obottom{
                display: flex;
                flex-direction: row;
                flex-grow: 1;
                background-color: white;
                border-radius: 10px;
            }
            #abt{
                /*flex-grow: 1;*/
                width: 50%;
                padding: 4px;
            }
            #spec{
                /*min-width: 30vw;
                max-width: 50%;*/
                width: 50%;
                padding: 4px;
            }
            npouter{
                display: flex;
                flex-direction: row;
                flex-grow: 1;
                min-height: 200px;
                border:1px solid RGBA(13,202,240,var(--bs-bg-opacity,1));
                margin:2px;
                border-radius: 10px;
                background-color: white;
            }
            npleft{
                width: 25vw;
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
                height: 35px;
                display:flex;
                flex-direction: row;
            }
            .bodymain{
                background-color: lightsteelblue;
            }
            .prodmain{
                color: #0070C0;
                font-weight: bold;
                font-size: 16px;
            }
            #tsearch{
                border-radius: 6px;
                line-height: 1.5!important;
            }
        </style>
        <!--
        <script>
            $(document).ready(function () {
                $("#myCarousel").carousel();
            })
        </script>
        -->

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%
            DemoService ds = new DemoService();
            FullProductDetail np = ds.getFullProductDetail(request.getParameter("hprodid"));
        %>
        <div id="outer">
            <npouter>
                <npleft>   
                    <div id="myCarousel" class="carousel slide" data-ride="carousel">
                        <!-- Indicators -->
                        <ol class="carousel-indicators">
                            <%
                                for (int i = 0; i < np.getFilename().size(); i++) {
                            %>
                            <li data-target="#myCarousel" data-slide-to="<%=i%>" <%=i < 1 ? "class=\"active\"" : ""%>></li>

                            <%
                                }
                            %>
                        </ol>

                        <!-- Wrapper for slides -->
                        <div class="carousel-inner">
                            <%
                                for (int i = 0; i < np.getFilename().size(); i++) {
                            %>
                            <div class="<%=i < 1 ? "item active" : "item"%>">
                                <img src="<%=np.getPath().get(i)%><%=np.getFilename().get(i)%>" alt="Los Angeles" style="width:100%;">
                            </div>
                            <%
                                }
                            %>
                        </div>

                        <!-- Left and right controls -->
                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                            <span class="glyphicon glyphicon-chevron-left"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                            <span class="glyphicon glyphicon-chevron-right"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>
                </npleft>
                <npright>
                    <nptop>
                        <div class="prodmain"><%=np.getProd_desc()%></div>
                        <%=np.getMaincat()%>/<%=np.getSubcat()%><br>
                        Rating
                    </nptop>
                    <npdetail>
                        <table border="0" class="table table-sm table-striped" style="width: fit-content;">
                            <thead><tr><th>Variant</th><th>Price</th><th>Discount</th><th>Delivery</th><th></th></tr></thead>
                            <tbody>
                                <%
                                    for (int i = 0; i < np.getVariant().size(); i++) {
                                %>                           
                                <tr>                                
                                    <td><%=np.getVariant().get(i)%></td>
                                    <td><%=np.getUnitpricestring().get(i)%></td>
                                    <td align="center"><%=np.getDiscount().get(i)%></td>
                                    <td align="center"><%=np.getDelivery().get(i)%></td> 
                                    <td>
                                        <% if (session.getAttribute("UID") != null && session.getAttribute("USERTYPE").toString().equals("C")) {%>
                                        <button class="btn btn-primary mb-3" type="button" onclick="addtoCart('<%=np.getProdid()%>',<%=np.getVslno().get(i)%>);">Add to Cart</button>
                                        <button class="btn btn-success mb-3" type="button" onclick="addtoWishlist('<%=np.getProdid()%>',<%=np.getVslno().get(i)%>);">&#10084;</button>
                                        <% } %>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <table border="0">  
                            <tr><td>Seller</td><td>:</td><td style="padding-left: 4px;"><%=np.getSeller()%></td></tr>
                        </table>
                    </npdetail>
                    <npbottom>
                        <% if (session.getAttribute("UID") != null) { %>
                        <% if (session.getAttribute("USERTYPE").toString().equals("C")) { %>
                        <div style="flex-grow: 1;">
                            <div class="col-auto" style="padding-bottom: 8px;">
                                <!--<button class="btn btn-primary mb-3" type="button" onclick="javascript:addtoCart('< %=np.getProdid()% >');">Add to Cart</button>-->
                            </div>
                        </div>
                        <div id="wishlist" style="padding:4px;color:darkred;"></div>
                        <div style="padding:4px;">Rating</div>
                        <div style="padding:4px;"><img src="images/like.png" style="cursor:pointer;" onclick="postlikedislike('<%=request.getParameter("hprodid")%>','L');" width="24" height="24" alt="like"/></div>
                        <div style="padding:4px;"><img src="images/dislike.png" style="cursor:pointer;" onclick="postlikedislike('<%=request.getParameter("hprodid")%>','D');" width="24" height="24" alt="dislike"/></div>
                            <% } %>
                            <% }%>
                    </npbottom>
                </npright>            
            </npouter>
            <div id="obottom">
                <div id="abt">
                    <b>About the Product</b><br>
                    <ul>
                        <%
                            for (int i = 0; i < np.getAslno().size(); i++) {
                        %>
                        <li><%=np.getAbout().get(i)%></li>
                            <%
                                }
                            %>                        
                    </ul>
                </div>
                <div id="spec" style="border-right:1px solid lightgray;">
                    <table border="0" class="table table-sm table-striped" style="width: fit-content;">
                        <thead><tr><th>Specification</th><th>Details</th><th>Unit</th></tr></thead>
                        <tbody>
                            <%
                                for (int i = 0; i < np.getSpec().size(); i++) {
                                    if (np.getSpecval().get(i) != null) {
                            %>                           
                            <tr>                                
                                <td><%=np.getSpec().get(i)%></td>
                                <td><%=np.getSpecval().get(i)%></td>
                                <td><%=np.getUnit().get(i)%></td>                                                                  
                            </tr>
                            <%
                                    }
                                }
                            %>
                        </tbody>
                    </table>
                </div>


            </div>
        </div>
    </body>
</html>
