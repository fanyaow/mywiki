<template>
  <a-layout-header class="header">
    <div>
      <div class="logo">Wiki知识库</div>
    </div>
    <div>
      <a-popconfirm
          title="确认退出登录?"
          ok-text="是"
          cancel-text="否"
          @confirm="logout()"
      >
        <a class="login-menu" v-show="user.id">
          <span>退出登录</span>
        </a>
      </a-popconfirm>
      <a class="login-menu" v-show="user.id">
        <span>您好：{{user.name}}</span>
      </a>
      <a class="login-menu" v-show="!user.id" @click="showLoginModal">
        <span>登录</span>
      </a>
    </div>
    <a-menu
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/">
        <router-link to="/">首页</router-link>
      </a-menu-item>
<!--      <a-menu-item key="/admin/user" :style="user.id? {} : {display:'none'}">-->
      <a-menu-item key="/admin/user" v-if="user.id">
        <router-link to="/admin/user">用户管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/ebook" v-if="user.id">
        <router-link to="/admin/ebook">文档管理</router-link>
      </a-menu-item>
      <a-menu-item key="/admin/category" v-if="user.id">
        <router-link to="/admin/category">市场管理</router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">关于我们</router-link>
      </a-menu-item>
    </a-menu>

    <a-modal
            title="用户登录"
            v-model:visible="loginModalVisible"
            :confirm-loading="loginModalLoading"
            @ok="login"
    >
      <a-form :model="loginUser" :label-col="{ span: 6 }" :wrapper-col="{ span: 18 }">
        <a-form-item label="登陆名">
          <a-input v-model:value="loginUser.loginName" />
        </a-form-item>
        <a-form-item label="密码" >
          <a-input v-model:value="loginUser.password" type="password"  />
        </a-form-item>
      </a-form>
    </a-modal>

  </a-layout-header>
</template>
<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
import router from "@/router";

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
  name: 'the-header',

  setup() {

    //   //登录后保存
    // const user=ref();
    // user.value={};
    const user = computed(()=>store.state.user);
      //登录用户
    const loginUser = ref({
      loginName: 'test',
      password: 'test'
    });

    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };

    const login = () => {
        console.log('开始登录');

        loginModalLoading.value=true;
        loginUser.value.password=hexMd5(loginUser.value.password + KEY)
        axios.post("/user/login", loginUser.value).then((response) => {
            loginModalLoading.value = false;
            const data = response.data;
            if (data.success) {
              loginModalVisible.value = false;
                message.success("登录成功");
                console.log("昵称：",loginUser.value.loginName)
                // user.value=data.content;
                store.commit("setUser",data.content);

            }else{
                message.error(data.message)
            }
            });

    };

    // 退出登录
    const logout = () => {
      console.log("退出登录开始");
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("退出登录成功！");
          store.commit("setUser", {});
          //退出登陆后,把退出的页面也都返回到登陆页面
          router.push('/');
        } else {
          message.error(data.message);
        }
      });
    };


    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      loginUser,
      login,
      user,
      logout,
    }
  }
});

</script>

<style>
  .logo {
    width: 120px;
    height: 31px;
    /*background: rgba(255, 255, 255, 0.2);*/
    /*margin: 16px 28px 16px 0;*/
    float: left;
    color: white;
    font-size: 18px;
  }
  .login-menu {
    float: right;
    color: white;
    padding-left: 10px;
  }
</style>
