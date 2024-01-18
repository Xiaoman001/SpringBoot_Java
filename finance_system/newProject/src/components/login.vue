<template>
  <div id="login">
    <div class="container">
      <div class="form-container">
        <div class="sign-in">
          <form action="" class="form-sign">
            <h2 class="title">Sign in</h2>
            <div class="input-field">
              <span class="img-container">
                <img src="../assets/images/user.svg" />
              </span>
              <input
                type="text"
                placeholder="Username"
                v-model="form.userName"
              />
            </div>
            <div class="input-field">
              <span class="img-container">
                <img src="../assets/images/lock.svg" />
              </span>
              <input
                type="password"
                placeholder="Password"
                v-model="form.password"
              />
            </div>

            <div class="login-code" @click="refreshCode">
              <!--验证码组件-->
              <input
                type="text"
              />
              <s-identify :identifyCode="identifyCode"></s-identify>
            </div>

            <div class="myButtons">
              <el-button type="primary" @click="submit">Login</el-button>
            </div>
          </form>
        </div>
      </div>
      <div class="panel-container">
        <div class="left-panel">
          <img src="../assets/images/example-7.svg" class="image" />
        </div>
      </div>
      <Vcode :show="isShow" @success="success" @close="close" />
    </div>
  </div>
</template>

<script>
import Vcode from "vue-puzzle-vcode";
import sIdentify from './sIdentify.vue'
export default {
  name: "login",
  components: { Vcode, sIdentify },
  data() {
    return {
      isShow: false,
      form: {
        userName: "",
        password: "",
        code: ""
      },
      identifyCodes: '1234567890abcdefjhijklinopqrsduvwxyz',//随机串内容
      identifyCode: '',
    };
  },
  mounted () {
    // 初始化验证码
    this.identifyCode = ''
    this.makeCode(this.identifyCodes, 4)
  },
  methods: {
    refreshCode () {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    makeCode (o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
      }
    },
    randomNum (min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },

    success() {
      this.isShow = false;
      this.$post({
        url: "/api/load",
        data: this.form,
      }).then((response) => {
        if (response.message!="成功") {
          // if (this.data.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
          //   this.$message.error('请填写正确验证码')
          //   this.refreshCode()
          //   return
          // }

          this.$message({
            showClose: true,
            message: "账号或密码错误",
            type: "error",
          });
        } else {
          localStorage.setItem("token", response.data);
          localStorage.setItem("username", this.form.userName);
          this.$router.push("/index");
          this.$store.commit("getUser", this.form.userName);
          this.$post({
            url: "/api/queryUser",
            data: {
              userName: this.form.userName,
            },
          }).then((res) => {
            this.$store.commit("getCurrentName", res.data[0].name);
          });

        }
      });
    },
    close() {
      this.isShow = false;
    },
    submit() {
      this.isShow = true;
    },
  },
};
</script>
<style>
#login {
  position: absolute;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}
#login::before {
  content: "";
  position: absolute;
  width: 2000px;
  height: 2000px;
  border-radius: 50%;
  background: linear-gradient(-45deg, #4481eb, #04befe);
  top: -10%;
  right: 50%;
  transform: translateY(-50%);
}
.container {
  position: absolute;
  width: 100vw;
  height: 100vh;
  overflow: hidden;
}

.form-container {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}
.sign-in {
  position: absolute;
  top: 50%;
  left: 75%;
  transform: translate(-50%, -50%);
  width: 50%;
  display: grid;
  grid-template-columns: 1fr;
}
form {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  grid-row: 1 / 2;
  grid-column: 1 / 2;
  overflow: hidden;
}
.title {
  font-size: 2.2rem;
  color: #444;
  margin-bottom: 10px ;
}
.input-field {
  max-width: 350px;
  width: 100%;
  height: 55px;
  background-color: #f0f0f0;
  margin: 10px;
  border-radius: 55px;
  display: grid;
  grid-template-columns: 15% 85%;
  padding: 0 0.4rem;
}
.img-container {
  position: relative;
  height: 55px;
  line-height: 55px;
  align-content: center;
}
.input-field img {
  position: absolute;
  width: 25px;
  height: 25px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.input-field input {
  background: none;
  outline: none;
  border: none;
  font-weight: 600;
  font-size: 1.1rem;
  line-height: 1px;
  color: #333;
}
.input-field input::placeholder {
  color: #8a8a8a;
  font-weight: 500;
}
.login-code {
  max-width: 150px;
  /*width: 100%;*/
  /*float: left;*/
  /*overflow: auto;*/
  /*align-self:flex-start;*/
  height: 55px;
  background-color: #f0f0f0;
  margin: 10px 220px 10px 50px;
  border-radius: 55px;
  display: grid;
  grid-template-columns: 130% 70%;
  padding: 0px 20px 0px 20px;
}
.login-code input {
  background: none;
  outline: none;
  border: none;
  font-weight: 600;
  font-size: 1.1rem;
  line-height: 1px;
  color: #333;
}
.myButtons {
  margin: 10px 0;
  height: 50px;
}
.myButtons .el-button {
  width: 100px;
  height: 45px;
  font-size: 20px;
  border-radius: 30px;
}
.pannels-container {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
}
.left-pannel {
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  padding: 3rem 17% 2rem 12%;
  justify-content: space-around;
  text-align: center;
}
.image {
  position: absolute;
  width: 40%;
  left: 32%;
  top: 50%;
  transform: translate(-50%, -50%);
}
</style>
