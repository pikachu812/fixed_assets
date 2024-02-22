import axios, { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

const service: AxiosInstance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    },
    method: 'post'
});

service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        return config;
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject();
    }
);

service.interceptors.response.use(
    (response: AxiosResponse) => {
        if (response.status === 200) {
            return response;
        } else {
            Promise.reject(new Error("请求失败，状态码：" + response.status));
        }
    },
    (error: AxiosError) => {
        console.log(error);
        return Promise.reject(error);
    }
);

export default service;
