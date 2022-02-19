<template>
  <v-container fluid fill-height>
    <v-layout align-center justify-center>
      <v-flex xs12 sm8 md4>
        <v-card
            class="ma-auto"
            max-width="344"
            outlined
        >
          <v-toolbar dark color="primary">
            <v-toolbar-title>Форма входа</v-toolbar-title>
          </v-toolbar >
          <v-card-text class="justify-center">
            <v-form
                ref="form"
                v-model="valid"
                lazy-validation
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
                color="secondary"
                class="ma-auto"
                @click="signUp"
            >
              Регистрация
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
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
    signIn () {
      console.log("signIn")
    },
    signUp () {
      this.$router.replace("/auth/registration")
    }
  },
}
</script>

<style scoped>

</style>