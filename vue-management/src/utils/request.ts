import axios, { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

// 定义全局可访问的常量baseUrl
export const baseUrl: string = 'http://127.0.0.1:8080';


const service: AxiosInstance = axios.create({
    baseURL: baseUrl,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json'
    },
    withCredentials: true
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
