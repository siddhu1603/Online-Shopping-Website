<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%
    if (!session.getAttribute("USERTYPE").toString().equals("S")) {
%>
<jsp:forward page="errorMsg.jsp">
    <jsp:param name="sysmsg" value="You must login as a seller first."></jsp:param>
</jsp:forward>
<%
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
        <script type="text/javascript">
            function listchangerequest(val) {
                if (val !== 'X') {
                    window.location = val;
                }
            }
        </script>
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#catid').focus();
                $('#catid').blur(function () {
                    $('#sysmsg').text("");
                    $.ajax({
                        url: 'validateUID',
                        data: {
                            catid: $('#catid').val(),
                            MYPARAM: "CATID"
                        },
                        success: function (responseText) {
                            if (responseText.substring(0, 1) === "F") {
                                $('#hcat').val('F');
                                $('#sysmsgCAT').text(responseText.substring(1));
                            } else {
                                $('#sysmsgCAT').text("");
                                $('#hcat').val('T');
                            }
                        }
                    });
                });

                $('#frmprod').on('submit', function (e) {
                    if (!check()) {
                        //alert('validation failed');
                        e.preventDefault();
                        return false;
                    }
                    if ($('#hcat').val() === 'F') {
                        $('#catid').focus();
                        e.preventDefault();
                        return false;
                    }
                });
            });
        </script>
        <script type="text/javascript">
            function addRows() {
                var table = document.getElementById('pvartbl');
                var rowCount = table.rows.length;
                var cellCount = table.rows[0].cells.length;
                var row = table.insertRow(rowCount);
                for (var i = 0; i <= cellCount; i++) {
                    var cell = 'cell' + i;
                    cell = row.insertCell(i);
                    var copycel = document.getElementById('col' + i).innerHTML;
                    cell.innerHTML = copycel;
                }
            }
            function deleteRows() {
                var table = document.getElementById('pvartbl');
                var rowCount = table.rows.length;
                if (rowCount > '2') {
                    var row = table.deleteRow(rowCount - 1);
                    rowCount--;
                } else {
                    alert('There should be atleast one row');
                }
            }
            function check() {
                var table = document.getElementById('pvartbl');
                var rowCount = table.rows.length;
                var qty = document.getElementsByName("qty");
                var unitprice = document.getElementsByName("unitprice");
                var discount = document.getElementsByName("discount");
                var delivery = document.getElementsByName("delivery");
                var regex = /^(0\.(?!00)|(?!0)\d+\.)\d\d$/;
                var valid = true;
                for (var i = 0; i < rowCount - 1; i++) {
                    if (qty[i].value.match(/^[0-9]+$/) === null) {
                        valid = false;
                        qty[i].focus();
                        $('#sysmsg').text('Quantity must be numeric.');
                        break;
                    }

                    if ((!regex.test(unitprice[i].value)) && (unitprice[i].value.match(/^[0-9]+$/) === null)) {
                        valid = false;
                        unitprice[i].focus();
                        $('#sysmsg').text('Invalid unit price.');
                        break;
                    }
                    if (discount[i].value.match(/^[0-9]+$/) === null) {
                        valid = false;
                        discount[i].focus();
                        $('#sysmsg').text('Discount must be numeric.');
                        break;
                    }
                    if (delivery[i].value.match(/^[0-9]+$/) === null) {
                        valid = false;
                        delivery[i].focus();
                        $('#sysmsg').text('Delivery period must be numeric.');
                        break;
                    }
                }
                return valid;
            }
        </script>
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
                
                
                <%
                        String msg = session.getAttribute("SYSMSG") == null ? "" : session.getAttribute("SYSMSG").toString();
                        session.removeAttribute("SYSMSG");
                    %>
                    <myheader>Register Product</myheader>
                    <form name="frmprod" id="frmprod" method="POST" action="submitProdVar">
                        <div class="mb-1">
                            <label class="form-label" for="inputCategory">Category</label>
                            <div class="divfrm">
                                <div class="form-outline w-50">
                                    <select size="1" id="catid" name="catid" class="form-control">
                                        <option selected value="X">(Select)</option>
                                        <c:forEach items="${cat}" var="catg">
                                            <option value="${catg.getCatid()}">${catg.getMaincat()}-${catg.getSubcat()}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div id="sysmsgCAT" style="padding:4px 8px;"></div><input type="hidden" id="hcat" name="hcat">
                            </div>
                        </div>                

                        <div class="mb-1">
                            <label for="inputDesc" class="form-label">Description</label>
                            <div class="divfrm" >
                                <div class="form-outline w-50">
                                    <input type="text" class="form-control" maxlength="100" id="prod_desc" placeholder="Product Description" name="prod_desc" required>
                                </div>
                            </div>                
                        </div>
                        <table id="pvartbl">
                            <tr><th>Variant</th><th>Quantity</th><th>Price</th><th>Discount</th><th>Delivery(days)</th></tr>
                            <tr>
                                <td id="col0"><input type="text" class="form-control" id="variant" maxlength="30" placeholder="Variant" name="variant" required></td>
                                <td id="col1"><input type="text" class="form-control" id="qty" maxlength="5" placeholder="Quantity" name="qty" required></td>
                                <td id="col2"><input type="text" class="form-control" id="unitprice" maxlength="10" placeholder="Unit Price" name="unitprice" required></td>
                                <td id="col3"><input type="text" class="form-control" id="discount" maxlength="2" placeholder="discount" name="discount" value="0" required></td>
                                <td id="col4"><input type="text" class="form-control" id="delivery" maxlength="2" placeholder="delivery period" name="delivery" required></td>
                            </tr>
                        </table>
                        <div class="col-auto" style="padding-top: 8px;">
                            <input type="button" class="btn btn-secondary mb-3" value="Add Row" onclick="addRows()" /> <input type="button" class="btn btn-secondary mb-3" value="Delete Row" onclick="deleteRows()" /> <button id="btn" name="btn" type="submit" class="btn btn-primary mb-3">Add Product</button>
                        </div>
                        <div id="sysmsg"><%=(msg != null) ? "<h6>" + msg + "</h6>" : ""%></div>
                    </form>
                
                
                
                
                
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
