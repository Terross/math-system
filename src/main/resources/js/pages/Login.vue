<template>
  <v-container fluid fill-height>
    <v-row  justify="center" align="center" >
      <v-col align="center" cols="4">
        <v-card
            class="ma-auto"
            max-width="600"
            outlined
        >
          <v-toolbar dark color="primary">
            <v-toolbar-title>Форма входа</v-toolbar-title>
          </v-toolbar >
          <v-card-text class="justify-center">
            <v-form
                ref="form"
                v-model="valid"
                class="ma-auto"
            >
              <v-text-field
                  v-model="email"
                  :rules="emailRules"
                  label="E-mail"
                  required
              ></v-text-field>

              <v-text-field
                  v-model="password"
                  label="Password"
                  type="password"
                  required
              ></v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-lg-start">
            <v-btn
                :disabled="!valid"
                color="primary"
                class="ma-auto"
                @click="signIn"
            >
              Войти
            </v-btn>

            <v-btn
                color="primary"
                class="ma-auto"
                @click="signUp"
            >
              Регистрация
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import {HTTP} from "../axios/http-common";
import {mapActions, mapGetters, mapMutations} from 'vuex'

export default {
  name: "Login",
  data: () => ({
    valid: true,
    email: '',
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
    password:'',
  }),

  methods: {
    ...mapMutations([
      'profile/authProfile'
    ]),
    signIn () {
      const data = {
        "email": this.email,
        "password": this.password
      }
      HTTP
          .post('auth/login', data)
          .then(response => {
            this['profile/authProfile'](response.data)
            this.$router.replace("/profile")
          })
          .catch(e => {
            console.log(e)
          })
    },
    signUp () {
      this.$router.replace("/auth/registration")
    }
  },
}
</script>

<style scoped>

</style>