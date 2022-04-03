import axios from "axios";

export const HTTP = axios.create({
    baseURL: 'http://localhost:8080/api/v1/',
})