import Vue from "vue";
import Vuetify from "vuetify";
import '@babel/polyfill'
import 'api/resource'
import App from 'pages/App.vue'
import store from "./store/store";
import router from "./router/router";
import 'vuetify/dist/vuetify.min.css'



Vue.use(Vuetify)


new Vue({
    vuetify: new Vuetify(),
    el: '#app',
    store,
    router,
    render: a => a(App)
})

