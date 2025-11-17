import {useForm, type SubmitHandler} from "react-hook-form"
import {Link, useNavigate} from "react-router-dom";
import {yupResolver} from "@hookform/resolvers/yup";
import {loginSchema} from "../validation/Schema.tsx";
import Container from "../common/utils/Container.tsx";
import {useContext} from "react";
import {UserDataContext} from "../../context/Context.tsx";
import type {LoginType} from "../../interfaces/Types.tsx";
import {useMutation} from "@tanstack/react-query";
import axios from "axios";

export default function Login() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<LoginType>({
        resolver: yupResolver(loginSchema),
    });

    const {setUserData} = useContext(UserDataContext);

    const navigate = useNavigate();

    const mutation = useMutation({
        mutationFn: (userData: LoginType) => {
            return axios.post("http://localhost:8080/api/v1/login", userData);
        },
        onSuccess:  (response) => {
            console.log(response);
            setUserData({token: response.data.token, username: response.data.username, id: response.data.userId})
            navigate(`/users`);
        }
    })

    const onSubmit: SubmitHandler<LoginType> = (formData) => {
        mutation.mutate(formData);
    }

    return (
        <Container style={"flex flex-col justify-center items-center my-40"}>
                <h1 className={"mb-5 font-bold text-2xl"}>Login</h1>
                <form onSubmit={handleSubmit(onSubmit)} className={"flex flex-col gap-5 min-w-60"}>
                    <div className="flex flex-col gap-1">
                        <label className={"font-semibold"}>Username</label>
                        <input {...register("username", {required: true})}
                               className={"border border-gray-300 rounded-4xl px-2 py-1 "}/>
                        <p className={"text-red-500 text-xs"}>{errors.username?.message}</p>
                    </div>

                    <div className="flex flex-col gap-1">
                        <label className={"font-semibold"}>Password</label>
                        <input type="password" {...register("password", {required: true})}
                               className={"border border-gray-300 rounded-4xl px-2 py-1"}/>
                        <p className={"text-red-500 text-xs"}>{errors.password?.message}</p>
                    </div>
                    <p className={"text-sm m-auto"}>Don't have an account? <Link to={"/register"}
                                                                                 className={"font-semibold"}>Register</Link>
                    </p>
                    <button type="submit" className={"py-2 px-4 rounded-sm m-auto self-start cursor-pointer bg-gradient-to-r from-gradient-light-purple to-gradient-dark-purple text-white hover:from-purple-300 hover:to-purple-700"}>Login
                    </button>
                </form>
        </Container>
    )
}