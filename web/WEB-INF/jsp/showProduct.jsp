<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        

        <style type="text/css">
            npleft,npright{
                padding: 4px;
            }
            table td{
                padding: 4px;
            }
        </style>
        <script type="text/javascript" src="js/jquery-3.7.0.min.js"></script>
        <script type="text/javascript">
            function addtoCart(pid, sl) {
                //alert(pid + '...' + sl);
                //$('#cart').html(1);
                $.ajax({
                    url: 'addProdtoCart',
                    data: {
                        prodid: pid,
                        slno: sl
                    },
                    success: function (responseText) {
                        $('#cart').html(responseText);
                    }
                });
            }
            function addtoWishlist(pid, sl){
                $.ajax({
                    url: 'addProdtoWishlist',
                    data: {
                        prodid: pid,
                        slno: sl
                    },
                    success: function (responseText) {
                        $('#wishlist').html(responseText);
                        const myInterval1 = setInterval(myTimer1, 5000);
                    }
                });
            }
            function myTimer1() {
                $('#wishlist').html("");
                clearInterval(myInterval1);
            }
            function postlikedislike(pid, ltype) {
                $.ajax({
                    url: 'postLike',
                    data: {
                        prodid: pid,
                        liketype: ltype
                    },
                    success: function (responseText) {
                        $('#wishlist').html(responseText);
                        const myInterval = setInterval(myTimer, 5000);
                    }
                });
            }
            function myTimer() {
                $('#wishlist').html("");
                clearInterval(myInterval);
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
                <jsp:include page="prodDetail.jsp" flush="true"/>          
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

