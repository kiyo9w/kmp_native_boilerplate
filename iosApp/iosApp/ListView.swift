import SwiftUI
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI
import Shared

struct CatalogListView: View {
    @StateViewModel
    var viewModel = CatalogListViewModel(
        catalogRepository: KoinDependencies().catalogRepository,
        sessionStore: KoinDependencies().sessionStore,
        appVersionReader: KoinDependencies().appVersionReader
    )

    let columns = [
        GridItem(.adaptive(minimum: 140), alignment: .top)
    ]

    var body: some View {
        NavigationStack {
            Group {
                if !viewModel.items.isEmpty {
                    VStack(spacing: 0) {
                        if let error = viewModel.errorMessage {
                            KitBanner(
                                message: error,
                                actionLabel: String(localized: "action_retry"),
                                onAction: { viewModel.refresh() }
                            )
                        }
                        ScrollView {
                            LazyVGrid(columns: columns, alignment: .leading, spacing: KitTheme.spaceMd) {
                                ForEach(viewModel.items, id: \.id) { item in
                                    NavigationLink(destination: CatalogDetailView(itemId: item.id)) {
                                        CatalogFrame(item: item)
                                    }
                                    .buttonStyle(.plain)
                                }
                            }
                            .padding(.horizontal, KitTheme.spaceMd)
                        }
                    }
                } else if viewModel.isRefreshing {
                    ProgressView()
                } else {
                    KitEmpty(
                        message: viewModel.errorMessage ?? String(localized: "no_data_available"),
                        actionLabel: String(localized: "action_retry"),
                        onAction: { viewModel.refresh() }
                    )
                }
            }
            .navigationTitle(String(localized: "app_name"))
            .navigationBarTitleDisplayMode(.large)
            .safeAreaInset(edge: .bottom) {
                Text(
                    String(
                        format: String(localized: "label_version"),
                        viewModel.appVersion.name,
                        viewModel.appVersion.build
                    )
                )
                .font(.caption)
                .foregroundStyle(.secondary)
                .padding(.bottom, KitTheme.spaceSm)
            }
            .refreshable { viewModel.refresh() }
        }
    }
}

struct CatalogFrame: View {
    let item: CatalogItem

    var body: some View {
        VStack(alignment: .leading, spacing: KitTheme.spaceXs) {
            AsyncImage(url: URL(string: item.imageUrl)) { phase in
                switch phase {
                case .empty:
                    ProgressView()
                        .frame(maxWidth: .infinity)
                        .aspectRatio(1, contentMode: .fit)
                case .success(let image):
                    image
                        .resizable()
                        .scaledToFill()
                        .aspectRatio(1, contentMode: .fill)
                        .clipped()
                default:
                    Color.secondary.opacity(0.15)
                        .aspectRatio(1, contentMode: .fit)
                }
            }
            Text(item.breed)
                .font(.headline)
            Text(item.source)
                .font(.caption)
                .foregroundStyle(.secondary)
        }
    }
}
