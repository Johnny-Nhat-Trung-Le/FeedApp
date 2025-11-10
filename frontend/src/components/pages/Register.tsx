import {type SubmitHandler, useForm} from "react-hook-form";
import {yupResolver} from "@hookform/resolvers/yup";
import {registerSchema} from "../validation/Schema.tsx";
import {Link} from "react-router-dom";
import Container from "../common/utils/Container.tsx";
import {useMutation} from "@tanstack/react-query";
import axios from "axios";
import {useNavigate} from "react-router-dom";
import type {UserType} from "../../interfaces/Types.tsx";

export default function Register() {
    const {
        register,
        handleSubmit,
        formState: {errors}
    } = useForm<UserType>({
        resolver: yupResolver(registerSchema),
    });

    const navigate = useNavigate();

    const mutation = useMutation({
        mutationFn: (formData: UserType) => {
            return axios.post("http://localhost:8080/api/v1/register", formData)
        },
        onSuccess:  () => {
            navigate(`/login`);
        }
    })

    const onSubmit: SubmitHandler<UserType> = (formData) => {
        mutation.mutate(formData);
    }

    return (
        <Container style={"flex flex-col justify-center items-center my-30"}>
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
        </Container>
    )
}