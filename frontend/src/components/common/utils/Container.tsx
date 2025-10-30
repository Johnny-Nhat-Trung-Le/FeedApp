import type {ReactNode} from "react";

export default function Container({children, style=""}: {children: ReactNode, style?: string}) {
    return <div className={`w-full max-w-6xl m-auto px-10 ${style}`}>
        {children}
    </div>
}