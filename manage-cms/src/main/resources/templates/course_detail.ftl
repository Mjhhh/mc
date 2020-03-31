<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="content-type" content="no-cache, must-revalidate"/>
    <title>MOCK--${courseBase.name}</title>
</head>
<body data-spy="scroll" data-target="#articleNavbar" data-offset="150">

<!-- 页面头部 -->
<!--#include virtual="/include/header.html"-->
<!--页面头部结束-->

<div id="body">
    <div class="article-banner">
        <div class="banner-bg"></div>
        <div class="banner-info">
            <div class="banner-left">
                <p class="tit">${courseBase.name}</p>
                <p class="pic"><span class="new-pic">特惠价格￥${(courseMarket.price)!""}</span> <span class="old-pic">原价￥${(courseMarket.priceOld)!""}</span></p>
                <p class="info">
                    <a href="http://ucenter.mock.com/#/learning/${courseBase.id}/0" target="_blank" v-if="learnstatus == 1" v-cloak>马上学习</a>
                    <a href="#" @click="addopencourse" v-if="learnstatus == 2" v-cloak>立即报名</a>
                    <a href="#" @click="buy" v-if="learnstatus == 3" v-cloak>立即购买</a>
                    <span><em>难度等级</em>
                    <#if courseBase.grade=='200001'>
                        低级
                    <#elseif courseBase.grade=='200002'>
                        中级
                    <#elseif courseBase.grade=='200003'>
                        高级
                    </#if>
                    </span>
                    <span><em>课程时长</em>2小时27分</span>
                    <span><em>评分</em>4.7分</span>
                    <span><em>授课模式</em>
                    <#if courseBase.studymodel=='201001'>
                        自由学习
                    <#elseif courseBase.studymodel=='201002'>
                        任务式学习
                    </#if>
                    </span>
                </p>
            </div>
            <div class="banner-rit">
                <p>
                    <a href="#">
<!--                        <#if (coursePic.pic)??>-->
<!--                        <img src="http://mjh0523.xyz/${coursePic.pic}" alt="" width="270" height="156">-->
<!--                        <#else>-->
<!--                        <img src="/static/img/widget-video.png" alt="">-->
<!--                        </#if>-->
                        <img src="/static/img/widget-video.png" alt="">
                    </a>
                </p>
                <p class="vid-act">
                    <span> <i class="i-heart"></i>收藏 23 </span> <span>分享 <i class="i-weixin"></i><i class="i-qq"></i></span>
                </p>
            </div>
        </div>
    </div>
    <div class="article-cont">
        <!--导航栏列表-->
        <div class="tit-list">
            <a href="javascript:;" id="articleClass" class="active">课程介绍</a>
            <a href="javascript:;" id="articleItem">目录</a>
            <a href="javascript:;" id="artcleAsk">问答</a>
            <a href="javascript:;" id="artcleNot">笔记</a>
            <a href="javascript:;" id="artcleCod">评价</a>
        </div>

        <div class="article-box">
            <!--课程介绍-->
            <div class="articleClass" style="display: block">
                <!--<div class="rit-title">评价</div>-->
                <div class="article-cont">
                    <div class="article-left-box">
                        <div class="content">
                            <div class="content-com suit">
                                <div class="title"><span>适用人群</span></div>
                                <div class="cont cktop">
                                    <div>
                                        <p>${(courseBase.users)!""}</p>
                                    </div>
                                    <span class="on-off">更多 <i class="i-chevron-bot"></i></span>
                                </div>
                            </div>
                            <div class="content-com course">
                                <div class="title"><span>课程制作</span></div>
                                <div class="cont">
                                    <div class="img-box"><img src="http://mjh0523.xyz/${courseTeacher.avatar}" alt=""></div>
                                    <div class="info-box">
                                        <p class="name">教学方：<em>${courseTeacher.name}</em></p>
                                        <!-- <p class="lab">高级前端开发工程师 10年开发经验</p>-->
                                        <p class="info">
                                            ${courseTeacher.description}
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div class="content-com about">
                                <div class="title"><span>课程介绍</span></div>
                                <div class="cont cktop">
                                    <div>
                                        <p>
                                            ${(courseBase.description)!""}
                                        </p>
                                    </div>
                                    <!--<span class="on-off">更多 <i class="i-chevron-bot"></i></span>-->
                                </div>
                            </div>
                            <div class="content-com prob">
                                <div class="title"><span>常见问题</span></div>
                                <div class="cont">
                                    <ul>
                                        <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> 我怎么开始自己的学习？</span>
                                            <div class="drop-down">
                                                <p>
                                                    免费课程点击加入学习队列即可开始学习观看，收费视频需要先下单购买，才能拥有观看权限！
                                                </p>
                                            </div>
                                        </li>
                                        <li class="item"><span class="on-off"><i class="i-chevron-bot"></i> 课程安排如何，我平时太忙了没空学习怎么办？</span>
                                            <div class="drop-down">
                                                <p>
                                                    课程安排灵活，采用的是点播的形式，有别于直播是，你可以任意安排自己的空闲时间来进行学习！
                                                </p>
                                            </div>
                                        </li>
                                        <li class="item"><span class="on-off"><i
                                                class="i-chevron-bot"></i> 我支付次课程之后会得到什么？</span>
                                            <div class="drop-down">
                                                <p>
                                                    你将会获得由资深讲师提供的自身经验与最新的行业专业知识，讲师将会致力于解决你在观看视频中遇到的各种疑问，相较于自学更加的节省您的时间！
                                                </p>
                                            </div>
                                        </li>
                                        <li class="item"><span class="on-off"><i
                                                class="i-chevron-bot"></i> 退款条例是如何规定的？</span>
                                            <div class="drop-down">
                                                <p>
                                                    一旦选择支付购买课程，你只能在一个小时内提出退款申请，逾期将关闭交易！
                                                </p>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>

<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
<!--                    <div class="article-right-box">-->
<!--                        <div class="about-teach">-->
<!--                            <div class="teach-info">-->
<!--                                &lt;!&ndash;机构logo&ndash;&gt;-->
<!--                                <img src="http://mjh0523.xyz/${mcCompany.logo}" width="40px" alt="">-->
<!--                                <p>${mcCompany.name}</p>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <ul class="tree-list"><li><p class="item-tt">好评度</p><span class="item-num">  93%  </span></li><li><p class="item-tt">课程数</p><span class="item-num js-item-num" data-num="39">39</span></li><li><p class="item-tt">学生数</p><span class="item-num js-item-num" data-num="364386">36万</span></li></ul>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <p><a href="#" class="courselist_link">TA的课程</a></p>-->
<!--                            </div>-->

<!--                            <p class="synopsis">${mcCompany.intro}</p>-->
<!--                        </div>-->
<!--&lt;!&ndash;                        <div class="learing-box">&ndash;&gt;-->
<!--&lt;!&ndash;                            <div class="tit">看过该课的同学也在看</div>&ndash;&gt;-->
<!--&lt;!&ndash;                            <div class="item-box">&ndash;&gt;-->
<!--&lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--&lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--&lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--&lt;!&ndash;                                            <p class="top-tit"><a href="">Linux 达人养成记</a></p>&ndash;&gt;-->
<!--&lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--&lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--&lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                        <a>Linux 达人养成记</a>&ndash;&gt;-->
<!--&lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--&lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--&lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--&lt;!&ndash;                                            <p class="top-tit"><a href="">Redis从入门到精通</a></p>&ndash;&gt;-->
<!--&lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--&lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--&lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                        <a>Redis从入门到精通</a>&ndash;&gt;-->
<!--&lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--&lt;!&ndash;                                </div>&ndash;&gt;-->

<!--&lt;!&ndash;                            </div>&ndash;&gt;-->
<!--&lt;!&ndash;                        </div>&ndash;&gt;-->
<!--                    </div>-->
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->

                </div>
            </div>
            <!--目录-->
            <div class="articleItem" style="display: none">
                <div class="article-cont-catalog">
                    <div class="article-left-box">
                        <div class="content">
                            <#if (teachplanNode.children)??>
                            <#list teachplanNode.children as firstNode>
                            <div class="item">
                                <div class="title act">
                                    <i class="i-chevron-top"></i>${firstNode.pname}<span class="time">${firstNode.timelength!""}</span>
                                </div>
                                <div class="about">
                                    ${firstNode.description!""}
                                </div>
                                <div class="drop-down" style="height: 260px;">
                                    <ul class="list-box">
                                        <#list firstNode.children as secondNode>
                                        <li>
                                            <a href="http://ucenter.mock.com/#/learning/${courseBase.id}/${secondNode.mediaId}">
                                                ${secondNode.pname}
                                            </a>
                                        </li>
                                        </#list>
                                    </ul>
                                </div>
                            </div>
                            </#list>
                            </#if>
                        </div>
                    </div>
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
<!--                    <div class="article-right-box">-->
<!--                        <div class="about-teach">-->
<!--                            <div class="teach-info">-->
<!--                                &lt;!&ndash;机构logo&ndash;&gt;-->
<!--                                <img src="http://mjh0523.xyz/${mcCompany.logo}" width="40px" alt="">-->
<!--                                <p>${mcCompany.name}</p>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <ul class="tree-list"><li><p class="item-tt">好评度</p><span class="item-num">  93%  </span></li><li><p class="item-tt">课程数</p><span class="item-num js-item-num" data-num="39">39</span></li><li><p class="item-tt">学生数</p><span class="item-num js-item-num" data-num="364386">36万</span></li></ul>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <p><a href="#" class="courselist_link">TA的课程</a></p>-->
<!--                            </div>-->

<!--                            <p class="synopsis">${mcCompany.intro}</p>-->
<!--                        </div>-->
<!--                        &lt;!&ndash;                        <div class="learing-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="tit">看过该课的同学也在看</div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="item-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Linux 达人养成记</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Linux 达人养成记</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Redis从入门到精通</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Redis从入门到精通</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->

<!--                        &lt;!&ndash;                            </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                        </div>&ndash;&gt;-->
<!--                    </div>-->
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
                </div>
            </div>
            <!--问答-->
            <div class="artcleAsk" style="display: none">
                <div class="article-cont-ask">
                    <div class="article-left-box">
                        <div class="content">
                            <div class="content-title">
                                <p><a class="all">全部</a><a>精选</a><a>我的</a></p>
                                <p>
                                    <a class="all">全部</a><span><a>1.1</a><a>1.2</a><a>1.3</a><a>1.4</a><a>1.5</a></span><a
                                        href="$" class="more">更多 <i class="i-chevron-bot"></i></a></p>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p><span>我来回答</span></p>
                                    <p>2017-3-20 <span><i></i>回答2</span><span><i></i>浏览2</span></p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，仔细观察微服务的内容和时间是很重要的。以下两个要点将会对任何微服务重构策略产生重大影响。 【最新 <i
                                            class="new">心跳347890</i> 的回答】</p>
                                    <p>2017-3-20 <span class="action-box"><span><i
                                            class="i-answer"></i>回答 2</span><span><i
                                            class="i-browse"></i>浏览 12</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，仔细观察微服务的内容和时间是很重要的。以下两个要点将会对任何微服务重构策略产生重大影响。 【最新 <i
                                            class="new">心跳347890</i> 的回答】</p>
                                    <p>2017-3-20 <span class="action-box"><span><i
                                            class="i-answer"></i>回答 2</span><span><i
                                            class="i-browse"></i>浏览 12</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，仔细观察微服务的内容和时间是很重要的。以下两个要点将会对任何微服务重构策略产生重大影响。 【最新 <i
                                            class="new">心跳347890</i> 的回答】</p>
                                    <p>2017-3-20 <span class="action-box"><span><i
                                            class="i-answer"></i>回答 2</span><span><i
                                            class="i-browse"></i>浏览 12</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，仔细观察微服务的内容和时间是很重要的。以下两个要点将会对任何微服务重构策略产生重大影响。 【最新 <i
                                            class="new">心跳347890</i> 的回答】</p>
                                    <p>2017-3-20 <span class="action-box"><span><i
                                            class="i-answer"></i>回答 2</span><span><i
                                            class="i-browse"></i>浏览 12</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p class="title">如何用微服务重构应用程序?</p>
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，仔细观察微服务的内容和时间是很重要的。以下两个要点将会对任何微服务重构策略产生重大影响。 【最新 <i
                                            class="new">心跳347890</i> 的回答】</p>
                                    <p>2017-3-20 <span class="action-box"><span><i
                                            class="i-answer"></i>回答 2</span><span><i
                                            class="i-browse"></i>浏览 12</span></span>
                                    </p>
                                </div>
                            </div>

                            <div class="itemlast">
                                <a href="#" class="overwrite">显示更多问题</a>
                            </div>
                        </div>
                    </div>
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
<!--                    <div class="article-right-box">-->
<!--                        <div class="about-teach">-->
<!--                            <div class="teach-info">-->
<!--                                &lt;!&ndash;机构logo&ndash;&gt;-->
<!--                                <img src="http://mjh0523.xyz/${mcCompany.logo}" width="40px" alt="">-->
<!--                                <p>${mcCompany.name}</p>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <ul class="tree-list"><li><p class="item-tt">好评度</p><span class="item-num">  93%  </span></li><li><p class="item-tt">课程数</p><span class="item-num js-item-num" data-num="39">39</span></li><li><p class="item-tt">学生数</p><span class="item-num js-item-num" data-num="364386">36万</span></li></ul>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <p><a href="#" class="courselist_link">TA的课程</a></p>-->
<!--                            </div>-->

<!--                            <p class="synopsis">${mcCompany.intro}</p>-->
<!--                        </div>-->
<!--                        &lt;!&ndash;                        <div class="learing-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="tit">看过该课的同学也在看</div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="item-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Linux 达人养成记</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Linux 达人养成记</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Redis从入门到精通</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Redis从入门到精通</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->

<!--                        &lt;!&ndash;                            </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                        </div>&ndash;&gt;-->
<!--                    </div>-->
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
                </div>
            </div>
            <!--笔记-->
            <div class="artcleNot" style="display: none;">
                <div class="article-cont-note">
                    <div class="article-left-box">
                        <div class="content">
                            <div class="content-title">
                                <p><a class="all">全部</a><a>精选</a><a>我的</a></p>
                                <p>
                                    <a class="all">全部</a><span><a>1.1</a><a>1.2</a><a>1.3</a><a>1.4</a><a>1.5</a></span><a
                                        href="$" class="more">更多 <i class="i-chevron-bot"></i></a></p>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <span class="video-time"><i class="i-play"></i>2`10`</span>
                                    <p><img src="/static/img/widget-demo.png" width="221" alt=""></p>
                                    <p class="action-box">4小时前 <span class="active-box"><span><i
                                            class="i-coll"></i>采集</span><span><i class="i-laud"></i>赞</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，<br>仔细观察微服务的内容和时间是很重要的。<br>以下两个要点将会对任何微服务重构策略产生重大影响。 </p>
                                    <p class="action-box">4小时前 <span class="active-box"><span><i
                                            class="i-edt"></i>编辑</span><span><i
                                            class="i-del"></i>删除</span><span><i class="i-laud"></i>赞</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，<br>仔细观察微服务的内容和时间是很重要的。<br>以下两个要点将会对任何微服务重构策略产生重大影响。 </p>
                                    <p class="action-box">4小时前 <span class="active-box"><span><i
                                            class="i-edt"></i>编辑</span><span><i
                                            class="i-del"></i>删除</span><span><i class="i-laud"></i>赞</span></span>
                                    </p>
                                </div>
                            </div>
                            <div class="item">
                                <div class="item-left">
                                    <p><img src="/static/img/widget-myImg.jpg" width="60px" alt=""></p>
                                    <p>毛老师</p>
                                </div>
                                <div class="item-right">
                                    <p>在讨论如何将重构转化为微服务之前，退后一步，<br>仔细观察微服务的内容和时间是很重要的。<br>以下两个要点将会对任何微服务重构策略产生重大影响。 </p>
                                    <p class="action-box">4小时前 <span class="active-box"><span><i
                                            class="i-edt"></i>编辑</span><span><i
                                            class="i-del"></i>删除</span><span><i class="i-laud"></i>赞</span></span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
<!--                    <div class="article-right-box">-->
<!--                        <div class="about-teach">-->
<!--                            <div class="teach-info">-->
<!--                                &lt;!&ndash;机构logo&ndash;&gt;-->
<!--                                <img src="http://mjh0523.xyz/${mcCompany.logo}" width="40px" alt="">-->
<!--                                <p>${mcCompany.name}</p>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <ul class="tree-list"><li><p class="item-tt">好评度</p><span class="item-num">  93%  </span></li><li><p class="item-tt">课程数</p><span class="item-num js-item-num" data-num="39">39</span></li><li><p class="item-tt">学生数</p><span class="item-num js-item-num" data-num="364386">36万</span></li></ul>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <p><a href="#" class="courselist_link">TA的课程</a></p>-->
<!--                            </div>-->

<!--                            <p class="synopsis">${mcCompany.intro}</p>-->
<!--                        </div>-->
<!--                        &lt;!&ndash;                        <div class="learing-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="tit">看过该课的同学也在看</div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="item-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Linux 达人养成记</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Linux 达人养成记</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Redis从入门到精通</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Redis从入门到精通</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->

<!--                        &lt;!&ndash;                            </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                        </div>&ndash;&gt;-->
<!--                    </div>-->
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
                </div>
            </div>
            <!--评价-->
            <div class="artcleCod" style="display: none;">
                <div class="article-cont">
                    <div class="article-left-box">
                        <div class="comment-box">
                            <div class="evaluate">
                                <div class="eva-top">
                                    <div class="tit">课程评分</div>
                                    <div class="star">
                                        <div class="score"><i>5</i></div>
                                    </div>
                                    <span class="star-score"> <i>5</i> 分</span></div>
                                <div class="eva-cont">
                                    <div class="tit">学员评语</div>
                                    <div class="text-box">
                                    <textarea class="form-control" rows="5"
                                              placeholder="扯淡、吐槽、表扬、鼓励......想说啥说啥！"></textarea>
                                        <div class="text-right"><span>发表评论</span></div>
                                    </div>
                                </div>
                            </div>
                            <div class="course-evaluate">
                                <div class="top-tit">评论
                                    <span>
                        <label><input name="eval" type="radio" value="" checked/> 所有学生 </label>
                        <label><input name="eval" type="radio" value=""/> 完成者 </label>
                    </span>
                                </div>
                                <div class="top-cont">
                                    <div class="cont-top-left">
                                        <div class="star-scor">
                                            <div class="star-show">
                                                <div class="score"><i>5</i></div>
                                            </div>
                                            <div class="scor">4.9分</div>
                                        </div>
                                        <div class="all-scor">总评分：12343</div>
                                    </div>
                                    <div class="cont-top-right">
                                        <div class="star-grade">五星
                                            <div class="grade">
                                                <div class="grade-percent"><span></span></div>
                                                <div class="percent-num"><i>95</i>%</div>
                                            </div>
                                        </div>
                                        <div class="star-grade">四星
                                            <div class="grade">
                                                <div class="grade-percent"><span></span></div>
                                                <div class="percent-num"><i>5</i>%</div>
                                            </div>
                                        </div>
                                        <div class="star-grade">三星
                                            <div class="grade">
                                                <div class="grade-percent"><span></span></div>
                                                <div class="percent-num"><i>0</i>%</div>
                                            </div>
                                        </div>
                                        <div class="star-grade">二星
                                            <div class="grade">
                                                <div class="grade-percent"><span></span></div>
                                                <div class="percent-num"><i>2</i>%</div>
                                            </div>
                                        </div>
                                        <div class="star-grade">一星
                                            <div class="grade">
                                                <div class="grade-percent"><span></span></div>
                                                <div class="percent-num"><i>1</i>%</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="comment-item-box">
                                    <div class="title">评论 <span>12453条评论</span></div>
                                    <div class="item">
                                        <div class="item-left">
                                            <p><img src="/static/img/widget-pic.png" width="60px" alt=""></p>
                                            <p>毛老师</p>
                                        </div>
                                        <div class="item-cent">
                                            <p>
                                                很受用，如果再深入下就更好了。虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！</p>
                                            <p class="time">2017-2-43</p>
                                        </div>
                                        <div class="item-rit">
                                            <p>
                                            <div class="star-show">
                                                <div class="score"><i>4</i></div>
                                            </div>
                                            </p>
                                            <p>评分 <span>5星</span></p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="item-left">
                                            <p><img src="/static/img/widget-pic.png" width="60px" alt=""></p>
                                            <p>毛老师</p>
                                        </div>
                                        <div class="item-cent">
                                            <p>
                                                很受用，如果再深入下就更好了。虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！</p>
                                            <p class="time">2017-2-43</p>
                                        </div>
                                        <div class="item-rit">
                                            <p>
                                            <div class="star-show">
                                                <div class="score"><i>5</i></div>
                                            </div>
                                            </p>
                                            <p>评分 <span>5星</span></p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="item-left">
                                            <p><img src="/static/img/widget-pic.png" width="60px" alt=""></p>
                                            <p>毛老师</p>
                                        </div>
                                        <div class="item-cent">
                                            <p>
                                                很受用，如果再深入下就更好了。虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！</p>
                                            <p class="time">2017-2-43</p>
                                        </div>
                                        <div class="item-rit">
                                            <p>
                                            <div class="star-show">
                                                <div class="score"><i>5</i></div>
                                            </div>
                                            </p>
                                            <p>评分 <span>5星</span></p>
                                        </div>
                                    </div>
                                    <div class="item">
                                        <div class="item-left">
                                            <p><img src="/static/img/widget-pic.png" width="60px" alt=""></p>
                                            <p>毛老师</p>
                                        </div>
                                        <div class="item-cent">
                                            <p>
                                                很受用，如果再深入下就更好了。虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！虽然都是入门级别的，但是也很使用，后续就需要自己发挥了！</p>
                                            <p class="time">2017-2-43</p>
                                        </div>
                                        <div class="item-rit">
                                            <p>
                                            <div class="star-show">
                                                <div class="score"><i>5</i></div>
                                            </div>
                                            </p>
                                            <p>评分 <span>5星</span></p>
                                        </div>
                                    </div>
                                    <div class="get-more">页面加载中...</div>
                                </div>
                            </div>
                        </div>
                    </div>
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
<!--                    <div class="article-right-box">-->
<!--                        <div class="about-teach">-->
<!--                            <div class="teach-info">-->
<!--                                &lt;!&ndash;机构logo&ndash;&gt;-->
<!--                                <img src="http://mjh0523.xyz/${mcCompany.logo}" width="40px" alt="">-->
<!--                                <p>${mcCompany.name}</p>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <ul class="tree-list"><li><p class="item-tt">好评度</p><span class="item-num">  93%  </span></li><li><p class="item-tt">课程数</p><span class="item-num js-item-num" data-num="39">39</span></li><li><p class="item-tt">学生数</p><span class="item-num js-item-num" data-num="364386">36万</span></li></ul>-->
<!--                            </div>-->
<!--                            <div class="teach-info">-->
<!--                                <p><a href="#" class="courselist_link">TA的课程</a></p>-->
<!--                            </div>-->

<!--                            <p class="synopsis">${mcCompany.intro}</p>-->
<!--                        </div>-->
<!--                        &lt;!&ndash;                        <div class="learing-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="tit">看过该课的同学也在看</div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                            <div class="item-box">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Linux 达人养成记</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Linux 达人养成记</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                <div class="item-list hov" >&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    <div class="infobox">&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <div class="morebox" style="background: url(/img/widget-titBg.png) no-repeat;">&ndash;&gt;-->

<!--                        &lt;!&ndash;                                            <p class="top-tit"><a href="">Redis从入门到精通</a></p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-lab">传智播客</p>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                            <p class="top-num">2589646次播放<span>4.8分</span></p>&ndash;&gt;-->

<!--                        &lt;!&ndash;                                        </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                        <a>Redis从入门到精通</a>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                    </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                                </div>&ndash;&gt;-->

<!--                        &lt;!&ndash;                            </div>&ndash;&gt;-->
<!--                        &lt;!&ndash;                        </div>&ndash;&gt;-->
<!--                    </div>-->
<!--                    &lt;!&ndash;侧边栏&ndash;&gt;-->
                </div>
            </div>
        </div>
    </div>

    <div class="popup-box" style="display: none">
        <div class="mask"></div>
        <!--欢迎访问课程弹窗- start -->
<!--        <div class="popup-course-box">-->
<!--            <div class="title">程序设计语言 <span class="close-popup">×</span></div>-->
<!--            <div class="content">-->
<!--                <p>欢迎学习本课程，您现在可以访问课程材料了。</p>-->
<!--                <p><a href="#">开始学习</a></p>-->
<!--            </div>-->
<!--        </div>-->
        <!--欢迎访问课程弹窗- end -->

        <!--支付弹窗- start -->
        <div class="popup-pay-box" style="width: 50%">
            <div class="title">${courseBase.name} <span class="close-popup">×</span></div>
            <div class="content">
                <img src="./text.png" alt="">
                <div class="info">
                    <p class="info-tit">${courseBase.name} <span>课程有效期:${courseMarket.startTime}-${courseMarket.endTime}</span></p>
                    <p class="info-pic">课程价格 : <span>￥${courseMarket.priceOld}</span></p>
                    <p class="info-new-pic">优惠价格 : <span>￥${courseMarket.price}</span></p>
                </div>
            </div>
            <div class="fact-pic">实际支付: <span>￥${courseMarket.price}</span></div>
            <div class="go-pay">
                <span style="cursor: pointer" @click="alipay"><img src="/static/img/alipay.png" alt=""></span>
                <span style="cursor: pointer"><img src="/static/img/webchat.png" alt=""></span>
            </div>
        </div>
        <!--支付弹窗- end -->
        <div class="popup-comment-box">

        </div>
    </div>

    <!-- 页面底部 -->
    <!--底部版权-->
    <!--#include virtual="/include/footer.html"-->
    <!--底部版权-->
</div>

<!-- 页面 css js -->
<!--#include virtual="/include/course_detail_dynamic.html"-->
</body>
<script>var courseId = "${courseBase.id}"</script>