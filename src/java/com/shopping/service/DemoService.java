/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.shopping.service;

import com.shopping.db.ConnectDB;
import com.shopping.model.AboutProduct;
import com.shopping.model.Address;
import com.shopping.model.CartProduct;
import com.shopping.model.Category;
import com.shopping.model.Customer;
import com.shopping.model.FullProductDetail;
import com.shopping.model.NewProduct;
import com.shopping.model.OrderDetails;
import com.shopping.model.Product;
import com.shopping.model.ProductImages;
import com.shopping.model.ProductReview;
import com.shopping.model.ProductVariant;
import com.shopping.model.Status;
import com.shopping.model.Template;
import com.shopping.model.User;
import com.shopping.model.Wallet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class DemoService {

    public static String generateCaptchaTextMethod(int captchaLength) {

        String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder captchaStrBuffer = new StringBuilder();
        java.util.Random rnd = new java.util.Random();
        // build a random captchaLength chars salt
        while (captchaStrBuffer.length() < captchaLength) {

            int index = (int) (rnd.nextFloat() * saltChars.length());
            captchaStrBuffer.append(saltChars.substring(index, index + 1));
        }
        return captchaStrBuffer.toString();
    }

    public String generateCaptcha(String capval, HttpSession session, HttpServletRequest request) throws IOException {
        int width = 100;
        int height = 30;
        Color bg = new Color(255, 255, 255);
        Color fg = new Color(0, 100, 0);
        Font font = new Font("Arial", Font.BOLD, 20);
        BufferedImage cpimg = new BufferedImage(width, height, BufferedImage.OPAQUE);
        Graphics g = cpimg.createGraphics();
        g.setFont(font);
        g.setColor(bg);
        g.fillRect(0, 0, width, height);
        g.setColor(fg);
        g.drawString(capval, 12, 22);
        //if(session.isNew()){
        //System.out.print("inside gen cap-session is new.");
        session = request.getSession(true);
        //}
        //System.out.print("session id="+session.getId());
        //session.setAttribute("CAPTCHA", capval);

        //System.out.println(generatedString);
        String path = session.getServletContext().getRealPath("/");
        session.setAttribute("cappath", "images/captcha/");
        String fname = getAlphaNumericString(6);
//        System.out.println(path);
        ImageIO.write(cpimg, "jpg", new File(path + "WEB-INF/imgs/captcha/" + fname + ".jpg"));

        //System.out.println(fname);
        return fname;
    }

    //function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public String checkUID(String uid) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select count(*) from USERMASTER where userid=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, uid);
                ResultSet rs = ps.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    msg = "T";
                }
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            //if (db != null) {
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
            //}
        }
        return msg;
    }

    public String registerUser(User user) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select '" + user.getCusttype() + "'||lpad(to_char(nvl(max(to_number(substr(custid,2,4))),0)+1),4,'0') from usermaster where CUSTTYPE='" + user.getCusttype() + "'";
                db.createStatement();
                Statement st = db.getStmt();
                ResultSet rs;
                rs = st.executeQuery(sql);
                rs.next();
                String custid = rs.getString(1);
                rs.close();
                st.close();
                sql = "insert into usermaster(CUSTID,USER_NAME,MOBILE,DOB,PWD,USERID,EMAIL,GENDER,CUSTTYPE) values (?,?,?,?,?,?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, custid);
                ps.setString(2, user.getUsername());
                ps.setLong(3, Long.parseLong(user.getMobile()));
                ps.setString(4, user.getDob());
                ps.setString(5, user.getPwd());
                ps.setString(6, user.getUserid());
                ps.setString(7, user.getEmail());
                ps.setString(8, user.getGender());
                ps.setString(9, user.getCusttype());
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "User successfully registered";
                } else {
                    msg = "Unable to register user";
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return msg;
    }

    public User authenticate(String uid, String pwd, String type) {
        User user = new User();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        ResultSet rs;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select CUSTID, USER_NAME,MOBILE,to_char(DOB,'DD-MM-YYYY') DOB,EMAIL,gender,CUSTTYPE from USERMASTER where userid=? and pwd=? and CUSTTYPE=? and nvl(ACTIVE,'N')='Y' ";
                ps = db.getPrepStmt(sql);
                ps.setString(1, uid);
                ps.setString(2, pwd);
                ps.setString(3, type);
                rs = ps.executeQuery();
                if (rs.next()) {
                    user.setIsUserValid(true);
                    user.setUsername(rs.getString("USER_NAME"));
                    user.setCustid(rs.getString("CUSTID"));
                    user.setMobile(rs.getString("MOBILE"));
                    user.setDob(rs.getString("DOB"));
                    user.setEmail(rs.getString("EMAIL"));
                    user.setUserid(uid);
                    user.setGender(rs.getString("gender"));
                    user.setCusttype(rs.getString("CUSTTYPE"));
                } else {
                    user.setIsUserValid(false);
                    user.setUsername("Not a valid user");
                }
                rs.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return user;
    }

    //GET EXISTING ADDRESS LIST
    public List<Address> getAddrList(HttpSession session) {
        ArrayList<Address> addrList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select CUSTID,SLNO,ALIAS_NAME,ADDR1,ADDR2,ADDR3,CITY,STATE,PINCODE,to_char(CRTDT,'DD-Mon-YYYY') CRTDT from cust_addr where custid=? order by SLNO";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Address lvaddr = new Address();
                    lvaddr.setSlno(rs.getInt("SLNO"));
                    lvaddr.setAlias_name(rs.getString("ALIAS_NAME"));
                    lvaddr.setAddr1(rs.getString("ADDR1"));
                    lvaddr.setAddr2(rs.getString("ADDR2"));
                    lvaddr.setAddr3(rs.getString("ADDR3"));
                    lvaddr.setCity(rs.getString("CITY"));
                    lvaddr.setState(rs.getString("STATE"));
                    lvaddr.setPincode(rs.getString("PINCODE"));
                    lvaddr.setCrtdt(rs.getString("CRTDT"));
                    addrList.add(lvaddr);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return addrList;
    }

    //ADD ADDRESS
    public String addAddr(Address addr, HttpSession session) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select nvl(max(slno),0)+1 from cust_addr where custid=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                int _slno = rs.getInt(1);
                rs.close();
                ps.close();
                sql = "insert into cust_addr(CUSTID,SLNO,ALIAS_NAME,ADDR1,ADDR2,ADDR3,CITY,STATE,PINCODE) values(?,?,?,?,?,?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ps.setInt(2, _slno);
                ps.setString(3, addr.getAlias_name());
                ps.setString(4, addr.getAddr1());
                ps.setString(5, addr.getAddr2());
                ps.setString(6, addr.getAddr3());
                ps.setString(7, addr.getCity());
                ps.setString(8, addr.getState());
                ps.setString(9, addr.getPincode());
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "Address successfully added";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            msg = ex.toString();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //GET EXISTING WALLET LIST
    public List<Wallet> getWList(HttpSession session) {
        ArrayList<Wallet> wList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select CUSTID,SLNO,WALLET_NAME,AMOUNT,to_char(AMOUNT,'9,99,99,999.99') AMOUNTSTRING,to_char(CRTDT,'DD-Mon-YYYY') CRTDT,nvl(to_char(LASTUSEDT,'DD-Mon-YYYY'),'-') LASTUSEDT from cust_wallet where custid=? order by SLNO";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Wallet lvw = new Wallet();
                    lvw.setSlno(rs.getInt("SLNO"));
                    lvw.setWallet_name(rs.getString("WALLET_NAME"));
                    lvw.setAmount(rs.getDouble("AMOUNT"));
                    lvw.setCrtdt(rs.getString("CRTDT"));
                    lvw.setLastusedt(rs.getString("LASTUSEDT"));
                    lvw.setAmountstring(rs.getString("AMOUNTSTRING"));
                    wList.add(lvw);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return wList;
    }

    //ADD WALLET
    public String addWallet(Wallet w, HttpSession session) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select nvl(max(slno),0)+1 from cust_wallet where custid=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                int _slno = rs.getInt(1);
                rs.close();
                ps.close();
                sql = "insert into cust_wallet(CUSTID,SLNO,WALLET_NAME,AMOUNT) values(?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ps.setInt(2, _slno);
                ps.setString(3, w.getWallet_name());
                ps.setDouble(4, w.getAmount());

                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "Wallet successfully added";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            msg = ex.toString();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //UPDATE USER PROFILE
    public String updateUser(Customer cust, HttpSession session) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "update USERMASTER set USER_NAME=?, MOBILE=?, DOB=?, EMAIL=?, GENDER=?, updtdt=sysdate where custid=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, cust.getCust_name());
                ps.setLong(2, Long.parseLong(cust.getMobile()));
                ps.setString(3, cust.getDob());
                ps.setString(4, cust.getEmail());
                ps.setString(5, cust.getGender());
                ps.setString(6, session.getAttribute("CUSTID").toString());
                //System.out.println(cust.getCust_name()+".."+cust.getMobile()+".."+cust.getDob()+"..."+cust.getEmail()+"..."+cust.getGender()+"..."+session.getAttribute("CUSTID").toString());

                int i = ps.executeUpdate();
                if (i > 0) {
                    if (!session.getAttribute("USERNAME").equals(cust.getCust_name())) {
                        session.setAttribute("USERNAME", cust.getCust_name());
                    }
                    if (!session.getAttribute("USEREMAIL").equals(cust.getEmail())) {
                        session.setAttribute("USEREMAIL", cust.getEmail());
                    }
                    if (!session.getAttribute("USERMOBILE").equals(cust.getMobile())) {
                        session.setAttribute("USERMOBILE", cust.getMobile());
                    }
                    if (!session.getAttribute("USERDOB").equals(cust.getDob())) {
                        session.setAttribute("USERDOB", cust.getDob());
                    }
                    if (!session.getAttribute("USERGENDER").equals(cust.getGender())) {
                        session.setAttribute("USERGENDER", cust.getGender());
                    }
                    msg = "profile successfully updated";
                } else {
                    msg = "Unable to update profile";
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return msg;
    }

    public ArrayList<Category> getCategory() {
        ArrayList<Category> _cat = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            if (db.dbConnect().equals("OK")) {
                String sql = "select CATID, MAINCAT, SUBCAT from CATEGORY where nvl(flag,'N')='Y' order by MAINCAT, SUBCAT";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Category lvcat = new Category();
                    lvcat.setCatid(rs.getInt("CATID"));
                    lvcat.setMaincat(rs.getString("MAINCAT"));
                    lvcat.setSubcat(rs.getString("SUBCAT"));
                    _cat.add(lvcat);
                }
                rs.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return _cat;
    }

    //ADD PRODUCT WITH VARIANT
    public String addProductVariant(ProductVariant p, HttpSession session) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            db.setAutoCommit(false);
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select 'P'||lpad(to_char(nvl(max(to_number(substr(prodid,2,5))),0)+1),5,'0') from products";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String _prodid = rs.getString(1);
                rs.close();
                ps.close();

                sql = "insert into products(PRODID,CATID,PROD_DESC,CUSTID) values(?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, _prodid);
                ps.setInt(2, p.getCatid());
                ps.setString(3, p.getProd_desc());
                ps.setString(4, p.getCustid());

                int i = ps.executeUpdate();
                ps.close();
                if (i > 0) {
                    //ADD VARIANTS
                    //GET SLNO
                    sql = "select nvl(max(slno),0)+1 from PROD_VARIANT where PRODID=?";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, _prodid);
                    rs = ps.executeQuery();
                    rs.next();
                    int _slno = rs.getInt(1);
                    rs.close();
                    ps.close();
                    //CREATE SQL FOR POPULATING VARIANTS
                    sql = "insert into PROD_VARIANT(PRODID,SLNO,VARIANT,QTY,UNITPRICE,DISCOUNT,DELIVERY) values(?,?,?,?,?,?,?)";
                    ps = db.getPrepStmt(sql);
                    List<String> _var = p.getVariant();

                    List<Integer> pqty = p.getQty();
                    List<Double> pup = p.getUnitprice();
                    List<Integer> pdisc = p.getDiscount();
                    List<Integer> pdelv = p.getDelivery();

                    int _size = p.getVariant().size();

                    for (int x = 0; x < _size; x++) {
                        ps.setString(1, _prodid);
                        ps.setInt(2, _slno++);
                        ps.setString(3, _var.get(x));
                        ps.setInt(4, pqty.get(x));
                        ps.setDouble(5, pup.get(x));
                        ps.setInt(6, pdisc.get(x));
                        ps.setInt(7, pdelv.get(x));
                        ps.addBatch();
                    }
                    int[] ii = ps.executeBatch();
                    ps.close();
                    db.commit();
                    msg = "Product successfully added";
                } else {
                    msg = "Unable to register Product";
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex1);
            }
            msg = "Error:Unable to add Product. <br>" + ex.toString();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    public ArrayList<Product> getProductList(HttpSession session) {
        ArrayList<Product> _prod = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            if (db.dbConnect().equals("OK")) {
                String sql = "select PRODID, PROD_DESC, to_char(CRTDT,'DD-Mon-YY') CRTDT from PRODUCTS where CUSTID=? and nvl(flag,'N')='Y' and PRODID not in (select distinct PRODID from PROD_SPEC) order by 1";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product lvprod = new Product();
                    lvprod.setProdid(rs.getString("PRODID"));
                    lvprod.setProd_desc(rs.getString("PROD_DESC"));
                    lvprod.setCrtdt(rs.getString("CRTDT"));
                    _prod.add(lvprod);
                }
                rs.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return _prod;
    }

    //GET TEMPLATE LIST FOR THE PRODUCT
    public List<Template> getTemplate(String prodid) {
        ArrayList<Template> specList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select SLNO,SPEC,nvl(UNIT,' ') UNIT from TEMPLATE a, PRODUCTS b where a.catid=b.catid and b.prodid=? order by slno";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    Template lvspec = new Template();
                    lvspec.setSlno(rs.getInt("SLNO"));
                    lvspec.setSpec(rs.getString("SPEC"));
                    lvspec.setUnit(rs.getString("UNIT"));
                    specList.add(lvspec);
                }
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return specList;
    }

    //ADD SPEC
    public String insSpec(ArrayList<Template> t) {
        String msg = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into PROD_SPEC(PRODID,SLNO,SPEC,SPECVAL,UNIT) values(?,?,?,?,?)";
                ps = db.getPrepStmt(sql);
                for (int i = 0; i < t.size(); i++) {
                    ps.setString(1, t.get(i).getProdid());
                    ps.setInt(2, t.get(i).getSlno());
                    ps.setString(3, t.get(i).getSpec());
                    ps.setString(4, t.get(i).getSpecval());
                    ps.setString(5, t.get(i).getUnit());
                    ps.addBatch();
                }
                ps.executeBatch();
                msg = "Product successfully added";
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            msg = "Error: Unable to add Specification.<br>" + ex.toString();
            System.out.println("Error:" + ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //getProductImgList-GET PRODUCT LIST FOR UPLOADING IMAGES
    public ArrayList<Product> getProductImgList(HttpSession session) {
        ArrayList<Product> _prod = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            if (db.dbConnect().equals("OK")) {
                String sql = "select PRODID, PROD_DESC, to_char(CRTDT,'DD-Mon-YY') CRTDT from PRODUCTS where CUSTID=? and nvl(flag,'N')='Y' order by 1";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Product lvprod = new Product();
                    lvprod.setProdid(rs.getString("PRODID"));
                    lvprod.setProd_desc(rs.getString("PROD_DESC"));
                    lvprod.setCrtdt(rs.getString("CRTDT"));
                    _prod.add(lvprod);
                }
                rs.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return _prod;
    }

    //getProdImages-GET IMAGES FOR PRODUCT
    public List<ProductImages> getProdImages(String prodid) {
        ArrayList<ProductImages> imgList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select SLNO,FILENAME,PATH,IMGDESC,to_char(CRTDT,'DD-Mon-YYYY') CRTDT from PROD_IMAGE where prodid=? and nvl(flag,'N')='Y' order by slno";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    ProductImages lvimage = new ProductImages();
                    lvimage.setProdid(prodid);
                    lvimage.setSlno(rs.getInt("SLNO"));
                    lvimage.setFilename(rs.getString("FILENAME"));
                    lvimage.setPath(rs.getString("PATH"));
                    lvimage.setImgdesc(rs.getString("IMGDESC"));
                    lvimage.setCrtdt(rs.getString("CRTDT"));
                    imgList.add(lvimage);
                }
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return imgList;
    }

    //addImageRecord
    public String addImageRecord(String prodid, String fname, String title) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into PROD_IMAGE(PRODID, SLNO, FILENAME, PATH, IMGDESC) select ?,nvl(max(slno),0)+1,?,?,? from PROD_IMAGE where PRODID=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ps.setString(2, fname);
                ps.setString(3, "images/prodimgs/");
                ps.setString(4, title);
                ps.setString(5, prodid);
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "T";
                } else {
                    msg = "F";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //getProdAbout
    public List<AboutProduct> getProdAbout(String prodid) {
        ArrayList<AboutProduct> aList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select SLNO,ABOUT,to_char(CRTDT,'DD-Mon-YYYY') CRTDT from ABOUTPROD where prodid=? order by slno";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    AboutProduct lvabt = new AboutProduct();
                    lvabt.setProdid(prodid);
                    lvabt.setSlno(rs.getInt("SLNO"));
                    lvabt.setAbout(rs.getString("ABOUT"));
                    lvabt.setCrtdt(rs.getString("CRTDT"));
                    aList.add(lvabt);
                }
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return aList;
    }

    //addProdaobout
    public String addProdaobout(String prodid, String abt) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into ABOUTPROD(PRODID, SLNO, ABOUT) select ?,nvl(max(slno),0)+1,? from ABOUTPROD where PRODID=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ps.setString(2, abt);
                ps.setString(3, prodid);
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "T";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //GET LIST OF NEW PRODUCT
    //GET EXISTING SPEC LIST
    public List<NewProduct> getNewProductList() {
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT, B.UNITPRICE,to_char(B.UNITPRICE,'9,99,99,999.99') UPSTRING, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME\n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "B.SLNO=(select min(slno) from prod_variant where prodid=A.prodid) and\n"
                        + "a.flag='Y'\n"
                        + "order by crtdt desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    lvnp.setUnitpricestring(rs.getString("UPSTRING"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //getFullProductDetail
    public FullProductDetail getFullProductDetail(String pid) {
        FullProductDetail p = new FullProductDetail();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        boolean exist = false;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') CRTDT, A.catid,D.maincat,D.subcat,C.USER_NAME\n"
                        + "from products A,  usermaster C, category D \n"
                        + "where A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "a.flag='Y' and a.prodid=?";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, pid);
                ResultSet rs;
                rs = ps.executeQuery();
                if (rs.next()) {
                    exist = true;
                    p.setProdid(rs.getString("PRODID"));
                    p.setProd_desc(rs.getString("PRODDESC"));
                    p.setCrtdt(rs.getString("CRTDT"));
                    p.setCatid(rs.getInt("CATID"));
                    p.setMaincat(rs.getString("MAINCAT"));
                    p.setSubcat(rs.getString("SUBCAT"));
                    p.setSeller(rs.getString("USER_NAME"));
                }
                rs.close();
                ps.close();
                if (exist) {
                    sql = "select slno, about from aboutprod where prodid=? order by slno asc";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, pid);
                    rs = ps.executeQuery();
                    List<Integer> _aslno = new ArrayList<>();
                    List<String> _abt = new ArrayList<>();
                    while (rs.next()) {
                        _aslno.add(rs.getInt("slno"));
                        _abt.add(rs.getString("about"));
                    }
                    rs.close();
                    ps.close();
                    p.setAslno(_aslno);
                    p.setAbout(_abt);
                }
                if (exist) {
                    sql = "select SLNO,VARIANT,QTY,UNITPRICE,to_char(UNITPRICE,'9,99,99,999.99') UPS,DISCOUNT,DELIVERY from PROD_VARIANT where prodid=? order by slno asc";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, pid);
                    rs = ps.executeQuery();
                    List<Integer> _sl = new ArrayList<>();
                    List<Integer> _q = new ArrayList<>();
                    List<String> _variant = new ArrayList<>();
                    List<Integer> _disc = new ArrayList<>();
                    List<Integer> _delv = new ArrayList<>();
                    List<Double> _up = new ArrayList<>();
                    List<String> _ups = new ArrayList<>();
                    while (rs.next()) {
                        _sl.add(rs.getInt("SLNO"));
                        _q.add(rs.getInt("QTY"));
                        _variant.add(rs.getString("VARIANT"));
                        _disc.add(rs.getInt("DISCOUNT"));
                        _delv.add(rs.getInt("DELIVERY"));
                        _up.add(rs.getDouble("UNITPRICE"));
                        _ups.add(rs.getString("UPS"));
                    }
                    rs.close();
                    ps.close();
                    p.setVslno(_sl);
                    p.setVariant(_variant);
                    p.setQty(_q);
                    p.setUnitprice(_up);
                    p.setDiscount(_disc);
                    p.setDelivery(_delv);
                    p.setUnitpricestring(_ups);
                }
                if (exist) {
                    sql = "select SLNO,SPEC,SPECVAL,UNIT from PROD_SPEC where prodid=? order by slno asc";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, pid);
                    rs = ps.executeQuery();
                    List<String> _spec = new ArrayList<>();
                    List<String> _specval = new ArrayList<>();
                    List<String> _unit = new ArrayList<>();
                    while (rs.next()) {
                        _spec.add(rs.getString("SPEC"));
                        _specval.add(rs.getString("SPECVAL"));
                        _unit.add(rs.getString("UNIT"));
                    }
                    rs.close();
                    ps.close();
                    p.setSpec(_spec);
                    p.setSpecval(_specval);
                    p.setUnit(_unit);
                }
                if (exist) {
                    sql = "select SLNO,FILENAME,PATH from PROD_IMAGE where prodid=? order by slno asc";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, pid);
                    rs = ps.executeQuery();
                    List<String> _fname = new ArrayList<>();
                    List<String> _path = new ArrayList<>();
                    while (rs.next()) {
                        _fname.add(rs.getString("FILENAME"));
                        _path.add(rs.getString("PATH"));
                    }
                    rs.close();
                    ps.close();
                    p.setFilename(_fname);
                    p.setPath(_path);
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return p;
    }

    //addprodtoCart
    public String addprodtoCart(String prodid, int slno, HttpSession session) {
        String msg = "0";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into CUST_CART(CUSTID,PRODID, SLNO, QTY) values( ?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ps.setString(2, prodid);
                ps.setInt(3, slno);
                ps.setInt(4, 1);
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "T";
                }
                ps.close();
                sql = "select count(*) from CUST_CART where CUSTID=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                msg = rs.getString(1);
                rs.close();
                ps.close();
                try {
                    if (Integer.parseInt(msg) > 0) {
                        msg = "<a href=\"showCart\" style=\"color:orange;text-decoration: none;\">" + msg + "</a>";
                    }
                } catch (NumberFormatException ex) {
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //getCartCnt
    public String getCartCnt(HttpSession session) {
        String msg = "0";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select count(*) from CUST_CART where CUSTID=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                rs.next();
                msg = rs.getString(1);
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //getCartDetails
    public List<CartProduct> getCartDetails(HttpSession session) {
        ArrayList<CartProduct> acp = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select a.custid, a.prodid, a.slno, a.qty,a.crtdt,to_char(a.crtdt,'DD-Mon-YYYY') CCRTDT,b.catid,b.prod_desc,b.custid sellerid,c.maincat, c.subcat, c.cat_desc,d.user_name sellername,e.variant, to_char(e.unitprice,'9,99,99,999.99') UNITPRICE, e.discount, to_char(e.UNITPRICE-e.unitprice*e.discount/100,'9,99,99,999.99') SELLPRICE,e.delivery, f.filename, f.path,f.imgdesc\n"
                        + "from CUST_CART A, PRODUCTS B, CATEGORY C, USERMASTER D, PROD_VARIANT E, PROD_IMAGE F\n"
                        + "where a.prodid = b.prodid and\n"
                        + "a.prodid= e.prodid and\n"
                        + "a.slno = e.slno and\n"
                        + "b.catid= c.catid and\n"
                        + "b.prodid = f.prodid and\n"
                        + "b.custid = d.custid and\n"
                        + "f.slno = 1 and\n"
                        + "a.custid = ? order by 5 desc";

                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();

                while (rs.next()) {
                    //System.out.println(rs.getString("PROD_DESC"));
                    CartProduct lvcp = new CartProduct();
                    lvcp.setProdid(rs.getString("PRODID"));
                    lvcp.setCartslno(rs.getInt("SLNO"));
                    lvcp.setQty(rs.getInt("QTY"));
                    lvcp.setCrtdt(rs.getString("CCRTDT"));
                    lvcp.setCatid(rs.getInt("CATID"));
                    lvcp.setMaincat(rs.getString("MAINCAT"));
                    lvcp.setSubcat(rs.getString("SUBCAT"));
                    lvcp.setProd_desc(rs.getString("PROD_DESC"));
                    lvcp.setSellername(rs.getString("SELLERNAME"));

                    lvcp.setVariant(rs.getString("VARIANT"));
                    lvcp.setUnitprice(rs.getString("UNITPRICE"));
                    lvcp.setDiscount(rs.getInt("DISCOUNT"));
                    lvcp.setDelivery(rs.getInt("DELIVERY"));
                    lvcp.setSellingprice(rs.getString("SELLPRICE"));

                    lvcp.setFilename(rs.getString("FILENAME"));
                    lvcp.setPath(rs.getString("PATH"));
                    acp.add(lvcp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return acp;
    }

    public String getCartTotal(HttpSession session) {
        String retval = "";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select to_char(sum(UNITPRICE-unitprice*discount/100),'9,99,99,999.99') from CUST_CART a,prod_variant b where a.prodid=b.prodid and a.slno=b.slno and a.custid = ?";

                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();
                rs.next();
                retval = rs.getString(1);
                rs.close();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return retval;
    }

    public void delCartItem(String prodid, int pslno, HttpSession session) {
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "delete from CUST_CART where prodid=? and slno=? and custid = ?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ps.setInt(2, pslno);
                ps.setString(3, session.getAttribute("CUSTID").toString());
                ps.executeUpdate();
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
    }

    //createOrder
    public String createOrder(String aslno, String wslno, HttpSession session) {
        wslno = wslno.substring(0, wslno.indexOf("-"));
        //System.out.println(wslno);
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        String retval = "";
        String _ordid = "NA";
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                db.setAutoCommit(false);
                //GET NEXT ORDERID
                String sql = "select 'Ord'||lpad(nvl(max(substr(orderid,4,7)),0)+1,7,'0') from ordermaster";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                rs.next();
                _ordid = rs.getString(1);
                rs.close();
                ps.close();
                sql = "insert into ordermaster (ORDERID,CUSTID,PAYMENT,ASLNO) values(?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, _ordid);
                ps.setString(2, session.getAttribute("CUSTID").toString());
                ps.setString(3, "WALLET");
                ps.setInt(4, Integer.parseInt(aslno));
                int reccnt = ps.executeUpdate();
                ps.close();
                if (reccnt > 0) {   //IF ORDERMASTER IS POPULATED
                    //GET NEXT TRANID
                    sql = "select nvl(max(substr(TRANID,2,5)),0)+1 from transactionmaster";
                    ps = db.getPrepStmt(sql);
                    rs = ps.executeQuery();
                    rs.next();
                    int _tranid = rs.getInt(1);
                    rs.close();
                    ps.close();
                    //System.out.println(_tranid);
                    String x = String.format("%05d", _tranid);
                    //System.out.println(x);
                    //System.out.println('T' + x);
                    //CREATE TRANSACTION RECORDS
                    sql = "select a.CUSTID, a.PRODID,a.SLNO prodslno,a.QTY,b.unitprice,b.unitprice*(100-b.discount)/100 AMOUNT,b.discount,c.custid SELLERID \n"
                            + " from CUST_CART A,PROD_VARIANT B, PRODUCTS C \n"
                            + " where A.PRODID=B.PRODID and A.SLNO=B.SLNO and c.prodid=b.prodid and a.CUSTID='" + session.getAttribute("CUSTID") + "'";
                    //System.out.println(sql);
                    ps = db.getPrepStmt(sql);
                    //System.out.println("after exec");
                    //ps.setString(1, session.getAttribute("CUSTID").toString());
                    rs = ps.executeQuery(sql);
                    //System.out.println("after exec1");
                    String sql1;
                    int prdslno = 0;
                    Double _amt = 0.0;
                    while (rs.next()) {
                        prdslno++;
                        //CREATE TRANID RECORDS
                        sql1 = "insert into transactionmaster(TRANID,CUSTID,WSLNO,SELLERID,AMOUNT) values(?,?,?,?,?)";
                        ps1 = db.getPrepStmt1(sql1);
                        //System.out.println("T" +String.format("%05d", _tranid));
                        ps1.setString(1, "T" + String.format("%05d", _tranid));
                        //System.out.println(session.getAttribute("CUSTID").toString());
                        ps1.setString(2, session.getAttribute("CUSTID").toString());
                        //System.out.println(wslno);
                        ps1.setInt(3, Integer.parseInt(wslno));
                        //System.out.println(rs.getString("SELLERID"));
                        ps1.setString(4, rs.getString("SELLERID"));
                        //System.out.println(rs.getString("AMOUNT"));
                        ps1.setDouble(5, Double.parseDouble(rs.getString("AMOUNT")));
                        ps1.executeUpdate();
                        ps1.close();
                        //SELLER WALLET
                        sql1 = "update cust_wallet set amount=amount+?,LASTUSEDT=sysdate where custid=? and slno=(select min(slno) from cust_wallet where custid=?)";
                        ps1 = db.getPrepStmt1(sql1);
                        ps1.setDouble(1, Double.parseDouble(rs.getString("AMOUNT")));
                        ps1.setString(2, rs.getString("SELLERID"));
                        ps1.setString(3, rs.getString("SELLERID"));
                        ps1.executeUpdate();
                        ps1.close();
                        _amt = _amt + Double.valueOf(rs.getString("AMOUNT"));
                        //CREATE ORDER DETAIL RECORDS
                        sql1 = "insert into orderdetail(ORDERID,ITEMNO,PRODID,SLNO,QTY,UNITPRICE,SHIPPING,DISCOUNT,FINALPRICE,SELLERID,TRANID) \n"
                                + "values(?,?,?,?,?,?,?,?,?,?,?)";
                        ps1 = db.getPrepStmt1(sql1);
                        ps1.setString(1, _ordid);
                        ps1.setInt(2, prdslno);
                        ps1.setString(3, rs.getString("PRODID"));
                        ps1.setInt(4, rs.getInt("prodslno"));
                        ps1.setInt(5, rs.getInt("QTY"));
                        ps1.setDouble(6, rs.getDouble("UNITPRICE"));
                        //ps1.setInt(7, rs.getInt("SHIPPING"));
                        ps1.setInt(7, 0);
                        ps1.setInt(8, rs.getInt("DISCOUNT"));
                        ps1.setDouble(9, Double.parseDouble(rs.getString("AMOUNT")));
                        ps1.setString(10, rs.getString("SELLERID"));
                        ps1.setString(11, "T" + String.format("%05d", _tranid));
                        ps1.executeUpdate();
                        ps1.close();
                        sql1 = "insert into orderstatus(ORDERID,ITEMNO,STATUSID,REMARKS) values(?,?,?,?)";
                        ps1 = db.getPrepStmt1(sql1);
                        ps1.setString(1, _ordid);
                        ps1.setInt(2, prdslno);
                        ps1.setInt(3, 1);
                        ps1.setString(4, "Waiting for Seller approval");
                        ps1.executeUpdate();
                        ps1.close();
                        _tranid = _tranid + 1;
                    }
                    rs.close();
                    ps.close();
                    //UPDATE WALLET AMOUNT
                    sql = "update cust_wallet set amount=amount-?,LASTUSEDT=sysdate where custid=? and slno=?";
                    ps = db.getPrepStmt(sql);
                    ps.setDouble(1, _amt);
                    ps.setString(2, session.getAttribute("CUSTID").toString());
                    ps.setInt(3, Integer.parseInt(wslno));
                    ps.executeUpdate();
                    ps.close();
                    //REFRESH CART
                    sql = "delete from cust_cart where custid=?";
                    ps = db.getPrepStmt(sql);
                    ps.setString(1, session.getAttribute("CUSTID").toString());
                    ps.executeUpdate();
                    ps.close();
                }
                db.commit();
                retval = _ordid;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            try {
                //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
                db.rollback();
            } catch (SQLException ex1) {
            }
            retval = ex.toString();
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    retval = se.toString();
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                retval = se.toString();
                System.out.println("SQLException:" + se.toString());
            }
        }
        return retval;
    }

    public List<OrderDetails> getOrderDetails(String ordid) {
        ArrayList<OrderDetails> olist = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select * from vworder where orderid=? order by itemno";
                ps = db.getPrepStmt(sql);
                ps.setString(1, ordid);
                ResultSet rs;
                rs = ps.executeQuery();

                while (rs.next()) {

                    OrderDetails lvod = new OrderDetails();
                    lvod.setOrderid(ordid);
                    lvod.setCustomerid(rs.getString("CUSTOMERID"));
                    lvod.setPayment(rs.getString("payment"));
                    lvod.setCrtdt(rs.getString("CRTDT"));
                    lvod.setItemno(rs.getInt("itemno"));
                    lvod.setProdid(rs.getString("PRODID"));
                    lvod.setQty(rs.getInt("qty"));
                    lvod.setUnitprice(rs.getDouble("UNITPRICE"));
                    lvod.setUprice(rs.getString("UPRICE"));
                    lvod.setDiscount(rs.getInt("discount"));
                    lvod.setFinalprice(rs.getDouble("FINALPRICE"));
                    lvod.setSellprice(rs.getString("SELLPRICE"));
                    lvod.setShipping(rs.getInt("shipping"));
                    lvod.setSellername(rs.getString("SELLERNAME"));
                    lvod.setTranid(rs.getString("TRANID"));
                    lvod.setProd_desc(rs.getString("PROD_DESC"));
                    lvod.setVariant(rs.getString("VARIANT"));
                    lvod.setWalletname(rs.getString("WALLET_NAME"));
                    lvod.setAliasname(rs.getString("ALIAS_NAME"));
                    lvod.setAddr1(rs.getString("ADDR1"));
                    lvod.setAddr2(rs.getString("ADDR2"));
                    lvod.setAddr3(rs.getString("ADDR3"));
                    lvod.setCity(rs.getString("CITY"));
                    lvod.setState(rs.getString("STATE"));
                    lvod.setPincode(rs.getString("PINCODE"));
                    lvod.setPath(rs.getString("PATH"));
                    lvod.setFilename(rs.getString("FILENAME"));
                    lvod.setStatusid(rs.getInt("STATUSID"));
                    lvod.setStatus(rs.getString("STATUS"));
                    lvod.setRemark(rs.getString("REMARK"));
                    lvod.setStatusdt(rs.getString("STATUSDT"));
                    olist.add(lvod);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return olist;
    }

    //getWalletList
    public ArrayList<Wallet> getWalletList(HttpSession session) {
        ArrayList<Wallet> Wlist = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select SLNO, WALLET_NAME, AMOUNT from CUST_WALLET where CUSTID = ?";

                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();

                while (rs.next()) {

                    Wallet lwallet = new Wallet();

                    lwallet.setSlno(rs.getInt("SLNO"));
                    lwallet.setAmount(rs.getDouble("AMOUNT"));
                    lwallet.setWallet_name(rs.getString("WALLET_NAME"));

                    Wlist.add(lwallet);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return Wlist;
    }

    public String fillWallet(HttpSession session, String name, String amt) {
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        String msg = "";
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "update Cust_wallet set AMOUNT = AMOUNT + ? where CUSTID = ? and SLNO = ?";

                ps = db.getPrepStmt(sql);
                ps.setDouble(1, Double.parseDouble(amt));
                ps.setString(2, session.getAttribute("CUSTID").toString());
                ps.setInt(3, Integer.parseInt(name));
                int i = ps.executeUpdate();

                if (i > 0) {
                    msg = "Amount Added";
                } else {
                    msg = "Something went Wrong!";
                }
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //SEARCH-searchResult
    public List<NewProduct> searchResult(String q) {
        q = q.replace(' ', '%');
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT, B.UNITPRICE, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME\n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "B.SLNO=(select min(slno) from prod_variant where prodid=A.prodid) and\n"
                        + "a.flag='Y' and\n"
                        + "a.prodid in(select distinct prodid from VWSEARCH where upper(stext) like '%'||upper('" + q + "')||'%')\n"
                        + "order by crtdt desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //addprodtoWishlist
    public String addprodtoWishlist(String prodid, int slno, HttpSession session) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into WISHLIST(CUSTID, SLNO, PRODID, PSLNO) select ?,nvl(max(slno),0)+1,?,? from WISHLIST where CUSTID=?";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ps.setString(2, prodid);
                ps.setInt(3, slno);
                ps.setString(4, session.getAttribute("CUSTID").toString());
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "T";
                } else {
                    msg = "F";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //showWISHLIST
    public List<NewProduct> getWishList(HttpSession session) {
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT,B.slno VSLNO, B.UNITPRICE, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME,f.slno \n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E, wishlist F \n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "b.prodid=f.prodid and b.slno=f.pslno and \n"
                        + "a.flag='Y' and f.custid=? \n"
                        + "order by f.slno desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setVslno(rs.getInt("VSLNO"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //getorderlist
    public List<String> getOrderidList(HttpSession session) {
        ArrayList<String> ordList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select distinct orderid from ORDERMASTER where custid=?";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    ordList.add(rs.getString("ORDERID"));
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return ordList;
    }

    public List<OrderDetails> getSellerOrderDetails(HttpSession session) {
        ArrayList<OrderDetails> olist = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select a.orderid,b.itemno,c.prod_desc,d.variant,a.CUSTID,h.user_name customername, a.payment,to_char(a.crtdt,'DD-Mon-YYYY') crtdt, b.qty,to_char(b.unitprice,'9,99,99,999.99') uprice,b.discount,to_char(b.finalprice,'9,99,99,999.99') sellprice,b.shipping,b.tranid\n"
                        + "from ordermaster a, orderdetail b, products c, prod_variant d, usermaster h\n"
                        + "where a.orderid=b.orderid and b.prodid=c.prodid and b.prodid=d.prodid and b.slno=d.slno and h.custid=a.custid and b.sellerid=? order by orderid desc";
                ps = db.getPrepStmt(sql);
                ps.setString(1, session.getAttribute("CUSTID").toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    OrderDetails lvod = new OrderDetails();
                    lvod.setOrderid(rs.getString("ORDERID"));
                    lvod.setItemno(rs.getInt("itemno"));
                    lvod.setProd_desc(rs.getString("PROD_DESC"));
                    lvod.setVariant(rs.getString("VARIANT"));
                    lvod.setCustomerid(rs.getString("CUSTID"));
                    lvod.setCustomername(rs.getString("CUSTOMERNAME"));
                    lvod.setPayment(rs.getString("payment"));
                    lvod.setCrtdt(rs.getString("CRTDT"));
                    lvod.setQty(rs.getInt("qty"));
                    lvod.setUprice(rs.getString("UPRICE"));
                    lvod.setDiscount(rs.getInt("discount"));
                    lvod.setSellprice(rs.getString("SELLPRICE"));
                    lvod.setShipping(rs.getInt("shipping"));
                    lvod.setTranid(rs.getString("TRANID"));
                    olist.add(lvod);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return olist;
    }

    public List<Integer> getAdminProductCnt() {
        List<Integer> cnt = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select 1,count(*) from products where sysdate-crtdt<=7\n"
                        + "union\n"
                        + "select 2,count(*) from products where sysdate>crtdt+7 and sysdate-crtdt<=14\n"
                        + "union\n"
                        + "select 3,count(*) from products where sysdate>crtdt+14 and sysdate-crtdt<=21\n"
                        + "union\n"
                        + "select 4,count(*) from products where sysdate>crtdt+21 and sysdate-crtdt<=28";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    cnt.add(rs.getInt(2));
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return cnt;
    }

    public List<String> getAdminOrderAmount() {
        List<String> lst = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select maincat||'-'||subcat,sum(finalprice) from category a,orderdetail b, products c, ordermaster d where d.orderid=b.orderid and d.crtdt>sysdate-30 and a.catid=c.catid and b.prodid=c.prodid group by maincat||'-'||subcat";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lst.add(rs.getString(1) + "#" + rs.getString(2));
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return lst;
    }

    public List<String> getAdminMonthwiseOrderAmount() {
        List<String> lst = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select to_char(a.crtdt,'MON-YY'),sum(finalprice) from ordermaster a,orderdetail b where a.orderid=b.orderid and a.crtdt>sysdate-365 group by to_char(a.crtdt,'MON-YY') order by to_date(to_char(a.crtdt,'MON-YY'),'MON-YY')";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    lst.add(rs.getString(1) + "#" + rs.getString(2));
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return lst;
    }

    //registerLike
    public String registerLike(String prodid, String ltype, HttpSession session) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into PRODLIKE(PRODID, LTYPE, CRTBY) values(?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ps.setString(2, ltype);
                ps.setString(3, session.getAttribute("CUSTID").toString());
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "T";
                } else {
                    msg = "F";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.toString().contains("ORA-00001: unique constraint")) {
                msg = "A";
            }
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    public ArrayList<Status> getStatusList() {
        ArrayList<Status> st = new ArrayList<>();

        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select Statusid, statusdesc from status order by 1";
                ps = db.getPrepStmt(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Status s = new Status();
                    s.setStatus_desc(rs.getString("statusdesc"));
                    s.setStatusID(rs.getInt("statusid"));
                    st.add(s);
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.toString().contains("ORA-00001: unique constraint")) {
                msg = "A";
            }
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }

        return st;
    }

    public String populateStatusDetails(String orderid, String itemno, String status, String rmk) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "insert into orderstatus(ORDERID, ITEMNO, STATUSID, REMARKS) values(?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, orderid);
                ps.setInt(2, Integer.parseInt(itemno));
                ps.setInt(3, Integer.parseInt(status));
                ps.setString(4, rmk);
                int i = ps.executeUpdate();

                if (i > 0) {
                    msg = "Successfully Updated";
                } else {
                    msg = "Couldnt Update Status";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.toString().contains("ORA-00001: unique constraint")) {
                msg = "A";
            }
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }

        return msg;
    }
    //getProductDetforReview

    public NewProduct getProductDetforReview(String pid) {
        NewProduct np = new NewProduct();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {
                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,c.user_name,D.maincat,D.subcat,E.filename,E.path \n"
                        + "from products A, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "a.flag='Y' and a.prodid=?\n";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ps.setString(1, pid);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    np.setProdid(rs.getString("PRODID"));
                    np.setProd_desc(rs.getString("PRODDESC"));
                    np.setMaincat(rs.getString("MAINCAT"));
                    np.setSubcat(rs.getString("SUBCAT"));
                    np.setFilename(rs.getString("FILENAME"));
                    np.setPath(rs.getString("PATH"));
                    np.setSeller(rs.getString("USER_NAME"));
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return np;
    }

    //postReview
    public String postReview(String prodid, String rating, String headline, String review, HttpSession session) {
        String msg = "F";
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "insert into PRODREVIEW(PRODID,RATING,HEADLINE,REVIEW,REVIEWER) values(?,?,?,?,?)";
                ps = db.getPrepStmt(sql);
                ps.setString(1, prodid);
                ps.setInt(2, Integer.parseInt(rating));
                ps.setString(3, headline);
                ps.setString(4, review);
                ps.setString(5, session.getAttribute("CUSTID").toString());
                int i = ps.executeUpdate();
                if (i > 0) {
                    msg = "Review successfully posted!";
                } else {
                    msg = "Unable to post Review!";
                }
                ps.close();
            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.toString().contains("ORA-00001: unique constraint")) {
                msg = "Something went wrong!";
            }
            System.out.println(ex.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException se) {
                    System.out.println("SQLException:" + se.toString());
                }
            }
            try {
                db.connectionClose();
            } catch (SQLException se) {
                System.out.println("SQLException:" + se.toString());
            }
        }
        return msg;
    }

    //TOPOffers
    public List<NewProduct> getTopOffersProductList() {
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT, B.UNITPRICE,to_char(B.UNITPRICE,'9,99,99,999.99') UPSTRING, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME\n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "B.SLNO=(select min(slno) from prod_variant where prodid=A.prodid) and\n"
                        + "a.flag='Y' and b.discount>0\n"
                        + "order by discount desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    lvnp.setUnitpricestring(rs.getString("UPSTRING"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //TOPOffers
    public List<NewProduct> getMobileProductList() {
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT, B.UNITPRICE,to_char(B.UNITPRICE,'9,99,99,999.99') UPSTRING, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME\n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "B.SLNO=(select min(slno) from prod_variant where prodid=A.prodid) and\n"
                        + "a.flag='Y' and A.catid=6\n"
                        + "order by crtdt desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    lvnp.setUnitpricestring(rs.getString("UPSTRING"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //getComputerProductList
    public List<NewProduct> getComputerProductList() {
        ArrayList<NewProduct> npList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select A.prodid PRODID,A.prod_desc PRODDESC,to_char(A.crtdt,'DD-Mon-YYYY') createdt,A.crtdt,B.VARIANT VARIANT, B.UNITPRICE,to_char(B.UNITPRICE,'9,99,99,999.99') UPSTRING, B.DISCOUNT, A.catid,D.maincat,D.subcat,E.filename,E.path,C.USER_NAME\n"
                        + "from products A, prod_variant B, usermaster C, category D,prod_image E\n"
                        + "where A.prodid=B.prodid and\n"
                        + "A.prodid=E.prodid and\n"
                        + "A.custid=C.custid and\n"
                        + "A.catid=D.catid and\n"
                        + "E.SLNO=(select min(slno) from prod_image where prodid=A.prodid) and\n"
                        + "B.SLNO=(select min(slno) from prod_variant where prodid=A.prodid) and\n"
                        + "a.flag='Y' and A.catid=4\n"
                        + "order by crtdt desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    NewProduct lvnp = new NewProduct();
                    lvnp.setProdid(rs.getString("PRODID"));
                    lvnp.setProd_desc(rs.getString("PRODDESC"));
                    lvnp.setCrtdt(rs.getString("createdt"));
                    lvnp.setVariant(rs.getString("VARIANT"));
                    lvnp.setUnitprice(Double.parseDouble(rs.getString("UNITPRICE")));
                    lvnp.setDiscount(rs.getInt("DISCOUNT"));
                    lvnp.setCatid(rs.getInt("CATID"));
                    lvnp.setMaincat(rs.getString("MAINCAT"));
                    lvnp.setSubcat(rs.getString("SUBCAT"));
                    lvnp.setFilename(rs.getString("FILENAME"));
                    lvnp.setPath(rs.getString("PATH"));
                    lvnp.setSeller(rs.getString("USER_NAME"));
                    lvnp.setUnitpricestring(rs.getString("UPSTRING"));
                    npList.add(lvnp);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return npList;
    }

    //getReviewList
    public ArrayList<ProductReview> getReviewList() {
        ArrayList<ProductReview> prList = new ArrayList<>();
        ConnectDB db = new ConnectDB();
        PreparedStatement ps = null;
        try {
            String retVal = db.dbConnect();
            if (retVal.equalsIgnoreCase("OK")) {

                String sql = "select a.prodid,a.rating,a.headline,a.review,a.reviewer,c.user_name reviewername,a.crtdt,to_char(a.crtdt,'dd-Mon-YYYY') CREATEDON,b.prod_desc PRODDESC,b.custid sellerid,d.user_name sellername from\n"
                        + "prodreview a, products b, usermaster c,usermaster d\n"
                        + "where a.prodid=b.prodid and\n"
                        + "a.reviewer=c.custid and\n"
                        + "b.custid=d.custid order by crtdt desc";
                //System.out.println(sql);
                ps = db.getPrepStmt(sql);
                ResultSet rs;
                rs = ps.executeQuery();
                while (rs.next()) {
                    ProductReview lvpr = new ProductReview();
                    lvpr.setProdid(rs.getString("PRODID"));
                    lvpr.setProd_desc(rs.getString("PRODDESC"));
                    lvpr.setCrtdt(rs.getString("CREATEDON"));
                    lvpr.setRating(rs.getInt("RATING"));
                    lvpr.setHeadline(rs.getString("HEADLINE"));
                    lvpr.setReview(rs.getString("REVIEW"));
                    lvpr.setReviewer(rs.getString("REVIEWER"));
                    lvpr.setReviewername(rs.getString("REVIEWERNAME"));
                    lvpr.setSellerid(rs.getString("sellerid"));
                    lvpr.setSellername(rs.getString("sellername"));
                    
                    prList.add(lvpr);
                }
                rs.close();
                ps.close();

            }
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DemoService.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        }
        return prList;
    }
}
