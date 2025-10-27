import * as yup from "yup";

export const loginSchema = yup.object({
    username: yup.string().required("Username is required"),
    password: yup.string().required("Password is required"),
});

export const registerSchema = yup.object({
    username: yup.string().required("Username is required"),
    password: yup.string().required("Password is required"),
    email: yup.string().email("Email is not valid").required("Email is required"),
})