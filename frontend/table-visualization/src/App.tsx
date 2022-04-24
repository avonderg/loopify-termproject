import React from "react";
import TableViz from "./components/TableViz";
import "./App.css";
import "./index.css";

/**
 * The high-level App class of the React app.
 * @returns The HTML and React components to be rendered
 */
function App() {
  return (
    <div
      style={{
        backgroundColor: "#2f1d39",
        margin: "0",
        paddingLeft: "2%",
        backgroundPosition: "center",
        backgroundSize: "cover",
        backgroundRepeat: "no-repeat",
        width: "100vw",
        minHeight: "100vw",
        height: "max-content",
      }}
    >
      <TableViz />
    </div>
  );
}

export default App;
