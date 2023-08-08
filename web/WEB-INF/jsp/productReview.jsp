<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/main.css"/>
        <link href="bscss/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">        
        <script type="text/javascript">
            function postReview(){
                if(document.getElementById('rating').value==='X'){
                    alert('Select rating from list!');
                    document.getElementById('rating').focus();
                    return false;
                }
                if(document.getElementById('headline').value.length<3){
                    alert('Enter headline!');
                    document.getElementById('headline').focus();
                    return false;
                }
                if(document.getElementById('review').value.length<10){
                    alert('Enter Review(minimum 10 characters)!');
                    document.getElementById('review').focus();
                    return false;
                }
                document.frmreview.submit();
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
                <myheader>Write Product Review</myheader>
                
                <div style="display: flex;flex-direction: row;margin:8px;">
                    <div><img src="${np.getPath()}${np.getFilename()}" height="100" alt="Image"/></div>
                    <div>${np.getProd_desc()}<br>${np.getSeller()}</div>
                </div>
                <form name="frmreview" id="frmreview" method="POST" action="postReview">
                    <input type="hidden" name="hpid" id="hpid" value="${np.getProdid()}">
                    <label class="form-label" for="inputRating">Overall Rating :</label>
                    <div class="form-outline w-25">
                        <select size="1" id="rating" name="rating" class="form-control">
                            <option selected value="X">(Select)</option>
                            <option value="1">1 Star</option>
                            <option value="2">2 Star</option>
                            <option value="3">3 Star</option>
                            <option value="4">4 Star</option>
                            <option value="5">5 Star</option>
                        </select>
                    </div>
                    <label class="form-label" for="inputRating">Add a headline</label>
                    <div class="form-outline w-50">
                        <input type="text" name="headline" id="headline" maxlength="100" class="form-control" required>
                    </div>
                    <label class="form-label" for="inputRating">Add a review</label>
                    <div class="form-outline w-50">
                        <textarea class="form-control" id="review" name="review" rows="3" maxlength="1000"></textarea>
                    </div>
                    <button class="btn btn-primary" type="button" onclick="postReview();" class="form-control" style="margin-top:8px;">Post Review</button>
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
