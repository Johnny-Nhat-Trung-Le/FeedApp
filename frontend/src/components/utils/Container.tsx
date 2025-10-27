import type {ReactNode} from "react";

export default function Container({children}: {children: ReactNode}) {
    return <div className={`w-full max-w-6xl m-auto grow px-10`}>
        {children}
    </div>
}