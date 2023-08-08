<%@page import="com.shopping.service.DemoService"%>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@page import="java.util.UUID"%>
<%@page import="java.util.Enumeration"%>
<jsp:useBean id="myPhotoUpload" scope="page" class="com.jspsmart.upload.SmartUpload"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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


                <h3>Add Product Image</h3>
                <%
                    String _sysMsg = "";
                    String _title = "", _imgfile = "", _fileName = "", _filePath = "", _prodid = "";
                    String _fname = "", _totSize = "";
                    int _uploadSuccess = 0;
                    int _fsize = 0;

                    try {
                        myPhotoUpload.initialize(pageContext);
                        myPhotoUpload.setTotalMaxFileSize(5000000);
                        // Upload
                        myPhotoUpload.upload();
                        Enumeration enum1 = myPhotoUpload.getRequest().getParameterNames();

                        while (enum1.hasMoreElements()) {
                            String key = (String) enum1.nextElement();
                            String[] values = myPhotoUpload.getRequest().getParameterValues(key);
                            if (key.equalsIgnoreCase("imgdesc")) {
                                _title = values[0];
                            }
                            if (key.equalsIgnoreCase("prodid")) {
                                _prodid = values[0];
                            }
                        }
                        com.jspsmart.upload.File _file = myPhotoUpload.getFiles().getFile(0);
                        _imgfile = _file.getFileName();
                        _fsize = _file.getSize();
                        double _fSizeMB = 0.0;
                        _fSizeMB = Math.round(_fsize * 100.0 / 1024 / 1024) / 100.0;
                        if (_fSizeMB > 1.0) {
                            _uploadSuccess = 0;
                            _sysMsg = "Image size exceeded required size(1MB)";
                        } else {

                            String fname = UUID.randomUUID().toString();
                            _fileName = fname + "." + _file.getFileExt().toLowerCase();
                            _filePath = session.getServletContext().getRealPath("/") + "WEB-INF/imgs/prodimgs/";
                            try {
                                _file.saveAs(_filePath + _fileName);
                                _uploadSuccess = 1;
                                _sysMsg = "Image successfully uploaded";
                            } catch (Exception e) {
                                _uploadSuccess = 0;
                                _sysMsg = "<br>Error: " + e.toString();
                            }
                            if (_uploadSuccess > 0) {//Image successfully uploaded. Update Database
                                DemoService ds = new DemoService();
                                String retval = ds.addImageRecord(_prodid, _fileName, _title);
                                if (retval.equalsIgnoreCase("T")) {
                                    _uploadSuccess = 2;
                                } else {
                                    _uploadSuccess = 0;
                                }
                            }
                        }
                    } catch (Exception e) {
                        _uploadSuccess = 0;
                        _sysMsg = "<br>Error: " + e.toString();
                    }
                    //System.out.println("_uploadSuccess"+_uploadSuccess);
                    if (_uploadSuccess > 0) {
                %>

                <br>Message: <%=_sysMsg%>
                <% } else {%>
                <br>Message: Unable to upload Image.<br><%=_sysMsg%>
                <% }%>
                <br><a href="addProdImg">Click here</a> to add more images.


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

