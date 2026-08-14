import SwiftUI
import Shared

enum KitTheme {
    static let spaceXs = CGFloat(ThemeTokens.shared.spaceXs)
    static let spaceSm = CGFloat(ThemeTokens.shared.spaceSm)
    static let spaceMd = CGFloat(ThemeTokens.shared.spaceMd)
    static let spaceLg = CGFloat(ThemeTokens.shared.spaceLg)
    static let radiusMd = CGFloat(ThemeTokens.shared.radiusMd)
    static let tapMin = CGFloat(ThemeTokens.shared.tapMin)
}

struct KitEmpty: View {
    let message: String
    var actionLabel: String? = nil
    var onAction: (() -> Void)? = nil

    var body: some View {
        VStack(spacing: KitTheme.spaceSm) {
            Text(message)
                .font(.body)
                .foregroundStyle(.secondary)
                .multilineTextAlignment(.center)
            if let actionLabel, let onAction {
                Button(actionLabel, action: onAction)
                    .frame(minHeight: KitTheme.tapMin)
            }
        }
        .padding(KitTheme.spaceLg)
    }
}

struct KitBanner: View {
    let message: String
    var actionLabel: String? = nil
    var onAction: (() -> Void)? = nil

    var body: some View {
        HStack(alignment: .center, spacing: KitTheme.spaceSm) {
            Text(message)
                .font(.subheadline)
                .foregroundStyle(.primary)
                .frame(maxWidth: .infinity, alignment: .leading)
            if let actionLabel, let onAction {
                Button(actionLabel, action: onAction)
            }
        }
        .padding(KitTheme.spaceMd)
        .background(.red.opacity(0.12), in: RoundedRectangle(cornerRadius: KitTheme.radiusMd))
        .padding(.horizontal, KitTheme.spaceMd)
    }
}
