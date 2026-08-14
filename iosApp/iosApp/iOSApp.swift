import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        let flavorName = Bundle.main.object(forInfoDictionaryKey: "APP_ENVIRONMENT") as? String ?? "debug"
        // Bind Crashlytics or Sentry with doInitKoinIos(flavorName:crashReporter:).
        KoinDependenciesKt.doInitKoinIos(flavorName: flavorName)
    }

    var body: some Scene {
        WindowGroup {
            CatalogListView()
                .preferredColorScheme(nil)
        }
    }
}
