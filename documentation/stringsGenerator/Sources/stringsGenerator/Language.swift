//
//  Created by Olof Thorén on 2022-01-21.
//

import Foundation

struct LanguageFile {
    let isoName: String
    let language: LanguageType
}

enum LanguageType: String {

    case english
    case norwegian
    case swedish
}
