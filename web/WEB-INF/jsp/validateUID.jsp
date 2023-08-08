
<%@page import="com.shopping.service.DemoService"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%
    String sysmsg = "T";
    String _param = request.getParameter("MYPARAM");
    if (_param.equalsIgnoreCase("USERID")) {
        String _user = request.getParameter("UID");
        if (_user.length() < 8) {
            sysmsg = "FUser ID can not be less than 8 characters.";
        } else if (_user.contains(" ")) {
            sysmsg = "FUser ID can not contain space.";
        } else {
            DemoService ds = new DemoService();
            String _exist = ds.checkUID(_user);
            if (_exist.equalsIgnoreCase("T")) {
                sysmsg = "FUser ID already exist. Try for a new user id.";
            }
        }

    } else if (_param.equalsIgnoreCase("PWD")) {
        String _pwd = request.getParameter("PWD");
        if (_pwd.length() < 8) {
            sysmsg = "FPassword can not be less than 8 characters.";
        } else if (_pwd.contains(" ")) {
            sysmsg = "FPassword can not contain space.";
        }
    } else if (_param.equalsIgnoreCase("USERNAME")) {
        String _name = request.getParameter("UNAME");
        if (_name.length() < 6) {
            sysmsg = "FUser Name can not be less than 6 characters.";
        }
    } else if (_param.equalsIgnoreCase("EMAIL")) {

        String _email = request.getParameter("email");
        //Regular Expression   
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                + "[a-zA-Z0-9_+&*-]+)*@"
                + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        //Compile regular expression to get the pattern  
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(_email);
        //System.out.println(_email + " : " + matcher.matches() + "\n");
        if (!matcher.matches()) {
            sysmsg = "FInvalid email address.";
        }

    } else if (_param.equalsIgnoreCase("MOBILE")) {

        String _mob = request.getParameter("mobile");
        //System.out.print("MOBILE"+_mob);
        if (_mob.length() < 10 || _mob.length() > 10) {
            sysmsg = "FMobile number must be 10 characters.";
        } else {
            try {
                double d = Double.parseDouble(_mob);
            } catch (NumberFormatException nfe) {
                sysmsg = "FMobile number must be 10 characters(numeric).";
            }
        }
    } else if (_param.equalsIgnoreCase("PINCODE")) {

        String _val = request.getParameter("pincode");
        try {
            double d = Double.parseDouble(_val);
        } catch (NumberFormatException nfe) {
            sysmsg = "FPincode number must be 6 characters(numeric).";
        }
    } else if (_param.equalsIgnoreCase("CATID")) {

        String _val = request.getParameter("catid");
        if (_val.equals("X")) {
            sysmsg = "FFirst select category from list.";
        } 
    } else if (_param.equalsIgnoreCase("QTY")) {
        String _val = request.getParameter("qty");
        try {
            double d = Double.parseDouble(_val);
        } catch (NumberFormatException nfe) {
            sysmsg = "FQuantity must be numeric.";
        }
    } else if (_param.equalsIgnoreCase("UNITPRICE")) {
        String _val = request.getParameter("UP");
        try {
            double d = Double.parseDouble(_val);
        } catch (NumberFormatException nfe) {
            sysmsg = "FUnit price must be numeric.";
        }
    }  else if (_param.equalsIgnoreCase("DISCOUNT")) {
        String _val = request.getParameter("discount");
        try {
            double d = Double.parseDouble(_val);
        } catch (NumberFormatException nfe) {
            sysmsg = "FDiscount must be numeric.";
        }
    } else if (_param.equalsIgnoreCase("SPECPRODID")) {
        String _val = request.getParameter("prodid");
        if (_val.equals("X")) {
            sysmsg = "FFirst select product from list.";
        }
    }
    response.setContentType("text/plain");
    response.getWriter().write(sysmsg.trim());
%>

