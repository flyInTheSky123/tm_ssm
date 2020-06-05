<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2020-02-19
  Time: 18:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%--$(".class") class选择器--%>
<script>

    $(function () {
        //stock 是存储数量
        var stock = ${p.stock};
        //判断当前商品的库存数量是否为0
        if (stock==0){
            //库存为空时，不再出售
            $(".addCartButton").html("哇，已经卖完！");
            $(".addCartButton").attr("disabled", "disabled");
            $(".addCartButton").css("background-color", "lightgray")
            $(".addCartButton").css("border-color", "lightgray")
            $(".addCartButton").css("color", "black")

            $(".buyButton").hide();



        }



        $(".productNumberSetting").keyup(function () {
            var num = $(".productNumberSetting").val();
            num = parseInt(num);
            if (isNaN(num))
                num = 1;
            if (num <= 0)
                num = 1;
            if (num > stock)
                num = stock;
            $(".productNumberSetting").val(num);
        });

        //点击 ^ ，则增加数量
        $(".increaseNumber").click(function () {
            //获取数量
            var num = $(".productNumberSetting").val();
            //自动加一
            num++;
            //如果增加的数量超过了 存储量，则num =存储量。
            if (num > stock)
                num = stock;
            //赋值
            $(".productNumberSetting").val(num);
        });
        //点击 减少按钮，则减少数量
        $(".decreaseNumber").click(function () {
            var num = $(".productNumberSetting").val();
            --num;
            if (num <= 0)
                num = 1;
            $(".productNumberSetting").val(num);
        });

        //将当前产品添加到 购物车 🛒
        $(".addCartLink").click(function () {
            //通过JQuery的.get方法，用异步ajax的方式访问forecheckLogin，获取当前是否登录状态
            var page = "forecheckLogin";
            $.get(
                page,
                function (result) {
                    if ("success" == result) {

                        //获取当前产品的id，数量
                        var pid = ${p.id};
                        var num = $(".productNumberSetting").val();
                        //购物车控制器地址
                        var addCartpage = "foreaddCart";
                        $.get(
                            addCartpage,
                            {"pid": pid, "num": num},
                            function (result) {
                                if ("success" == result) {
                                    $(".addCartButton").html("已加入购物车");
                                    $(".addCartButton").attr("disabled", "disabled");
                                    $(".addCartButton").css("background-color", "lightgray")
                                    $(".addCartButton").css("border-color", "lightgray")
                                    $(".addCartButton").css("color", "black")
                                    //当 当前商品添加购物车成功后，在右上角动态的显示当前用户的购物车数量
                                    var numberPage = "forecartNumber";
                                    var total;
                                    $.get(
                                        numberPage,
                                        function (result) {
                                            if (result != null) {
                                                total = result;
                                                $("#total").html(total);
                                            } else {

                                            }
                                        }
                                    )

                                } else {

                                }
                            }
                        );
                    } else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });

        $(".buyLink").click(function () {
            //通过JQuery的.get方法，用异步ajax的方式访问forecheckLogin，获取当前是否登录状态
            var page = "forecheckLogin";

            $.get(
                page,
                function (result) {
                    //返回success 时，表示是登录状态。
                    if ("success" == result) {
                        //已经登录，为地址后面加上num 数量。
                        var num = $(".productNumberSetting").val();
                        location.href = $(".buyLink").attr("href") + "&num=" + num;
                    }
                    //否则，弹出登录窗口
                    else {
                        $("#loginModal").modal('show');
                    }
                }
            );
            return false;
        });

        $("button.loginSubmitButton").click(function () {
            var name = $("#name").val();
            var password = $("#password").val();

            if (0 == name.length || 0 == password.length) {
                $("span.errorMessage").html("请输入账号密码");
                $("div.loginErrorMessageDiv").show();
                return false;
            }

            var page = "foreloginAjax";
            $.post(
                page,
                {"name": name, "password": password},
                function (result) {
                    if ("success" == result) {
                        location.reload();
                    } else {
                        $("span.errorMessage").html("账号密码错误");
                        $("div.loginErrorMessageDiv").show();
                    }
                }
            );

            return true;
        });

        $("img.smallImage").mouseenter(function () {
            var bigImageURL = $(this).attr("bigImageURL");
            $("img.bigImg").attr("src", bigImageURL);
        });

        $("img.bigImg").load(
            function () {
                $("img.smallImage").each(function () {
                    var bigImageURL = $(this).attr("bigImageURL");
                    img = new Image();
                    img.src = bigImageURL;

                    img.onload = function () {
                        console.log(bigImageURL);
                        $("div.img4load").append($(img));
                    };
                });
            }
        );
    });

</script>

<div class="imgAndInfo">

    <%--左侧显示5张单个图片图片--%>
    <div class="imgInimgAndInfo">
        <%--默认显示第一张图片--%>
        <img src="img/productSingle/${p.productFirstImage.id}.jpg" class="bigImg">
        <div class="smallImageDiv">
            <%--循环遍历单个图片--%>
            <c:forEach items="${p.productSingleImages}" var="pi">
                <img src="img/productSingle_small/${pi.id}.jpg" bigImageURL="img/productSingle/${pi.id}.jpg"
                     class="smallImage">
            </c:forEach>
        </div>
        <div class="img4load hidden"></div>
    </div>

    <div class="infoInimgAndInfo">
        <%--设置title--%>
        <div class="productTitle">
            ${p.name}
        </div>
        <%--设置副标题--%>
        <div class="productSubTitle">
            ${p.subTitle}
        </div>

        <div class="productPrice">
            <div class="juhuasuan">
                <span class="juhuasuanBig">聚划算</span>
                <span>此商品即将参加聚划算，<span class="juhuasuanTime">1天19小时</span>后开始，</span>
            </div>
            <div class="productPriceDiv">
                <%--图片购物卷--%>
                <div class="gouwujuanDiv"><img height="16px" src="img/site/gouwujuan.png">
                    <span> 实物商品通用</span>

                </div>
                <div class="originalDiv">
                    <span class="originalPriceDesc">价格</span>
                    <span class="originalPriceYuan">¥</span>
                    <span class="originalPrice">${p.originalPrice}

                        <fmt:formatNumber type="number" value="${p.originalPrice}" minFractionDigits="2"/>
                    </span>
                </div>
                <div class="promotionDiv">
                    <span class="promotionPriceDesc">促销价 </span>
                    <span class="promotionPriceYuan">¥</span>
                    <span class="promotionPrice">
                        <fmt:formatNumber type="number" value="${p.promotePrice}" minFractionDigits="2"/>
                    </span>
                </div>
            </div>
        </div>
        <div class="productSaleAndReviewNumber">
            <div>销量 <span class="redColor boldWord"> ${p.saleCount }</span></div>
            <div>累计评价 <span class="redColor boldWord"> ${p.reviewCount}</span></div>
        </div>
        <div class="productNumber">
            <span>数量</span>
            <span>
                <span class="productNumberSettingSpan">
                <input class="productNumberSetting" type="text" value="1">
                </span>
                <span class="arrow">
                    <a href="#nowhere" class="increaseNumber">
                    <span class="updown">
                            <img src="img/site/increase.png">
                    </span>
                    </a>

                    <span class="updownMiddle"> </span>
                    <a href="#nowhere" class="decreaseNumber">
                    <span class="updown">
                            <img src="img/site/decrease.png">
                    </span>
                    </a>

                </span>

            件</span>

            <span class="stock">库存${p.stock}件</span>
        </div>
        <div class="serviceCommitment">
            <span class="serviceCommitmentDesc">服务承诺</span>
            <span class="serviceCommitmentLink">
                <a href="#nowhere">正品保证</a>
                <a href="#nowhere">极速退款</a>
                <a href="#nowhere">赠运费险</a>
                <a href="#nowhere">七天无理由退换</a>
            </span>
        </div>

        <div class="buyDiv">
            <a class="buyLink" href="forebuyone?pid=${p.id}">
                <button class="buyButton">立即购买</button>
            </a>
            <a href="#nowhere" class="addCartLink">
                <button class="addCartButton">
                    <span class="glyphicon glyphicon-shopping-cart"></span>加入购物车
                </button>
            </a>
        </div>
    </div>

    <div style="clear:both"></div>

</div>
