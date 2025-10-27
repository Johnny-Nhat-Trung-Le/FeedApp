import type {ReactNode} from "react";

export default function Container({children}: {children: ReactNode}) {
    return <div className={`max-w-6xl m-auto h-full px-10`}>
        {children}
    </div>
}