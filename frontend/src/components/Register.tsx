import {type SubmitHandler, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {registerSchema} from "./validation/Schema.tsx";
import {Link} from "react-router-dom";

interface RegisterType{
    username: string,
    password: string,
    email: string,
}

export default function Register() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<RegisterType>({
        resolver: yupResolver(registerSchema),
    });

    const onSubmit: SubmitHandler<RegisterType> = (data) => console.log(data);

    return (<div className={"flex flex-col justify-center items-center my-10"}>
            <h1 className={"mb-8 font-bold text-2xl"}>Register</h1>
            <form onSubmit={handleSubmit(onSubmit)} className={"flex flex-col gap-5 min-w-60"}>
                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Username</label>
                    <input {...register("username", {required: true})}
                           className={"border border-gray-300 rounded-4xl px-2 py-1 "}/>
                    <p className={"text-red-500 text-xs"}>{errors.username?.message}</p>
                </div>

                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Email</label>
                    <input {...register("email", {required: true})}
                           className={"border border-gray-300 rounded-4xl px-2 py-1 "}/>
                    <p className={"text-red-500 text-xs"}>{errors.email?.message}</p>
                </div>

                <div className="flex flex-col gap-1">
                    <label className={"font-semibold"}>Password</label>
                    <input type="password" {...register("password", {required: true})}
                           className={"border border-gray-300 rounded-4xl px-2 py-1"}/>
                    <p className={"text-red-500 text-xs"}>{errors.password?.message}</p>
                </div>
                <p className={"text-sm m-auto"}>Already have an account? <Link to={"/login"}
                                                                             className={"font-semibold"}>Login</Link>
                </p>
                <button type="submit" className={"py-2 px-4 rounded-sm m-auto self-start cursor-pointer bg-gradient-to-r from-gradient-light-purple to-gradient-dark-purple text-white hover:from-purple-300 hover:to-purple-700"}>Register
                </button>
            </form>
        </div>
    )
}