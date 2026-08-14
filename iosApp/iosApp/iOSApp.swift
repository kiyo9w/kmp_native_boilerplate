import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        KoinKt.doInitKoin(driverFactory: IosDatabaseDriverFactory())
    }

    var body: some Scene {
        WindowGroup {
            CatalogListView()
        }
    }
}
