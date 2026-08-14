import SwiftUI
import KMPNativeCoroutinesAsync
import KMPObservableViewModelSwiftUI
import Shared

struct CatalogDetailView: View {
    @StateViewModel
    var viewModel = CatalogDetailViewModel(
        catalogRepository: KoinDependencies().catalogRepository
    )

    let itemId: Int64

    var body: some View {
        Group {
            if let item = viewModel.item {
                ScrollView {
                    VStack(alignment: .leading, spacing: 12) {
                        AsyncImage(url: URL(string: item.imageUrl)) { phase in
                            switch phase {
                            case .success(let image):
                                image
                                    .resizable()
                                    .scaledToFill()
                                    .clipped()
                            default:
                                ProgressView()
                                    .frame(maxWidth: .infinity, minHeight: 220)
                            }
                        }
                        VStack(alignment: .leading, spacing: 6) {
                            Text(item.breed)
                                .font(.title)
                            Text("Source: \(item.source)")
                            Text("Id: \(item.id)")
                        }
                        .padding(16)
                    }
                }
                .navigationTitle(item.breed)
            } else {
                ProgressView()
            }
        }
        .onAppear {
            viewModel.setId(id: itemId)
        }
    }
}
