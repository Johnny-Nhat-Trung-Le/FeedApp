import {useForm, type SubmitHandler} from "react-hook-form"
import {Link} from "react-router-dom";
import {yupResolver} from "@hookform/resolvers/yup";
import {loginSchema} from "./validation/Schema.tsx";

interface LoginType{
    username: string,
    password: string,
}

export default function Login() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<LoginType>({
        resolver: yupResolver(loginSchema),
    });

    const onSubmit: SubmitHandler<LoginType> = (data) => console.log(data);

    return (<div className={"flex flex-col justify-center items-center my-10"}>
            <h1 className={"mb-8 font-bold text-2xl"}>Login</h1>
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
        </div>
    )
}