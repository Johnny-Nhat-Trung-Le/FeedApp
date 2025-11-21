import * as yup from "yup";

export const loginSchema = yup.object({
    username: yup.string().required("Username is required"),
    password: yup.string().required("Password is required"),
});

export const registerSchema = yup.object({
    username: yup.string().min(3, "Username must be longer than 3 characters").required("Username is required"),
    password: yup.string().min(3, "Password must be longer than 3 characters").required("Password is required"),
    email: yup.string().email("Email is not valid").required("Email is required"),
})