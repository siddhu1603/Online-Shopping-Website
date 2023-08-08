/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.mvc.controller;

import com.shopping.model.Address;
import com.shopping.model.CartProduct;
import com.shopping.model.Category;
import com.shopping.model.Customer;
import com.shopping.model.NewProduct;
import com.shopping.model.Product;
import com.shopping.model.ProductReview;
import com.shopping.model.ProductVariant;
import com.shopping.model.Status;
import com.shopping.model.Template;
import com.shopping.model.User;
import com.shopping.model.Wallet;
import com.shopping.service.DemoService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping({"/home", "/"})
    public String home() {
        return "index";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLogin(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String cstr = DemoService.generateCaptchaTextMethod(6);
        DemoService ds = new DemoService();
        try {
            String fname = ds.generateCaptcha(cstr, session, request);
            session.setAttribute("fname", fname);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        session.setAttribute("capval", cstr);
        return "login";
    }

    @RequestMapping(path = "/doLogin", method = RequestMethod.POST)
    public String doLogin(Model model, HttpServletRequest request, HttpSession session) {
        DemoService ds = new DemoService();
        User vUser = ds.authenticate(request.getParameter("txtuid"), request.getParameter("txtpwd"), request.getParameter("logintype"));
        //SET SESSION VARIABLE
        if (vUser.getIsUserValid()) {
            session.setAttribute("UID", vUser.getUserid());
            session.setAttribute("USERNAME", vUser.getUsername());
            session.setAttribute("USERMOBILE", vUser.getMobile());
            session.setAttribute("USEREMAIL", vUser.getEmail());
            session.setAttribute("CUSTID", vUser.getCustid());
            session.setAttribute("USERDOB", vUser.getDob());
            session.setAttribute("USERGENDER", vUser.getGender());
            session.setAttribute("USERTYPE", vUser.getCusttype());
        }
        //model.addAttribute("objLogin", vUser);
        model.addAttribute("isvalid", (String) vUser.getIsUserValid().toString());
        if (vUser.getIsUserValid()) {
            return "redirect:/home";
        } else {
            return "login";
        }
    }

    //logout
    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String signout(HttpSession session) {
        //System.out.println(session.getAttribute("UID"));
        if (session.getAttribute("UID") != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }

    @RequestMapping("/validateCap")
    public String validateCAP() {
        return "validateCap";
    }

    //REGISTER USER
    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String signup() {
        return "register";
    }

    @RequestMapping("/validateUID")
    public String validateUID() {
        return "validateUID";
    }

    @RequestMapping(path = "/createUser", method = RequestMethod.POST)
    public String registerUser(Model model, @ModelAttribute User user) { //staff is class defined in model package|| user is object name
        //System.out.println(cust);
        DemoService ds = new DemoService();
        String retVal = ds.registerUser(user);
        model.addAttribute("sysmsg", retVal);
        //System.out.println(retVal);
        return "register";
    }

    //request mapping for add address
    @RequestMapping(path = "/addAddress", method = RequestMethod.GET)
    public String addAddress(Model model, HttpSession session) {
        //GET EXISTING ADDRESS DETAILS
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "addAddress";
        }
    }

    //displayAddress
    @RequestMapping(path = "/displayAddress", method = RequestMethod.GET)
    public String displayAddress() {
        return "displayAddress";
    }

    @RequestMapping(path = "/submitAddress", method = RequestMethod.POST)
    public String submitAddress(Model m, @ModelAttribute Address addr, HttpSession session) {
        DemoService ds = new DemoService();
        String retval = ds.addAddr(addr, session);
        m.addAttribute("retval", retval);

        return "addAddress";
    }

    //addWallet
    @RequestMapping(path = "/addWallet", method = RequestMethod.GET)
    public String addWallet(Model model, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "addWallet";
        }
    }

    //displayWallets
    @RequestMapping(path = "/displayWallets", method = RequestMethod.GET)
    public String displayWallets() {
        return "displayWallets";
    }

    //addWalt
    @RequestMapping(path = "/addWalt", method = RequestMethod.POST)
    public String addWalt(Model m, @ModelAttribute Wallet w, HttpSession session) {
        DemoService ds = new DemoService();
        String retval = ds.addWallet(w, session);
        m.addAttribute("retval", retval);
        return "addWallet";
    }

    //vwProfile
    @RequestMapping(path = "/vwProfile", method = RequestMethod.GET)
    public String vwProfile(HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "viewProfile";
        }
    }

    //edProfile
    @RequestMapping(path = "/edProfile", method = RequestMethod.GET)
    public String edProfile(HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "edProfile";
        }
    }

    //updtUser
    @RequestMapping(path = "/updtUser", method = RequestMethod.POST)
    public String updateUser(Model model, @ModelAttribute Customer cust, HttpSession session) { //staff is class defined in model package|| user is object name
        //System.out.println(cust);
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            String retVal = ds.updateUser(cust, session);
            model.addAttribute("sysmsg", retVal);
            //System.out.println(retVal);
            return "edProfile";
        }
    }

    //addProdVar
    @RequestMapping(path = "/addProdVar", method = RequestMethod.GET)
    public String addProdVar(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            ArrayList<Category> _cat = ds.getCategory();
            m.addAttribute("cat", _cat);
            return "addProduct";
        }
    }

    //submitProdVar
    @RequestMapping(path = "/submitProdVar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String submitProdWithVariants(@RequestBody MultiValueMap<String, String> formData, HttpSession session) {
        //CREATE AN OBJECT TO STORE VALUES RECEIVED FROM JSP FORM
        //System.out.println("1");
        ProductVariant p = new ProductVariant();
        //System.out.println("1a");
        //p.setCatid(6);
        p.setCatid(Integer.parseInt(formData.getFirst("catid")));
        //p.setCatid(Integer.parseInt(formData.get("catid").toString()));
        //System.out.println("1a");

        p.setProd_desc(formData.getFirst("prod_desc"));
        //System.out.println("1b"+session.getAttribute("CUSTID").toString());
        p.setCustid(session.getAttribute("CUSTID").toString());
        //System.out.println("11");
        p.setVariant(formData.get("variant"));
        List<Integer> pqty = new ArrayList<>();
        for (String q : formData.get("qty")) {
            pqty.add(Integer.valueOf(q));
        }
        p.setQty(pqty);
        //System.out.println("111");
        List<Integer> pdisc = new ArrayList<>();
        for (String d : formData.get("discount")) {
            pdisc.add(Integer.valueOf(d));
        }
        p.setDiscount(pdisc);
        //System.out.println("1111");
        List<Integer> pdelv = new ArrayList<>();
        for (String d : formData.get("qty")) {
            pdelv.add(Integer.valueOf(d));
        }
        p.setDelivery(pdelv);
        //System.out.println("111111");
        List<Double> pprice = new ArrayList<>();
        for (String pr : formData.get("unitprice")) {
            pprice.add(Double.valueOf(pr));
        }
        p.setUnitprice(pprice);
        //call a function in Service Class to insert record
        DemoService ds = new DemoService();
        String retval = ds.addProductVariant(p, session);
        session.setAttribute("SYSMSG", retval);
        return "redirect:/addProdVar";
    }

    //addProdSpec
    @RequestMapping(path = "/addProdSpec", method = RequestMethod.GET)
    public String addProdSpec(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            ArrayList<Product> _prod = ds.getProductList(session);
            m.addAttribute("prod", _prod);
            return "prodSpec";
        }
    }

    //displaySpec
    @RequestMapping(path = "/displaySpec", method = RequestMethod.GET)
    public String displaySpec() {
        return "displaySpec";
    }

    //insProdSpec
    @RequestMapping(path = "/insProdSpec", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String insProdSpec(@RequestBody MultiValueMap<String, String> formData, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            ArrayList<Template> tlist = new ArrayList<>();
            for (int i = 0; i < formData.get("slno").size(); i++) {
                Template lvTemplate = new Template();
                lvTemplate.setProdid(formData.getFirst("prodid"));
                lvTemplate.setSlno(Integer.parseInt(formData.get("slno").get(i)));
                lvTemplate.setSpec(formData.get("spec").get(i));
                lvTemplate.setSpecval(formData.get("specval").get(i));
                lvTemplate.setUnit(formData.get("unit").get(i));
                tlist.add(lvTemplate);
            }
            DemoService ds = new DemoService();
            String retval = ds.insSpec(tlist);
            return "redirect:/addProdSpec";
        }
    }

    //addProdImg
    @RequestMapping(path = "/addProdImg", method = RequestMethod.GET)
    public String addProdImg(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            ArrayList<Product> _prod = ds.getProductImgList(session);
            m.addAttribute("prod", _prod);
            return "prodImages";
        }
    }

    //displayProdImages
    @RequestMapping(path = "/displayProdImages", method = RequestMethod.GET)
    public String displayProdImages() {
        return "displayProdImages";
    }

    @RequestMapping(path = "/insImg", method = RequestMethod.POST)
    public String insImg() {
        return "uploadImage";
    }

    //abtProd
    @RequestMapping(path = "/abtProd", method = RequestMethod.GET)
    public String abtProd(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            ArrayList<Product> _prod = ds.getProductImgList(session);
            m.addAttribute("prod", _prod);
            return "abtProd";
        }
    }

    //displayProdAbout
    @RequestMapping(path = "/displayProdAbout", method = RequestMethod.GET)
    public String displayProdAbout() {
        return "displayProdAbout";
    }

    //insAbout
    @RequestMapping(path = "/insAbout", method = RequestMethod.POST)
    public String insAbout(Model m, HttpServletRequest request, HttpSession session) {
        //CALL SERVICE METHOD TO INSRT DATA
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            String msg = ds.addProdaobout(request.getParameter("prodid"), request.getParameter("txtabt"));
            m.addAttribute("msg", msg);
            return "redirect:/abtProd";
        }
    }

    //showProduct
    @RequestMapping(path = "/showProduct", method = RequestMethod.POST)
    public String showProduct(Model m, HttpServletRequest request) {

        return "showProduct";
    }

    //addProdtoCart
    @RequestMapping(path = "/addProdtoCart", method = RequestMethod.GET)
    public String addProdtoCart(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "addProdtoCart";
        }
    }

    //showCart
    @RequestMapping(path = "/showCart", method = RequestMethod.GET)
    public String showCart(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            DemoService ds = new DemoService();
            List<CartProduct> cp = ds.getCartDetails(session);
            String tot = ds.getCartTotal(session);
            m.addAttribute("ocp", cp);
            m.addAttribute("totval", tot);
            return "showCart";
        }
    }

    //deleteCart
    @RequestMapping(path = "/deleteCart", method = RequestMethod.GET)
    public String deleteCart(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("prodid")+"-"+request.getParameter("slno"));
            DemoService ds = new DemoService();
            ds.delCartItem(request.getParameter("prodid"), Integer.parseInt(request.getParameter("slno")), session);
            return "redirect:/showCart";
        }
    }

    //proceedToBuy
    @RequestMapping(path = "/proceedToBuy", method = RequestMethod.POST)
    public String proceedToBuy(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println("populate addredd");
            DemoService ds = new DemoService();
            List<Address> address = ds.getAddrList(session);
            m.addAttribute("addr", address);
            List<Wallet> wallet = ds.getWList(session);
            m.addAttribute("wall", wallet);
            String _tot = ds.getCartTotal(session);
            m.addAttribute("cartamount", _tot);
            return "proceedToBuy";
        }
    }

    //buyItem
    @RequestMapping(path = "/placeOrder", method = RequestMethod.POST)
    public String buyItem(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("btnradio"));
            //System.out.println(request.getParameter("btnwall"));
            //System.out.println("Create order table.order detail table. transaction table delete amt from wallet, empty cart");
            DemoService ds = new DemoService();
            String retval = ds.createOrder(request.getParameter("btnradio"), request.getParameter("btnwall"), session);
            m.addAttribute("ordid", retval);
            return "placeOrder";
        }
    }

    //add money
    @RequestMapping(path = "/addMoney", method = RequestMethod.GET)
    public String addMoney(Model m, HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("btnradio"));
            //System.out.println(request.getParameter("btnwall"));
            //System.out.println("Create order table.order detail table. transaction table delete amt from wallet, empty cart");
            DemoService ds = new DemoService();
            ArrayList<Wallet> Wlist = ds.getWalletList(session);
            m.addAttribute("Wlist", Wlist);
            return "addMoney";
        }
    }

    @RequestMapping(path = "/fillWallet", method = RequestMethod.POST)
    public String fillWallet(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("btnradio"));
            //System.out.println(request.getParameter("btnwall"));
            //System.out.println("Create order table.order detail table. transaction table delete amt from wallet, empty cart");
            DemoService ds = new DemoService();
            String retval = ds.fillWallet(session, request.getParameter("walletlist"), request.getParameter("txtmon"));
            m.addAttribute("msg", retval);
            return "redirect:/addMoney";
        }
    }

    @RequestMapping(path = "/searchShop", method = RequestMethod.POST)
    public String searchShop(Model m, HttpSession session, HttpServletRequest request) {
        m.addAttribute("q", request.getParameter("tsearch"));
        return "searchShop";
    }
//addProdtoWishlist

    @RequestMapping(path = "/addProdtoWishlist", method = RequestMethod.GET)
    public String addProdtoWishlist(Model m, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "addProdtoWishlist";
        }
    }

    //wishList
    @RequestMapping(path = "/wishList", method = RequestMethod.GET)
    public String myWishList(HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "myWishList";
        }
    }

    //myOrders
    @RequestMapping(path = "/myOrders", method = RequestMethod.GET)
    public String myOrders(HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "myOrders";
        }
    }

    //postLike
    @RequestMapping(path = "/postLike", method = RequestMethod.GET)
    public String postLike(HttpSession session) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            return "postLike";
        }
    }

    //updateOrdStat
    @RequestMapping(path = "/updateOrdStat", method = RequestMethod.POST)
    public String updateOrdStat(HttpSession session, Model model) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else if (session.getAttribute("USERTYPE").equals("S")) {
            DemoService ds = new DemoService();
            ArrayList<Status> st = ds.getStatusList();
            model.addAttribute("st", st);
            return "updateOrdStat";
        } else {
            return "redirect:/home";
        }
    }

    //statusUpdated
    @RequestMapping(path = "/statusUpdated", method = RequestMethod.POST)
    public String statusUpdated(HttpServletRequest request, Model model) {
        System.out.println(request.getParameter("orderid"));
        System.out.println(request.getParameter("itemno"));
        System.out.println(request.getParameter("status"));
        System.out.println(request.getParameter("rmk"));

        DemoService ds = new DemoService();
        String retval = ds.populateStatusDetails(request.getParameter("orderid"), request.getParameter("itemno"), request.getParameter("status"), request.getParameter("rmk"));
        model.addAttribute("retval", retval);
        return "redirect:/home";
    }

    //prodReview
    @RequestMapping(path = "/prodReview", method = RequestMethod.POST)
    public String prodReview(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("hprodid"));
            DemoService ds = new DemoService();
            NewProduct np = ds.getProductDetforReview(request.getParameter("hprodid"));
            model.addAttribute("np", np);
            return "productReview";
        }
    }

    //postReview
    @RequestMapping(path = "/postReview", method = RequestMethod.POST)
    public String postReview(Model model, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("UID") == null) {
            return "redirect:/login";
        } else {
            //System.out.println(request.getParameter("rating"));
            //System.out.println(request.getParameter("headline"));
            //System.out.println(request.getParameter("review"));
            //System.out.println(request.getParameter("hpid"));
            DemoService ds = new DemoService();
            String retval = ds.postReview(request.getParameter("hpid"), request.getParameter("rating"), request.getParameter("headline"), request.getParameter("review"), session);
            model.addAttribute("msg", retval);
            return "myOrders";
        }
    }

    //underDevelopment
    @RequestMapping(path = "/underDevelopment", method = RequestMethod.GET)
    public String underDevelopment() {
        return "ud";
    }
    //topOffers
    @RequestMapping(path = "/topOffers", method = RequestMethod.GET)
    public String topOffers() {
        return "topOffers";
    }
    //mobilePhones
    @RequestMapping(path = "/mobilePhones", method = RequestMethod.GET)
    public String mobilePhones() {
        return "mobiles";
    }
    //computerLaptop
    @RequestMapping(path = "/computerLaptop", method = RequestMethod.GET)
    public String computerLaptop() {
        return "compLaptop";
    }
    //vwProdReview
    @RequestMapping(path = "/vwProdReview", method = RequestMethod.GET)
    public String vwProdReview(Model model) {
        DemoService ds = new DemoService();
        ArrayList<ProductReview> prlist = ds.getReviewList();
        model.addAttribute("prlist", prlist);
        return "vwProdReview";
    }
}
