import Button from "../../common/utils/Button.tsx";
import GetStarted from "./GetStarted.tsx";
import Container from "../../common/utils/Container.tsx";
import LatestPolls from "../polls/LatestPolls.tsx";
import Carousel from "../../common/utils/Carousel.tsx";
export default function Home() {
    return <>
        <Container>
            <header className={"mt-40 relative"}>
                <div className={"flex flex-col gap-4 z-10 relative"}>
                    <h2 className={"text-primary font-bold"}>Easy to make, easy to use</h2>
                    <h1>Create polls and get people's opinions!</h1>
                    <Button url={"/register"} title={"Get started"}
                            style={"bg-primary text-white border hover:bg-white hover:text-primary hover:border-primary"}/>
                </div>
                <img src={"/src/assets/header-img.jpg"} alt="hands voting on different numbers"
                     className={"absolute top-15 right-0 max-w-2xl"}/>
            </header>
        </Container>
        <GetStarted />
        <section className={"my-15"}>
            <Container>
                <div className={"text-center"}>
                    <h2 className={"mb-5 font-bold inline-block border-b"}>Latest polls</h2>
                </div>
                <LatestPolls />
            </Container>
        </section>
        <section>
        <Container>
        <h2>TESTER</h2>
        <Carousel />
        </Container>
        </section>
    </>
}