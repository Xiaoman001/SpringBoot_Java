<template>
  <div class="wrap">
    <div class="wrap-head">
      <div class="head-logo">
        <img src="../../assets/images/liuyan.png" />
      </div>
    </div>
    <div class="main-txt">
      <textarea name="" rows="" cols="" class="main-area"></textarea>
    </div>
    <div class="warp-footer">

      <div class="warp-footer-btns">
        <div class="release-btn">
          <a href="javascript:void(0)">发布</a>
        </div>
      </div>
    </div>
  </div>
  <!-- 显示留言的主体 -->
<!--  <div class="show">-->
<!--    <div class="show-content">-->
<!--      <div class="show-name">Xx</div>-->
<!--      <div class="show-txt">-->
<!--        <p class="">这是内容</p>-->
<!--      </div>-->
<!--      <div class="show-time">2018年10月24日</div>-->
<!--      <div class="show-close">x</div>-->
<!--    </div>-->
<!--  </div>-->
</template>

<script>
export default {
  mounted(){
    // 匿名函数包裹，防止外界操作的修改
    $(function () {
      // 还能输入的字得个数
      var able_count = 140;
      // 是否可以发布留言
      var release_able = false;
      // 右上角文字
      var $title_txt = $('.title-txt');
      // 留言框
      var $main_area = $('.main-area');
      // 发布按钮
      var $release_btn = $('.release-btn');

      // 输入框获取焦点
      $main_area.focus(function () {
        console.log("获取焦点");
        $(this).parent().addClass('outline');
        $title_txt.addClass('title');
        if (able_count >= 0) {
          $title_txt.html("还可以输入" + able_count + "个字");
        } else {
          $title_txt.html("你以超出" + (-able_count) + "个字");
        }
      })

      // 输入框失去焦点
      $main_area.blur(function () {
        console.log("失去焦点");
        $(this).parent().removeClass('outline');
        $title_txt.removeClass('title');

      })

      // 输入框文本修改
      $main_area.on('input', function () {
        console.log("文本修改");
        // 剩余可输入的字个数
        able_count = 140 - $main_area.val().length;
        // console.log(able_count);
        // 根据可输入字的个数决定右上角文本的提示 与 是否能发布的状态
        if (able_count >= 0 && able_count <= 140) {
          $title_txt.html("还可以输入" + able_count + "个字");
          if (able_count != 140) {
            release_able = true;
          } else {
            release_able = false;
          }
        } else {
          $title_txt.html("你以超出" + (-able_count) + "个字");
          release_able = false;
        }
        // 根据发布状态决定发布按钮的样式
        if (release_able) {
          $release_btn.css({
            backgroundColor: "orange",
            borderColor: "orange"
          })
        } else {
          $release_btn.css({
            backgroundColor: "#ffc09f",
            borderColor: "#ffc09f"
          })
        }

      })

      // 发布事件
      $release_btn.click(function () {
        console.log("发布");
        if (release_able) {
          console.log('可以发布');
          // 创建show对象的各个部位
          var $showContent = $('<div class="show-content"></div>'),
            $showName = $('<div class="show-name"></div>'),
            $showTxt = $('<div class="show-txt"></div>'),
            $showTime = $('<div class="show-time"></div>'),
            $showClose = $('<div class="show-close"></div>'),
            $showP = $('<p class=""></p>');

          var date = new Date();
          // 设置，对象结构内内容
          $showName.text("王五");
          $showP.text($main_area.val());
          $showTime.text(date);
          $showClose.text("x");
          // 添加进入主结构
          $showTxt.append($showP);
          $showContent.append($showName);
          $showContent.append($showTxt);
          $showContent.append($showTime);
          $showContent.append($showClose);

          // 向所有匹配元素内部的开始处插入内容
          $('.show').prepend($showContent);

          // 添加动画
          // 位置从输入框处下移
          $showContent.css({
            top: '-150px'
          })
          $showContent.animate({
            top: 0
          }, 200)

          // 删除事件
          $showClose.click(function () {
            // 显示插入的索引位置
            // console.log($(this).parent().index());
            // console.log($showContent.index());


            删除操作为顺便
            $showContent.remove();

            // 使用删除动画，创建效果
            $showContent.animate({
              height: 0
            }, 200, function () {
              // 动画结束后将自身从dom中移除
              $showContent.remove();
            })


          })

          // 发布成功后收尾工作
          $main_area.val(""); //输入框清空
          able_count = 140;  //输入框可输入内容数重置
          release_able = false;
          $release_btn.css({
            backgroundColor: '#ffc09f',
            borderColor: '#ffc09f'
          })  //按钮点击事件重置

        }
      })

    })
  }

}
</script>

<style scoped>
body,
ul {
  margin: 0;
  padding: 0;
}

ul {
  list-style: none;
}

/*最外层*/
.wrap {
  width: 600px;
  height: 165px;
  margin: 20px auto;
  border-radius: 4px;
  border: 1px solid #ddd;
  padding: 0 10px;
}

.wrap-head {
  width: 100%;
  height: 24px;
  padding-top: 4px;
  overflow: hidden;
}

.head-logo {
  width: 40%;
  float: left;
}

.head-logo img {
  width: 30px;
  height: 30px;
}

.head-txt {
  padding: 4px 0;
  width: 60%;
  float: right;
}

.head-txt a {
  font-size: 12px;
  color: #eb7350;
  text-decoration: none;
}

.title-txt.title {
  text-align: right;
  color: black;
  display: block;
  width: 100%;
}


/*内层设计，输入框*/
.main-txt {
  border: 1px solid #ccc;
  width: 98%;
  height: 68px;
  margin: 4px 0 0;
  padding: 5px;
  box-shadow: 0px 0px 3px 0px rgba(0, 0, 0, 0.15) inset;
}

.main-txt textarea {
  border: none;
  width: 100%;
  height: 66px;
  outline: none;
  resize: none;
  color: #333;
}

.main-txt.outline {
  outline: 2px orange solid;
}

/*下层设计*/
.warp-footer {
  width: 100%;
  height: 40px;
  margin: 6px 0;
  overflow: hidden;
}

.warp-icon-cont {
  width: 65%;
  float: left;
  margin-top: 10px;
}

.warp-icon-cont ul li {
  display: inline-block;
  margin-right: 15px;
  cursor: pointer;
}

.warp-icon-cont a {
  font-size: 12px;
  color: #333;
  text-decoration: none;
  height: 20px;
  margin-left: 5px;
  display: block;
  width: 25px;
  float: right;
  line-height: 20px;
}

.warp-icon-cont a:hover {
  color: #eb7350;
}

.warp-icon-cont img {
  width: 20px;
  height: 20px;
}

.warp-footer-btns {
  width: 35%;
  float: right;
  overflow: hidden;
  margin-top: 3px;
}

.release-btn {
  width: 80px;
  height: 28px;
  float: right;
  background-color: #ffc09f;
  border: 4px solid #fbbd9e;
  cursor: pointer;
  border-radius: 2px;
}

.release-btn a {
  display: block;
  color: #fff;
  width: 80px;
  height: 28px;
  line-height: 28px;
  text-align: center;
  text-decoration: none;
  font-size: 15px;
}

/*后期添加的留言框*/
.show {
  width: 600px;
  margin: 20px auto;
}

.show-content {
  width: 578px;
  border: 1px solid #ccc;
  border-radius: 4px;
  margin-bottom: 10px;
  padding: 10px;
  position: relative;
  overflow: hidden;
}

.show-name {
  width: 100%;
  text-align: left;
  font-size: 14px;
  color: #333;
  font-weight: bold;
}

.show-txt {
  width: 100%;
  color: #444;
  font-size: 14px;
  margin-top: 10px;
}

.show-txt p {
  width: 100%;
  word-wrap: break-word;
}

.show-time {
  font-size: 12px;
  color: #808080;
  margin-top: 10px;
}

.show-close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 12px;
  color: #ccc;
  cursor: pointer;
  transition: .5s;
}

.show-close:hover {
  color: red;
}
</style>
