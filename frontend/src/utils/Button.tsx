import { Link } from "react-router-dom";

export default function Button({title, style}: {title: string, style?: string}) {
    return <Link to={"/login"} className={`py-2 px-4 rounded-sm self-start ${style}`}>
        {title}
    </Link>
}