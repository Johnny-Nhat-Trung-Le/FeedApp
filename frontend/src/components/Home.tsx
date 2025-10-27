import Button from "../utils/Button.tsx";
import Container from "../utils/Container.tsx";

export default function Home() {
    return <Container>
        <header className={"mt-20 flex flex-col gap-4"}>
            <h2 className={"text-primary font-bold"}>Easy to make, easy to use</h2>
            <h1>Create polls and get people's opinions</h1>
            <Button title={"Get started"} style={"bg-primary text-white"}/>
        </header>
    </Container>
}