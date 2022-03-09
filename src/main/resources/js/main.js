import Vue from "vue";
import Vuetify from "vuetify";
import '@babel/polyfill'
import './api/resource'

import App from './pages/App.vue'
import store from "./store/store";
import router from "./router/router";
import 'vuetify/dist/vuetify.min.css'
import VueCytoscape from 'vue-cytoscape'
import colors from 'vuetify/lib/util/colors'

Vue.use(Vuetify)
Vue.use(VueCytoscape)

const vuetify = new Vuetify({
    theme: {
        themes: {
            light: {
                primary: colors.blue.lighten1,
                secondary: colors.blue.lighten5,
                accent: colors.green.darken3,
                error: colors.red.darken3,
            }
        },
    },
})

new Vue({
    vuetify: new Vuetify(),
    el: '#app',
    store,
    router,
    render: a => a(App)
})

