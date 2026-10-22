import Combine
import Shared
import SwiftUI

/// Bridges the shared AppViewModel's StateFlows into SwiftUI published state.
final class BoxStore: ObservableObject {
    private let vm = AppViewModel()

    @Published var tabs: [AppTab] = []
    @Published var selectedTabId: String = ""
    @Published var title: String = ""
    @Published var files: [FileItem] = []
    @Published var notes: [NoteItem] = []
    @Published var collections: [CollectionItem] = []

    init() {
        vm.observe(flow: vm.tabs) { [weak self] value in
            self?.tabs = (value as? [AppTab]) ?? []
        }
        vm.observe(flow: vm.selectedTabId) { [weak self] value in
            self?.selectedTabId = (value as? String) ?? ""
        }
        vm.observe(flow: vm.title) { [weak self] value in
            self?.title = (value as? String) ?? ""
        }
        vm.observe(flow: vm.files.items) { [weak self] value in
            self?.files = (value as? [FileItem]) ?? []
        }
        vm.observe(flow: vm.notes.items) { [weak self] value in
            self?.notes = (value as? [NoteItem]) ?? []
        }
        vm.observe(flow: vm.collections.items) { [weak self] value in
            self?.collections = (value as? [CollectionItem]) ?? []
        }
    }

    func selectTab(_ id: String) {
        vm.selectTab(id: id)
    }
}

struct ContentView: View {
    @StateObject private var store = BoxStore()

    var body: some View {
        TabView(selection: Binding(
            get: { store.selectedTabId },
            set: { store.selectTab($0) }
        )) {
            ForEach(store.tabs, id: \.id) { tab in
                NavigationStack {
                    screen(for: tab.id)
                        .navigationTitle(tab.title)
                }
                .tabItem {
                    Label(tab.title, image: "tab_\(tab.icon)")
                }
                .tag(tab.id)
            }
        }
        .frame(minWidth: 480, minHeight: 360)
    }

    @ViewBuilder
    private func screen(for id: String) -> some View {
        switch id {
        case "notes":
            List(store.notes, id: \.title) { note in
                VStack(alignment: .leading) {
                    Text(note.title).font(.headline)
                    Text(note.preview).font(.subheadline).foregroundStyle(.secondary)
                    Text(note.updated).font(.caption).foregroundStyle(.secondary)
                }
            }
        case "collections":
            List(store.collections, id: \.name) { c in
                VStack(alignment: .leading) {
                    Text("\(c.name) (\(c.itemCount))").font(.headline)
                    Text(c.description_).font(.subheadline).foregroundStyle(.secondary)
                }
            }
        default:
            List(store.files, id: \.name) { file in
                VStack(alignment: .leading) {
                    Text((file.kind == "folder" ? "📁 " : "📄 ") + file.name).font(.headline)
                    Text([file.size, file.modified].compactMap { $0 }.joined(separator: " · "))
                        .font(.caption).foregroundStyle(.secondary)
                }
            }
        }
    }
}

@main
struct MacApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
