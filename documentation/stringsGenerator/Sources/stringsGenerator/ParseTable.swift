//
//  Created by Olof Thorén on 2022-01-18.
//

import Foundation

struct LanguageFile {
    let folderName: String
    let language: LanguageType
}

enum LanguageType: String {
    
    case english
    case norwegian
    case swedish
}

class ParseTable {
    
    init() {
        guard #available(macOS 10.15, *) else { exit(1) }
        
        let fileManager = FileManager.default
        let script = CommandLine.arguments[0];

        let path = URL(fileURLWithPath: fileManager.currentDirectoryPath).appendingPathComponent(script)
            .absoluteString
        let end = path.range(of: "merchant/documentation/")!.upperBound
        let documentationPath = String(path[path.startIndex..<end])
        let fullString = documentationPath + "localization-strings.md"

        let fileURL = URL(string: fullString)!
        //print("filepath given to script \(fileURL)")
        let file = String(data: try! Data(contentsOf: fileURL), encoding: .utf8)!
        //detect linefeed or normal newlines
        let normal = file.range(of: "\r\n")?.isEmpty ?? true
        let parsed:[LocalizationRow] = file.split(separator: normal ? "\n" : "\r\n")
            .compactMap {
            fetchRow(String($0))
        }
        
        //generate strings files for iOS
        let docURL = URL(string: documentationPath)!.deletingLastPathComponent().appendingPathComponent("strings", isDirectory: true)
        try? fileManager.createDirectory(at: docURL, withIntermediateDirectories: false, attributes: nil)
        
        let languages = [LanguageFile(folderName: "en.lproj", language: .english), LanguageFile(folderName: "nb.lproj", language: .norwegian), LanguageFile(folderName: "sv.lproj", language: .swedish)]
        for language in languages {
            let data = generateStringsFile(language, parsed)
            let folder = docURL.appendingPathComponent(language.folderName, isDirectory: true)
            try? fileManager.createDirectory(at: folder, withIntermediateDirectories: false, attributes: nil)
            do {
                try data.write(to: folder.appendingPathComponent("SwedbankPaySDKLocalizable.strings"))
            } catch {
                print("error writing data: \(error)")
            }
        }
    }
    
    var startOfFile = false
    func fetchRow(_ next: String) -> LocalizationRow? {
        
        //skip the first info section to the start of the table
        if !startOfFile {
            if next.contains("| ---") {
                startOfFile = true
            }
            return nil
        }
        
        //trailing whitespace creates an extra column
        let row = next.trimmingCharacters(in: .whitespacesAndNewlines).split(separator: "|")
        
        if row.count != 5 {
            print("Error count \(row.count): table is bad: \(next)\n\(row)")
            return nil
        }
        let cleanedRow = row.map { String($0).trimmingCharacters(in: .whitespacesAndNewlines.union(.punctuationCharacters)) }
        return LocalizationRow(key: cleanedRow[0], description: cleanedRow[1], english: cleanedRow[2], norwegian: cleanedRow[3], swedish: cleanedRow[4])
    }
    
    func generateStringsFile(_ language: LanguageFile, _ parsed: [LocalizationRow]) -> Data {
        
        let strings: [String] = parsed.compactMap { row in
            
            //should look like this: "OK" = "OK";
            var word: String
            switch language.language {
                case .swedish:
                    word = row.swedish
                case .norwegian:
                    word = row.norwegian
                case .english:
                    word = row.english
            }
            
            return "\"\(row.key)\" = \"\(word)\";"
        }
        let file = String(strings.joined(separator: "\n"))
        //print(file)
        return file.data(using: .utf8)!
    }
}
