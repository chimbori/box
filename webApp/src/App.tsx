import { useEffect, useMemo, useState } from "react";
import { com } from "box-shared";
import filesIcon from "./assets/icons/files.svg";
import notesIcon from "./assets/icons/notes.svg";
import collectionsIcon from "./assets/icons/collections.svg";


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
    <div className="flex h-screen max-[299px]:h-auto max-[299px]:min-h-screen max-[299px]:flex-col">
      <nav
        className="flex w-60 shrink-0 flex-col gap-1 bg-[#0061d5] px-3 py-5 text-white max-[599px]:w-[72px] max-[599px]:items-center max-[599px]:px-2 max-[599px]:py-4 max-[299px]:w-full max-[299px]:flex-row max-[299px]:items-center max-[299px]:gap-1 max-[299px]:p-2"
        aria-label="Main navigation"
      >
        <div className="px-3 pt-1 pb-5 text-[28px] font-bold tracking-[-0.5px] max-[599px]:pb-4 max-[599px]:text-xl max-[299px]:hidden">
          Box
        </div>
        {state.tabs.map((tab) => (
          <button
            key={tab.id}
            className={`rail-item ${tab.id === state.selectedTabId ? "rail-item-selected" : ""}`}
            onClick={() => box.selectTab(tab.id)}
          >
            <img src={icons[tab.icon]} alt="" width={22} height={22} className="shrink-0 brightness-0 invert" />
            <span className="whitespace-nowrap max-[599px]:sr-only">{tab.title}</span>
          </button>
        ))}
      </nav>
      <main className="min-w-0 flex-1 overflow-y-auto bg-white px-8 py-6 max-[599px]:p-4">
        <div className="mb-7 flex max-w-[520px] items-center gap-3 rounded-3xl border border-[#ccc] px-4 py-2.5 max-[599px]:mb-5">
          <span aria-hidden="true">🔍</span>
          <input
            type="search"
            placeholder="Search files and folders"
            aria-label="Search"
            className="flex-1 border-none bg-transparent font-[inherit] text-[15px] outline-none"
          />
        </div>
        <h1 className="mb-4 text-[28px] font-semibold">{state.title}</h1>
        {state.selectedTabId === "files" && (
          <>
            <h2 className="mb-3 text-base font-semibold">Recents</h2>
            <div className="mb-8 grid grid-cols-[repeat(auto-fill,minmax(160px,1fr))] gap-4">
              {state.files.slice(0, 4).map((f) => (
                <div key={f.name} className="overflow-hidden rounded-xl border border-[#e3e3e3] bg-white">
                  <div className="flex h-[120px] items-center justify-center bg-[#f5f5f5] text-[40px]">
                    {f.kind === "folder" ? "📁" : "📄"}
                  </div>
                  <div className="truncate px-3 pt-2.5 pb-0.5 font-medium">{f.name}</div>
                  <div className="px-3 pb-2.5 text-sm text-[#666]">{f.modified}</div>
                </div>
              ))}
            </div>
            <table className="w-full border-collapse max-[599px]:block max-[599px]:overflow-x-auto">
              <thead>
                <tr>
                  <th className="table-th">Name</th>
                  <th className="table-th w-[220px]">Updated</th>
                  <th className="table-th w-[120px]">Size</th>
                </tr>
              </thead>
              <tbody>
                {state.files.map((f) => (
                  <tr key={f.name}>
                    <td className="table-td name">{f.kind === "folder" ? "📁" : "📄"} {f.name}</td>
                    <td className="table-td meta">{f.modified}</td>
                    <td className="table-td meta">{f.size ?? (f.kind === "folder" ? "—" : "")}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
        {state.selectedTabId === "notes" && (
          <ul className="m-0 list-none p-0">
            {state.notes.map((n) => (
              <li key={n.title} className="list-row">
                <span className="name">{n.title}</span>
                <span className="meta">{n.preview}</span>
                <span className="meta">{n.updated}</span>
              </li>
            ))}
          </ul>
        )}
        {state.selectedTabId === "collections" && (
          <ul className="m-0 list-none p-0">
            {state.collections.map((c) => (
              <li key={c.name} className="list-row">
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
