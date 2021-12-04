<template>
  <v-col v-if=" longTimeExecute ||
                alreadyExistAlert ||
                errorJarAlert ||
                errorInterfaceAlert ||
                successAlert ||
                somethingWrong ||
                errorSize ">
    <v-alert
        type="success"
        v-model="successAlert"
        text
        outlined
        class="ma-4"
        dismissible>
      Плагин успешно загружен!
    </v-alert>
    <v-alert
        type="error"
        v-model="errorInterfaceAlert"
        text
        outlined
        class="ma-4"
        dismissible>
      Класс в jar реализует другой тип плагина!
    </v-alert>
    <v-alert
        type="error"
        v-model="errorJarAlert"
        text
        outlined
        class="ma-4"
        dismissible>
      Класса именем {{pluginName.substring(0, pluginName.indexOf('.'))}} не обнаружено в jar!
    </v-alert>
    <v-alert
        type="error"
        v-model="alreadyExistAlert"
        text
        outlined
        class="ma-4"
        dismissible>
      Плагин уже добавлен! (Совпадает либо имя плагина, либо имя файла)
    </v-alert>
    <v-alert
        type="error"
        v-model="longTimeExecute"
        text
        outlined
        class="ma-4"
        dismissible>
      Плагин выполняется слишком долго!
    </v-alert>
    <v-alert
        type="error"
        v-model="somethingWrong"
        text
        outlined
        class="ma-4"
        style="white-space: pre-line"
        dismissible>
      {{"В вашем коде ошибка: \n" + javaError}}
    </v-alert>
    <v-alert
        type="error"
        v-model="errorSize"
        text
        outlined
        class="ma-4"
        dismissible>
      Размер jar файла не должен превышать 128кб!
    </v-alert>

  </v-col>
</template>

<script>
export default {
  name: "PluginAlerts",
  props: {
    alertType : String,
    pluginName : String,
    javaError: String
  },
  data() {
    return {
      longTimeExecute: false,
      alreadyExistAlert: false,
      errorJarAlert: false,
      errorInterfaceAlert: false,
      successAlert: false,
      somethingWrong: false,
      errorSize: false
    }
  },
  watch: {
    alertType() {
      switch (this.alertType) {
        case 'somethingWrong':
          console.log(this.javaError)
          this.longTimeExecute
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.successAlert
              = this.errorSize
              = false
          this.somethingWrong = true
          break
        case 'success':
          this.longTimeExecute
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.somethingWrong
              = this.errorSize
              = false
          this.successAlert = true
          break
        case 'errorInterface':
          this.longTimeExecute
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.successAlert
              = this.somethingWrong
              = this.errorSize
              = false
          this.errorInterfaceAlert = true
          break
        case 'errorJar':
          this.longTimeExecute
              = this.alreadyExistAlert
              = this.successAlert
              = this.errorInterfaceAlert
              = this.somethingWrong
              = this.errorSize
              = false
          this.errorJarAlert = true
          break
        case 'errorExist':
          this.longTimeExecute
              = this.successAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.somethingWrong
              = this.errorSize
              = false
          this.alreadyExistAlert = true
          break
        case 'errorTime':
          this.successAlert
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.somethingWrong
              = this.errorSize
              = false
          this.longTimeExecute = true
          break
        case 'errorSize':
          this.successAlert
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.somethingWrong
              = this.longTimeExecute
              = false
          this.errorSize = true
          console.log(123123)
          break
        default:
          this.successAlert
              = this.alreadyExistAlert
              = this.errorJarAlert
              = this.errorInterfaceAlert
              = this.longTimeExecute
              = this.somethingWrong
              = this.errorSize
              = false
          break
      }
    }
  }
}
</script>

<style scoped>

</style>