//
//  Created by Olof Thorén on 2022-01-18.
//

import Foundation

struct LocalizationRow: Decodable {
    let key: String
    let description: String
    let english: String
    let norwegian: String
    let swedish: String
}
