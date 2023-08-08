<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js"></script>
    </head>
    <body>
        <h4>Admin Dashboard</h4>
        <%
            DemoService ds = new DemoService();
        %>
        <div style="display: flex;flex-direction: row;width: 100%;border:0px solid gray;">
            <div style="display: flex;width: 50%;border:1px solid gray;border-radius:8px;margin:4px;">
                <canvas id="myChart" style="width:100%;max-width:600px"></canvas>
                <script>
                    var xValues = ["0-7 days", "8-14 days", "15-21 days", "21-28 days"];
                    var yValues = [
                    <%
                        List<Integer> cnt = ds.getAdminProductCnt();
                        for (int i = 0; i < cnt.size(); i++) {
                            if (i > 0) {
                                out.print(",");
                            }
                            out.print(cnt.get(i));
                        }
                    %>
                    ];
                    var barColors = [
                        "#b91d47",
                        "#00aba9",
                        "#2b5797",
                        "#e8c3b9"
                    ];

                    new Chart("myChart", {
                        type: "pie",
                        data: {
                            labels: xValues,
                            datasets: [{
                                    backgroundColor: barColors,
                                    data: yValues
                                }]
                        },
                        options: {
                            title: {
                                display: true,
                                text: "Product Registered during last month"
                            }
                        }
                    });
                </script>
            </div>
            <div style="display: flex;width: 50%;border:1px solid gray;border-radius:8px;margin:4px;">
                <canvas id="myChart1" style="width:100%;max-width:600px"></canvas>
                <script>
                    <%
                        List<String> lst = ds.getAdminOrderAmount();
                        String xval = "[";
                        String yval = "[";
                        String lstval, lstval1, lstval2;
                        for (int i = 0; i < lst.size(); i++) {
                            lstval = lst.get(i);
                            lstval1 = lstval.substring(0, lstval.indexOf('#'));
                            lstval2 = lstval.substring(lstval.indexOf('#') + 1);
                            if (i > 0) {
                                xval = xval + ",";
                                yval = yval + ",";
                            }
                            xval = xval + "\"" + lstval1 + "\"";
                            yval = yval + lstval2;
                        }
                        xval = xval + "]";
                        yval = yval + "]";
                        out.println("var xValues =" + xval + ";");
                        out.println("var yValues =" + yval + ";");
                    %>
                    var barColors = [
                        "#b91d47",
                        "#00aba9",
                        "#2b5797",
                        "#e8c3b9"
                    ];

                    new Chart("myChart1", {
                        type: "pie",
                        data: {
                            labels: xValues,
                            datasets: [{
                                    backgroundColor: barColors,
                                    data: yValues
                                }]
                        },
                        options: {
                            title: {
                                display: true,
                                text: "Selling during last month"
                            }
                        }
                    });
                </script>
            </div>
        </div>
        <div style="display: flex;width: 50%;border:1px solid gray;border-radius:8px;margin:4px;">
            <canvas id="monthwiseamt" style="width:100%;max-width:600px"></canvas>
            <script>
                <%
                    lst = ds.getAdminMonthwiseOrderAmount();
                    xval = "[";
                    yval = "[";
                    for (int i = 0; i < lst.size(); i++) {
                        lstval = lst.get(i);
                        lstval1 = lstval.substring(0, lstval.indexOf('#'));
                        lstval2 = lstval.substring(lstval.indexOf('#') + 1);
                        if (i > 0) {
                            xval = xval + ",";
                            yval = yval + ",";
                        }
                        xval = xval + "\"" + lstval1 + "\"";
                        yval = yval + lstval2;
                    }
                    xval = xval + "]";
                    yval = yval + "]";
                    out.println("var xValues =" + xval + ";");
                    out.println("var yValues =" + yval + ";");
                %>
                var barColors = ["red", "green","blue","orange","brown","purple","olive","gray","Maroon","Mustard","Lavender","Mauve"];

                new Chart("monthwiseamt", {
                    type: "bar",
                    data: {
                        labels: xValues,
                        datasets: [{
                                backgroundColor: barColors,
                                data: yValues
                            }]
                    },
                    options: {
                        legend: {display: false},
                        title: {
                            display: true,
                            text: "Selling during last Year"
                        }
                    }
                });
            </script>
        </div>
    </body>
</html>