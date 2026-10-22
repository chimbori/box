import Shared
import SwiftUI

struct ContentView: View {
    var body: some View {
        Text(Greeting().greet())
            .font(.largeTitle)
            .padding()
    }
}

@main
struct iosApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
