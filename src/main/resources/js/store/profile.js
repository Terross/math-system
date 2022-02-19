const state = () => ({
    profile: {
        authorization: false,
        name: '',
        email: '',
        role: '',
        group: '',
        jwt: ''
    }
})

const getters = {
    isAuth() {
        return state().profile.authorization
    }
}

export default {
    namespaced: true,
    state,
    getters
}