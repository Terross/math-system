const state = () => ({
    profiles: frontendData.users,
    profile: {
        authorization: false,
        firstName: '',
        lastName: '',
        patronymic: '',
        email: '',
        role: '',
        group: '',
        jwt: ''
    }
})

const getters = {
    isAuth() {
        console.log(state().profile.authorization)
        return state().profile.authorization
    }
}

const mutations = {
    authProfile(state, auth) {
        state.profile = {
            authorization: true,
            firstName: auth.user.firstName,
            lastName: auth.user.lastName,
            patronymic: auth.user.patronymic,
            email: auth.user.email,
            role: auth.user.role.name === "ROLE_USER" ? "USER" : "ADMIN",
            group: auth.user.userGroup,
            jwt: auth.token
        }
    },
    logout(state) {
        state.profile = {
            authorization: false,
            firstName: '',
            lastName: '',
            email: '',
            role: '',
            group: '',
            jwt: ''
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations
}