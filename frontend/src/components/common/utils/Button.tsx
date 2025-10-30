import { Link } from "react-router-dom";

export default function Button({url, title, style=""}: {url:string, title: string, style?: string}) {
    return <Link to={url} className={`py-2 px-4 rounded-sm self-start ${style}`}>
        {title}
    </Link>
}