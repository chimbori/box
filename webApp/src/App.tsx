import { com } from "box-shared";

// Kotlin code from shared/src/commonMain and shared/src/jsMain, exported via @JsExport.
const greeting = new com.chimbori.box.JsGreeting().greet();

export default function App() {
  return (
    <main style={{ fontFamily: "system-ui", textAlign: "center", padding: "2rem" }}>
      <h1>{greeting}</h1>
    </main>
  );
}
