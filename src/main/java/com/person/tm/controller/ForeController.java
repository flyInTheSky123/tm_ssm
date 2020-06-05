package com.person.tm.controller;


import com.github.pagehelper.PageHelper;
import com.person.tm.comparator.*;
import com.person.tm.pojo.*;
import com.person.tm.service.*;
import com.person.tm.util.Page;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.print.DocFlavor;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//fore(前端页面)的后端部分
@Controller
@RequestMapping("")
public class ForeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    //跳转首页
    @RequestMapping("forehome")
    public String home(Model model) {
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        model.addAttribute("cs", cs);
        return "fore/home";
    }


    //注册用户
    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        //对前台传来的特殊字符进行转义，如 <script>alert('lalal')</script>。
        name = HtmlUtils.htmlEscape(name);

        user.setName(name);

        Boolean exits = userService.isExist(name);
        //判断注册的用户是否已经存在了
        if (exits) {
            //存在。
            String m = "该用户名已经存在！";
            model.addAttribute("msg", m);
            model.addAttribute("user", null);

            return "fore/register";
        }
        //不存在
        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    //用户登录
    @RequestMapping("forelogin")
    public String login(User user, Model model, HttpSession session) {
        User u = userService.get(user.getName(), user.getPassword());
        String str = "账号/密码为空，请重新输入";
        if (u == null) {
            model.addAttribute("msg", str);
            //返回页面，重新登录
            return "fore/login";
        }
        //登录成功，重定向到 首页
        session.setAttribute("user", u);
        return "redirect:forehome";
    }

    //用户登录
    @RequestMapping("foreout")
    public String login(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:forehome";
    }


    //通过ID，获取当前产品的信息。
    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {
        Product p = productService.get(pid);

        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);

        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        return "fore/product";
    }

    //判断当前状态是否为登录状态
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return "success";
        }
        return "fail";


    }

    //验证登录的账号，密码是否为true.
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String login(User user, HttpSession session) {
        String name = user.getName();
        String password = user.getPassword();
        //将name 进行转义--》如果有特殊字符则进行 转义。
        name = HtmlUtils.htmlEscape(user.getName());
        //通过name,password进行查找数据库。
        User u = userService.get(name, password);
        //判断返回的结果，如果不为空，则表示输入的账号密码 为true.
        if (u != null) {
            session.setAttribute("user", u);
            return "success";
        }
        return "fail";

    }


    //产品排序显示，通过接收 sort（排序关键字），判断排序的方式
    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
        Category c = categoryService.get(cid);
        //为当前分类c 设置产品信息
        productService.fill(c);
        //设置出售和评论总量
        productService.setSaleAndReviewNumber(c.getProducts());

        if (null != sort) {
            //排序
            switch (sort) {
                //通过评论总量排序
                case "review":
                    Collections.sort(c.getProducts(), new ProductReviewComparator());
                    break;
                //通过创建的日期排序
                case "date":
                    Collections.sort(c.getProducts(), new ProductDateComparator());
                    break;
                //通过销售量来排序
                case "saleCount":
                    Collections.sort(c.getProducts(), new ProductSaleCountComparator());
                    break;
                //通过价格排序
                case "price":
                    Collections.sort(c.getProducts(), new ProductPriceComparator());
                    break;
                //综合排序。
                case "all":
                    Collections.sort(c.getProducts(), new ProductAllComparator());
                    break;
            }
        }

        model.addAttribute("c", c);
        return "fore/category";
    }


    //搜索框，通过产品名称进行关键字模糊查询。
    @RequestMapping("foresearch")
    public String search(String keyword, Model model, Page page) {
        //设置分页参数。
        PageHelper.offsetPage(page.getStart(), 20);
        //通过keyword 进行关键字查询
        List<Product> search = productService.search(keyword);
        productService.setSaleAndReviewNumber(search);
        model.addAttribute("ps", search);

        return "fore/searchResult";
    }

    //.购物车，对于购物车里面已经有该pid产品，则进行数量上的修改。购物车里面没有的 则进行添加新的orderItem。
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
        //获取该产品信息
        Product product = productService.get(pid);

        //该订单项的id
        int oiid = 0;

        //用于判断该产品在购物车里面是否已经有了。
        Boolean flag = false;

        //获取已经登录的用户信息
        User user = (User) session.getAttribute("user");

        //获取该用户购物车里面的订单项
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        //遍历，判断出 pid产品是否已经存在于该订单项中
        for (OrderItem oi : orderItems) {
            if (oi.getPid().intValue() == product.getId().intValue()) {
                //当订单项里面的pid == 传进来的pid 时,修改该订单项里面产品的数量
                oi.setNumber(oi.getNumber() + num);
                orderItemService.update(oi);
                flag = true;
                //该订单的id
                oiid = oi.getId();
                break;

            }

        }
        if (!flag) {
            //当不存在时，新建一个订单项。
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setPid(pid);
            orderItem.setUid(user.getId());
            orderItemService.add(orderItem);
            oiid = orderItem.getId();
        }
        return "redirect:forebuy?oiid=" + oiid;


    }


    //结算页面。oiid 是订单项的ID。"forebuy" 的数据来源于 1,"直接购买" 2，购物车结算购买（这时 ooid是一个数组） ，它们都要生成结算页面。
    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {

        ArrayList<OrderItem> ois = new ArrayList<>();
        float total = 0;

        //遍历订单项
        for (String str : oiid) {
            //转换为int
            int id = Integer.parseInt(str);
            OrderItem orderItem = orderItemService.get(id);
            //累加总金额。
            total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
            ois.add(orderItem);
        }

        //session可以将数据传递到多个页面，在后续的生成订单order会使用但是model 只能传递到指定的一个页面中。
        session.setAttribute("ois", ois);

        model.addAttribute("total", total);

        //跳转结算页面
        return "fore/buy";
    }


    //添加购物车，参数num（数量），pid（产品id）˝
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int num, int pid, HttpSession session) {
        Product product = productService.get(pid);
        //添加购物车前，有一个检验是否登录的机制。能到这里添加购物车说明已经登录。
        //获取用户信息。
        User user = (User) session.getAttribute("user");

        //获取该用户下的所有订单项。
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());

        //标记。用于 判断添加的产品是否有同类的产品。
        Boolean flag = false;

        for (OrderItem oi : orderItems) {
            //如果已经有同类的产品，则改变该产品在购物车的数量。
            if (oi.getId().intValue() == product.getId().intValue()) {
                //改变数量
                oi.setNumber(oi.getNumber() + num);
                //更新
                orderItemService.update(oi);
                //标记为true，说明已经有了同类产品
                flag = true;
                break;

            }

        }


        if (!flag) {
            //没有同类的产品，则增加购物车（订单项）。
            //新建购物车
            OrderItem orderItem = new OrderItem();
            //设置数量
            orderItem.setNumber(num);
            //设置产品id
            orderItem.setPid(pid);
            //设置用户
            orderItem.setUid(user.getId());
            orderItemService.add(orderItem);
        }
        return "success";
    }

    //查看购物车。
    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {
        //获取当前用户。
        User user = (User) session.getAttribute("user");
        //获取当前用户下的订单项（购物车）
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        model.addAttribute("ois", orderItems);

        return "fore/cart";
    }


    //获取当前用户购物车的数量。
    @RequestMapping("forecartNumber")
    @ResponseBody
    public String showCartTotalNumber(HttpSession session) {
        //获取当前登录用户信息
        User user = (User) session.getAttribute("user");

        // 为购物车🛒，显示商品数量
        int totalOrderItemNum = 0;
        //已经登录
        if (user != null) {
            List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
            for (OrderItem oi : orderItems) {
                //得到商品数量
                totalOrderItemNum += oi.getNumber();
            }
            //转换为String类型
            String total = String.valueOf(totalOrderItemNum);
            return total;
        }

        return "";
    }

    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Integer oiid, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //说明已经登录，可以实现删除
        if (user != null) {
            String flag = orderItemService.deleteByOrderItemId(oiid);
            if (flag == "success") {
                //删除成功
                return "success";

            }

        }
        //没有登录，则跳转到login 进行登录。
        // return "redirect:forelogin";
        return null;


    }

    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeNum(int pid, int num, Model model, HttpSession session) {
        //判断当前是否已经登录
        User user = (User) session.getAttribute("user");
        if (user == null)
            //没有登录，则返回false ,重新进行登录。
            return "fail";

        //根据，user获取orderitem
        List<OrderItem> orderItems = orderItemService.listByUser(user.getId());
        for (OrderItem oi : orderItems) {
            //找出pid 商品。
            if (oi.getProduct().getId().intValue() == pid) {
                //更新商品数量。
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return "success";
    }

    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session) {
        User user = (User) session.getAttribute("user");
        //订单号由当前时间+5位随机数
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) +
                RandomUtils.nextInt(100000);
        order.setOrderCode(orderCode);
        order.setUid(user.getId());
        //设置订单状态是待付款
        order.setStatus(OrderService.waitPay);

        order.setCreateDate(new Date());

        //获取session中的订单项（选中的购物车），在forebuy 中已经设置了
        List<OrderItem> ois = (List<OrderItem>) session.getAttribute("ois");

        //创建订单，更新订单项
        float total = orderService.add(order, ois);

        //进入支付页面
        return "redirect:forepay?oid=" + order.getId() + "&total=" + total;
    }


    //付款，直接跳转付款页面
    @RequestMapping("forepay")
    public String pay() {

        return "fore/pay";
    }

    //点击按钮"确认支付"，设置该订单状态为等待发货，创建付款时间，跳转 "您已成功付款" 页面
    //更新商品的仓库数量
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
        //获取当前订单信息
        Order order = orderService.get(oid);
        //修改订单的状态
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        //更新订单
        orderService.update(order);
        // Product product = new Product();

        //修改商品仓库数量 仓库数量=原数量-购买的数量
        //获取当前已经支付订单项里面的 商品信息
        List<OrderItem> orderItems = orderItemService.listByOid(order.getId());
        //遍历该订单下的订单项
        for (OrderItem oi : orderItems) {
            Product product = oi.getProduct();
//            //线程，同步产品的仓库数量。
//            Thread thread = new Thread() {
//                public void run() {
//                    synchronized (product) {
                        //获取原仓库数量
                        Integer stock = product.getStock();
                        //仓库数量=原数量-购买的数量
                        stock = stock - oi.getNumber();
                        //防止stock 为负数
                        stock = stock >= 0 ? stock : 0;
                        product.setStock(stock);
                        //更新
                        productService.update(product);

//
//                    }
//
//                }
//            };
//            thread.start();

        }

        model.addAttribute("o", order);
        return "fore/payed";
    }


    //我的订单
    @RequestMapping("forebought")
    public String bought(Model model, HttpSession session) {
        //获取当前用户
        User user = (User) session.getAttribute("user");
        //获取除去状态为"OrderService.delete" 的所有该用户下的所有的订单
        List os = orderService.list(user.getId(), OrderService.delete);
        orderItemService.fill(os);
        model.addAttribute("os", os);

        return "fore/bought";

    }

    //点击确认收货，显示当前收货商品
    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model, int oid) {
        Order order = orderService.get(oid);
        orderItemService.fill(order);
        model.addAttribute("o", order);
        return "fore/confirmPay";

    }


    //点击确认收货，修改当前商品状态为等待评论
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model, int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitReview);
        order.setConfirmDate(new Date());
        orderService.update(order);
        return "fore/orderConfirmed";
    }

    //删除指定oid的订单，同时修改订单的状态
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String orderDelete(int oid, HttpSession session) {
        Order order = orderService.get(oid);
        //修改订单状态为 delete
        order.setStatus(OrderService.delete);
        //更新数据库
        orderService.update(order);
        return "success";
    }

    //点击评论
    @RequestMapping("forereview")
    public String review(int oid, Model model) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p.getId());
        productService.setSaleAndReviewNumber(p);
        model.addAttribute("p", p);
        model.addAttribute("o", o);
        model.addAttribute("reviews", reviews);
        return "fore/review";

    }

    //提交评论
    @RequestMapping("foredoreview")
    public String doreview(Model model, HttpSession session, @RequestParam("oid") int oid, @RequestParam("pid") int pid, String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user = (User) session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setPid(pid);
        review.setCreateDate(new Date());
        review.setUid(user.getId());
        reviewService.add(review);

        return "redirect:forereview?oid=" + oid + "&showonly=true";
    }


}


