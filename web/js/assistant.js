//S    创建显示消息的方法
function showMessage(a, b, c) {
//a为显示的对话，b停留时间，默认为5秒，c消失时间，默认为5秒
    if (b == null) b = 5000;
    if (c == null) c = 5000;
//去除消息框隐藏
    jQuery("#message").hide().stop();
//添加输出信息到标签后的html代码中
    jQuery("#message").html(a);
//淡入
    jQuery("#message").fadeIn(300);

//延时5秒执行淡出
    function d() {
        jQuery("#message").fadeOut(c);
//释放延时资源
        clearTimeout(d);
    }

    window.setTimeout(d, b);
};
//E    创建显示消息的方法

//S    拖动
var _move = false;
var ismove = false;
var _x, _y;//鼠标距离左上角的位置
jQuery(document).ready(function ($) {
    $("#assistant").mousedown(function (e) {
        _move = true;
//获取当前对象距离浏览器左上角的位置
        _x = e.pageX - parseInt($("#assistant").css("left"));
        _y = e.pageY - parseInt($("#assistant").css("top"));
    });
    $(document).mousemove(function (e) {
        if (_move) {
            var x = e.pageX - _x;
            var y = e.pageY - _y;
//限制移动空间在屏幕内
            var wx = $(window).width() - $("#assistant").width();
            var dy = $(document).height() - $("#assistant").height();
            if (x >= 0 && x <= wx && y > 0 && y <= dy) {
//控制CSS移动控件到新位置
                $("#assistant").css({
                    top: y, left: x
                });
            }
            ismove = true;
        }
    }).mouseup(function () {
        _move = false;
    });
});
//E    拖动

//S 开始
jQuery(document).ready(function ($) {
    if (isindex) {
//如果是主页
        var now = (new Date()).getHours();
        if (now > 0 && now <= 6) {
            showMessage(visitor + ' 你是夜猫子呀？还不睡觉，明天起的来么你？', 6000, 600);
        } else if (now > 6 && now <= 11) {
            showMessage(visitor + ' 早上好，早起的鸟儿有虫吃噢！早起的虫儿被鸟吃，你是鸟儿还是虫儿？嘻嘻！', 6000, 600);
        } else if (now > 11 && now <= 14) {
            showMessage(visitor + ' 中午了，吃饭了么？不要饿着了，饿死了谁来挺我呀！', 6000, 600);
        } else if (now > 14 && now <= 18) {
            showMessage(visitor + ' 下午的时光真难熬！还好有你在！', 6000, 600);
        } else {
            showMessage(visitor + ' 你晚饭吃饱了吗？我好无聊啊，快来逗我玩~~', 6000, 600);
        }
    } else {
//visitor登录的帐号的姓名,title当前页面的标题
        showMessage('(●\'◡\'●)欢迎' + visitor + '来到<span style="color:#0099cc;">嗖嗖移动</span> ' + title + ' ', 6000, 600);
    }
    $("#assistant").animate({
            top: $(window).height()-200,
            left: $(window).width()-160
        },
        {
            queue: false,
            duration: 1000
        });
});
//E 开始

//S    鼠标点击事件
jQuery(document).ready(function ($) {
    var stat_click = 0;
    var impolite = 0;
    var girl = $("#assistant_girl");
    girl.click(function () {
        if (!ismove) {
            stat_click++;
            var msgs;
            if (stat_click > 6) {
//调戏助理过分了
                msgs = [" ( >﹏<。)你有完没完呀",
                    "（//▽//）你已经摸了我" + stat_click + "次了，人家都脸红了...",
                    "(>_<、)非礼呀，救命！！！",
                    "( ∙̆ .̯ ∙̆ )求你了，别摸我了，我都脸红了！",
                    "::(>_<)::臭色狼~~~走开啦~~",
                    "我讨厌你，哇哇哇哇~~"];

            } else {
//调戏助理行动开始
                msgs = ["我闪~~",
                    "筋斗云！~走你！",
                    "我飞呀~飞呀~飞...",
                    "惹不起你，我还躲不起你么",
                    "不要摸我了，再摸...我...我就告诉主人打你哟!",
                    "(>_<、)干嘛动我，小心我咬你！"];
            }
//随即获取消息组中的一条调用消息方法回复
            var i = Math.floor(Math.random() * msgs.length);
//移动速度随机数组
            s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -0.75];
            var i1 = Math.floor(Math.random() * s.length);
            var i2 = Math.floor(Math.random() * s.length);
            $("#assistant").animate({
                    left: document.body.offsetWidth / 2 * (1 + s[i1]),
                    top: document.body.offsetheight / 2 * (1 + s[i2]),
                },
                {
//移动速度，越小越快，三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000)
                    duration: 500,
                    callback: showMessage(msgs[i], 8000, 1000),
                });
        } else {
            ismove = false;
        }
    });
});
//E    鼠标点击事件

//S 滚动条移动
jQuery(document).ready(function ($) {
    //滚动前坐标相对顶坐标
    var f = $("#assistant").offset().top + $("#assistant").height();
    $(window).scroll(function () {
        showMessage('等等我~~~', 3000, 1000);
        //滚动后坐标相对顶坐标
        $("#assistant").animate({
                top: $(window).scrollTop() + f,
            },
            {
                queue: false,//指示是否在效果队列中放置动画。如果为 false，则动画将立即开始，强制优先执行
                duration: 1000,
            });
    });

});
//E 滚动条移动

//S 窗口大小改变
jQuery(document).ready(function($){
	$(window).resize(function(){				
		if($(window).height()<$("#assistant").offset().top+200){
			//超出了屏幕下方
			showMessage('我会很快适应新的环境大小哟~~~', 5000, 1000);
			$("#assistant").animate({
                top: $(window).height()-200,
            },
            {
                queue: false,//指示是否在效果队列中放置动画。如果为 false，则动画将立即开始，强制优先执行
                duration: 1000,
            });
		}
		if($(window).width()<$("#assistant").offset().left+160){
			//超出了屏幕右方
			showMessage('我会很快适应新的环境大小哟~~~', 5000, 1000);
			$("#assistant").animate({
                left: $(window).width()-160,
            },
            {
                queue: false,//指示是否在效果队列中放置动画。如果为 false，则动画将立即开始，强制优先执行
                duration: 1000,
            });
		}		
	});
});

//E 窗口大小改变

//S 注册帐号
jQuery(document).ready(function ($) {

    $("#reg-user").click(function () {
        showMessage("这些号码还没被注册："+"<br/>139 1117 5430<br/>139 1185 8395<br/>139 1186 8388<br/>139 1218 4734<br/>139 1225 6335<br/>139 1236 7972<br/>139 1268 7205<br/>139 1346 9933<br/>139 1395 3756",15000,1000);
//助理移动值填写的位置，下同		
        $("#assistant").animate({
			top: document.getElementById("reg-user").offsetTop + 100,
			left:document.getElementById("reg-user").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
            });
    });

    $("#reg-password").click(function () {
        showMessage("我绝对不会告诉别人你刚才输入的是你的生日，嘻嘻~（＾_＾）",15000,1000);
        $("#assistant").animate({
 			top: document.getElementById("reg-password").offsetTop + 100,
			left:document.getElementById("reg-password").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
            });
    });

    $("#reg-name").click(function () {
        showMessage("请在这里留下你的大名",15000,1000);
        $("#assistant").animate({
   			top: document.getElementById("reg-name").offsetTop + 100,
			left:document.getElementById("reg-name").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
            });
    });

	$("#reg-combo").click(function () {		
        showMessage("话痨套餐：<br/>月租58元<br/>500分钟通话<br/>30条短信<br/><br/>网虫套餐：<br/>月租68元<br/>3GB流量<br/><br/>超人套餐：<br/>月租78元<br/>200分钟通话<br/>1GB流量<br/>50条短信<br/><br/>请认真输入套餐，我不会免费给你第二次机会修改",20000,10000);
        $("#assistant").animate({
 			top: document.getElementById("reg-combo").offsetTop,
			left:document.getElementById("reg-combo").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
            });
    });

    $("#reg-money").click(function () {
        showMessage("总感觉不够第一个月的话费哦，让我数数哈，1元，2元，3元...zzZZZ",15000,1000);
        $("#assistant").animate({
  			top: document.getElementById("reg-money").offsetTop + 100,
			left:document.getElementById("reg-money").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
        });
    });

    $("#reg-email").click(function () {
        showMessage("留下你的邮箱，不然你的密码忘了可就没办法啦╮(╯▽╰)╭",15000,1000);
        $("#assistant").animate({
 			top: document.getElementById("reg-email").offsetTop,
			left:document.getElementById("reg-email").offsetLeft - 170
            },
            {
                queue: false,
                duration: 1000
            });
    });
 
});
//E 注册帐号

//S 无聊的助理在随机移动
jQuery(document).ready(function ($) {
    window.setInterval(function () {
        msgs = ["只有您的支持，嗖嗖移动才能有更多的收入、才能提供更优质的通信服务、才能让我拿到更多的收入来填饱肚子！",
            "我悄悄的走过来！",
            "我飘过来了！~",
            "我飘过去了",
            "我得意地飘！~飘！~",
            "飘啊~~",
            "这里发生了什么，快来看看",
            "好无聊啊，我们来玩捉迷藏",
            "如果你能追到我，我就让你，嘿！嘿！嘿！"];
        var i = Math.floor(Math.random() * msgs.length);
        s = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.75, -0.1, -0.2, -0.3, -0.4, -0.5, -0.6, -0.7, -0.75];
        var i1 = Math.floor(Math.random() * s.length);        
        var i2 = Math.floor(Math.random() * s.length);
		var j1=document.body.offsetWidth / 2 * (1 + s[i1]);
		var j2=document.body.offsetHeight / 2 * (1 + s[i2]);
		//防止自动移出屏幕
		if(j1<10) j1=10;
		if(j1>$(window).width) j1=$(window).width-160;
		if(j2<50) j2=50;
		if(j2>$(window).top) j2=$(window).top-200;
        $("#assistant").animate({
                left: j1,
                top: j2
            },
            {
                duration: 2000,
                callback: showMessage(msgs[i], 6000, 5000)
            });
    }, 30000);
});
//E 无聊的助理在随机移动

//S 无聊的助理在自言自语
jQuery(document).ready(function ($) {
    window.setInterval(function () {
        msgs = ["只有您的支持，嗖嗖移动才能有更多的收入、才能提供更优质的通信服务、才能让我拿到更多的收入来填饱肚子！",
            "好无聊哦，你都不陪我玩~~哼",
            "zzZZZ，zzZZZ...",
            "…@……!………",
            "^%#&*!@*(&#)(!)(",
            "我是嗖嗖移动的小小助理哦~~",
            "^_^我可爱吧！嘻嘻!~~~",
            "我可是个“炒鸡AI”哟，我可比那个叫“绊爱(きずな)”的智障要强好多呢~嘻嘻~(*^__^*)",
            "从前有座山，山上有座庙，庙里有个老和尚给小和尚讲故事，讲：“从前有座……”"];
        var i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i], 10000, 1000);
    }, 35000);
});
//E 无聊的助理在自言自语


//S 鼠标在某些元素上方时的提示功能
jQuery(document).ready(function ($) {
    $('#reg-btn').click(function () {
//div按钮的超链接被点击时，“注册”按钮
        showMessage('正在用吃奶的劲帮你把注册信息送到服务器中呢，请稍候...');
    });
    $('#reg-btn').mouseover(function () {
        showMessage('要再看一看吗，我跑的可快呢？');
    });
    $('#add-money').mouseover(function () {
        showMessage('要充值话费吗?');
    });
    $('#comboinfo').mouseover(function () {
        showMessage('我们的套餐是针对特殊人群分为的3种');
    });
//这里到时候在标签里绑定选择的选项的结果
    $('#combo-change').mouseover(function () {
        showMessage('确定要更换为' + $(this).text() + '套餐了么？');
    });
    $('#go-home').mouseover(function () {
        showMessage('点它就可以回到大厅啦！');
    });
});


//S 右键菜单
jQuery(document).ready(function ($) {
    $("#assistant").mousedown(function (e) {
        if (e.which == 3) {
            showMessage("恭喜你，发现秘密通道(●'◡'●)♥:<br/><br/><a href=\"http://www.baidu.com\" title=\"百度\">百度</a><br/><br/><a href =\"http://https://github.com/zxcv8556723/SouSou\" title=\"此项目源码github\">此项目源码github</a>", 10000, 10000
            )
            ;
        }
    });
    $("#assistant").bind("contextmenu", function (e) {
        return false;
    });
});
//E 右键菜单

//S 鼠标在消息上时
jQuery(document).ready(function ($) {
    $("#message").hover(function () {
//显示到最亮，不变淡
        $("#message").fadeTo("100", 1);
    });
});
//E 鼠标在消息上时

//S 鼠标在助理上方时
jQuery(document).ready(function ($) {
//$(".assistant_girl").jrumble({rangeX: 2,rangeY: 2,rangeRot: 1});
    $("#assistant_girl").mouseover(function () {
        $("#assistant_girl").fadeTo("300", 0.3);
        msgs = ["充个话费，我就跟你玩~",
            "本小助理可远观不可亵玩！",
            "我会隐身哦！嘿嘿！",
            "好没礼貌呀，把手拿开！！",
            "再不把手拿开小心我横竖竖你！！",
            "主人，他摸我，呜呜呜呜~~~",
            "你把手拿开我就出来！"];
        var i = Math.floor(Math.random() * msgs.length);
        showMessage(msgs[i], 5000, 3000);
    });
    $("#assistant_girl").mouseout(function () {
        $("#assistant_girl").fadeTo("300", 1)
    });
});
//E 鼠标在上方时