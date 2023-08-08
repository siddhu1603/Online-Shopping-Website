<%
    if (session.getAttribute("USERTYPE") == null){
    } else if (session.getAttribute("USERTYPE").toString().equalsIgnoreCase("C")) {
%>
<b>CUSTOMER</b>
<ul>
    <li><a href="register">Registration</a></li>
    <li><a href="addAddress">Add Address</a></li>
    <li><a href="vwProfile">View Profile</a></li>
    <li><a href="addWallet">Wallet - Add/Delete </a></li>
    <li><a href="addMoney">Wallet - Add money </a></li>
</ul>
<%
} else if (session.getAttribute("USERTYPE").toString().equalsIgnoreCase("A")) {
%>
<b>Administration</b>
<ul>
    <li><a href="vwProdReview">Product Review</a></li>
    <li><a href="underDevelopment">Add Item Category</a></li>
    <li><a href="underDevelopment">Edit Item Category</a></li>
    <li><a href="underDevelopment">Delete Item Category</a></li>
</ul>
<%
} else {
%>
<b>Seller</b>
<ul>
    <b>Product</b>
    <li><a href="addProdVar">Add Product</a></li>
    <li><a href="underDevelopment">Edit Product</a></li>
    <li><a href="underDevelopment">Delete Product</a></li>
    <b>Product-Specification</b>
    <li><a href="addProdSpec">Add Specification</a></li>
    <li><a href="underDevelopment">Edit Specification</a></li>
    <b>Product-Images</b>
    <li><a href="addProdImg">Add Image</a></li>
    <b>Product-About</b>
    <li><a href="abtProd">About Product</a></li>
</ul>
<%
    }
%>
<a href="home">Home</a>

