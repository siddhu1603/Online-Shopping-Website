<%@page import="com.shopping.service.DemoService"%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script type="text/javascript">
            function listchangerequest(val) {
                if (val !== 'X') {
                    window.location = val;
                }
            }
            function searchSite(stext) {
                if (stext.length < 3) {
                    alert('Enter atleast 3 character to search!');
                    document.getElementById("tsearch").focus();
                    return false;
                } else {
                    document.frmsearch.submit();
                }
            }
        </script>
    </head>
    <body>
        <div style="display: flex;flex-direction: row;">
            <div>
                <a href="home"><img src="images/logo.png" height="80" alt="Online Shopping"/></a>
            </div>
            <div style="padding:30px 8px 0px 8px;">
                <span style="display: inline-block;line-height: 18px;">
                    <span style="color:lightgoldenrodyellow;font-size:24px;font-weight: bold;">Online Shopping Site</span><br>
                    <span style="color:ghostwhite;font-size:12px;">website for online shopping n selling</span>
                </span>
            </div>
            <div style="flex-grow: 1;">

            </div>
            <div style="padding:8px;min-width:200px;line-height: 18px;">
                <select onchange="listchangerequest(this.options[this.selectedIndex].value);">
                    <% if (session.getAttribute("UID") == null) { %>
                    <option value="X">Guest</option>
                    <option value="register">Register</option>
                    <option value="login">Sign In</option>
                    <% } else if (session.getAttribute("USERTYPE").equals("C")) {%>
                    <option value="X"><%=session.getAttribute("USERNAME")%></option>
                    <option value="vwProfile">My Profile</option>
                    <option value="myOrders">My Orders</option>
                    <option value="addAddress">my Address</option>
                    <option value="wishList">My Wishlist</option>
                    <option value="addWallet">My Wallet</option>
                    <option value="logout">Sign Out</option>
                    <% } else if (session.getAttribute("USERTYPE").equals("S")) {%>
                    <option value="X"><%=session.getAttribute("USERNAME")%></option>
                    <option value="vwProfile">My Profile</option>
                    <option value="addProdVar">Add Product</option>
                    <option value="addProdSpec">Product Specification</option>
                    <option value="addProdImg">Product Image</option>
                    <option value="abtProd">About Product</option>
                    <option value="addWallet">My Wallet</option>
                    <option value="logout">Sign Out</option>
                    <% } else {%>
                    <option value="X"><%=session.getAttribute("USERNAME")%></option>
                    <option value="vwProfile">My Profile</option>
                    <option value="logout">Sign Out</option>
                    <% }%>
                </select>
            </div>
            <%
                String cnt;
                if (session.getAttribute("UID") == null) {
                    cnt = "0";
                } else {
                    DemoService ds = new DemoService();
                    cnt = ds.getCartCnt(session);
                }
            %>
            <div style="padding:8px;min-width:100px;line-height: 18px;background: url('images/cart.png') no-repeat local;">
                <span style="color:orange;font-weight: bold;min-width:100px;display:flex;padding-left: 30px;">
                    <div id="cart" style="font-size: 18px;">
                        <% if (cnt.equals("0")) {%>
                        <%=cnt%>
                        <% } else {%>
                        <a href="showCart" style="color:orange;text-decoration: none;"><%=cnt%></a>
                        <% }%>
                    </div>
                </span>
            </div>
        </div>
    </body>
</html>