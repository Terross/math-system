<template>
  <v-container fluid fill-height>
    <v-row  justify="center" align="center" >
      <v-col align="center" cols="4">
        <v-card
            class="mx-auto"
            max-width="600"
        >
          <v-toolbar dark color="primary">
            <v-toolbar-title>Регистрация</v-toolbar-title>
          </v-toolbar >
          <v-card-text>
            <v-form
                ref="form"
                v-model="valid"
            >
              <v-text-field
                  label="Имя"
                  v-model="firstName"
                  :rules="nameRules"
                  required
              >
              </v-text-field>
              <v-text-field
                  label="Фамилия"
                  v-model="lastName"
                  :rules="nameRules"
                  required
              >
              </v-text-field>
              <v-text-field
                  label="Отчество"
                  v-model="patronymic"
                  required
              >
              </v-text-field>
              <v-text-field
                  v-model="email"
                  :rules="emailRules"
                  label="E-mail"
                  required
              ></v-text-field>
              <v-text-field
                  v-model="userGroup"
                  label="Группа"
              >
              </v-text-field>
              <v-text-field
                  v-model="password"
                  :rules="passwordRules"
                  label="Пароль"
                  type="password"
                  required
              >
              </v-text-field>
              <v-text-field
                  v-model="secondPassword"
                  label="Подтвердите пароль"
                  type="password"
                  required
              >
              </v-text-field>
            </v-form>
          </v-card-text>
          <v-card-actions class="justify-lg-start">
            <v-btn
                :disabled="!valid"
                color="primary"
                class="ma-auto"
                @click="register"
            >
              Зарегистрироваться
            </v-btn>
            <v-btn
                color="primary"
                class="ma-auto"
                @click="signIn"
            >
              Войти
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
  name: 'Register',
  data: () => ({
    valid: true,
    firstName: '',
    lastName: '',
    patronymic: '',
    email: '',
    userGroup: '',
    password: '',
    secondPassword: '',
    nameRules: [
        v => !!v || 'Обязательное поле'
    ],
    emailRules: [
        v => !!v || 'Обязательное поле',
        v => /.+@.+\..+/.test(v) || 'Некорректная почта'
    ]
  }),
  computed : {
    passwordRules () {
      const rules = []
      rules.push(v => !! v || 'Обязательное поле')
      if (this.secondPassword) {
        rules.push(v => (!!v && v) === this.secondPassword ||
                'Пароли должны совпадать')
      }
      return rules
    }
  },
  watch: {
    firstName: 'validate',
    lastName: 'validate',
    email: 'validate',
    password: 'validate',
    secondPassword: 'validate'
  },
  methods: {
    ...mapMutations([
      'profile/authProfile'
    ]),
    validate() {
      this.$refs.form.validate()
    },
    register () {
      const data = {
        "firstName": this.firstName,
        "lastName": this.lastName,
        "patronymic": this.patronymic,
        "email": this.email,
        "password": this.password,
        "userGroup": this.userGroup
      }
      this.$http
          .post('/api/v1/auth/register', data)
          .then(response => {
            this['profile/authProfile'](response.data)
            this.$router.push("/profile")
          })
          .catch(e => {
            console.log(e)
          })

    },
    signIn () {
      this.$router.push("/auth/login")
    }
  },
}
</script>

<style scoped>

</style>