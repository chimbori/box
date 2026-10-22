import Shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        Text(Greeting().greet())
            .font(.largeTitle)
            .padding()
            .frame(minWidth: 320, minHeight: 200)
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
