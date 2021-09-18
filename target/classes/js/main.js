import Vue from "vue";
import Vuetify from "vuetify";
import '@babel/polyfill'
import './api/resource'

import App from './pages/App.vue'
import store from "./store/store";
import router from "./router/router";
import 'vuetify/dist/vuetify.min.css'
import VueCytoscape from 'vue-cytoscape'


Vue.use(Vuetify)
Vue.use(VueCytoscape)


new Vue({
    vuetify: new Vuetify(),
    el: '#app',
    store,
    router,
    render: a => a(App)
})

