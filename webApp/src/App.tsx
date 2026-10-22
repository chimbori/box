import { useEffect, useMemo, useState } from "react";
import { com } from "box-shared";
import filesIcon from "./assets/icons/files.svg";
import notesIcon from "./assets/icons/notes.svg";
import collectionsIcon from "./assets/icons/collections.svg";
import "./App.css";

// Icon keys come from tabs.yaml; each maps to an SVG asset in src/assets/icons.
const icons: Record<string, string> = {
  files: filesIcon,
  notes: notesIcon,
  collections: collectionsIcon,
};

interface Tab {
  id: string;
  title: string;
  icon: string;
}

interface BoxState {
  tabs: Tab[];
  selectedTabId: string;
  title: string;
  files: { name: string; kind: string; size?: string; modified: string }[];
  notes: { title: string; preview: string; updated: string }[];
  collections: { name: string; description: string; itemCount: number }[];
}

const emptyState: BoxState = {
  tabs: [],
  selectedTabId: "",
  title: "",
  files: [],
  notes: [],
  collections: [],
};

export default function App() {
  const box = useMemo(() => new com.chimbori.box.JsBox(), []);
  const [state, setState] = useState<BoxState>(emptyState);

  useEffect(() => {
    box.onChange(() => setState(JSON.parse(box.stateJson())));
  }, [box]);

  return (
    <div className="app">
      <nav className="rail" aria-label="Main navigation">
        <div className="brand">Box</div>
        {state.tabs.map((tab) => (
          <button
            key={tab.id}
            className={`rail-item ${tab.id === state.selectedTabId ? "selected" : ""}`}
            onClick={() => box.selectTab(tab.id)}
          >
            <img src={icons[tab.icon]} alt="" width={22} height={22} />
            <span>{tab.title}</span>
          </button>
        ))}
      </nav>
      <main className="content">
        <div className="search">
          <span aria-hidden="true">🔍</span>
          <input type="search" placeholder="Search files and folders" aria-label="Search" />
        </div>
        <h1 className="title">{state.title}</h1>
        {state.selectedTabId === "files" && (
          <>
            <h2 className="section-title">Recents</h2>
            <div className="cards">
              {state.files.slice(0, 4).map((f) => (
                <div key={f.name} className="card">
                  <div className="card-thumb">{f.kind === "folder" ? "📁" : "📄"}</div>
                  <div className="card-name">{f.name}</div>
                  <div className="meta">{f.modified}</div>
                </div>
              ))}
            </div>
            <table className="files-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Updated</th>
                  <th>Size</th>
                </tr>
              </thead>
              <tbody>
                {state.files.map((f) => (
                  <tr key={f.name}>
                    <td className="name">{f.kind === "folder" ? "📁" : "📄"} {f.name}</td>
                    <td className="meta">{f.modified}</td>
                    <td className="meta">{f.size ?? (f.kind === "folder" ? "—" : "")}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
        {state.selectedTabId === "notes" && (
          <ul className="list">
            {state.notes.map((n) => (
              <li key={n.title} className="row">
                <span className="name">{n.title}</span>
                <span className="meta">{n.preview}</span>
                <span className="meta">{n.updated}</span>
              </li>
            ))}
          </ul>
        )}
        {state.selectedTabId === "collections" && (
          <ul className="list">
            {state.collections.map((c) => (
              <li key={c.name} className="row">
                <span className="name">{c.name} ({c.itemCount})</span>
                <span className="meta">{c.description}</span>
              </li>
            ))}
          </ul>
        )}
      </main>
    </div>
  );
}
