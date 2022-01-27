//
//  Created by Olof Thorén on 2022-01-18.
//

import Foundation

class ParseTable {
    
    let docURL: URL
    
    init() {
        guard #available(macOS 10.15, *) else { exit(1) }
        
        let fileManager = FileManager.default
        let script = CommandLine.arguments[0];

        let path = URL(fileURLWithPath: fileManager.currentDirectoryPath).appendingPathComponent(script)
            .absoluteString
        guard let end = path.range(of: "merchant/documentation/")?.upperBound else {
            fatalError("You must run this from the terminal:\ncd documentation/stringsGenerator \nswift run")
        }
        let documentationPath = String(path[path.startIndex..<end])
        let localizationSource = documentationPath + "localization-strings.md"
        
        //create one folder for all localizations
        docURL = URL(string: documentationPath)!.deletingLastPathComponent().appendingPathComponent("localizations", isDirectory: true)
        try? fileManager.createDirectory(at: docURL, withIntermediateDirectories: false, attributes: nil)
        let languages = [LanguageFile(isoName: "en", language: .english), LanguageFile(isoName: "nb", language: .norwegian), LanguageFile(isoName: "sv", language: .swedish)]
        

        let sourceURL = URL(string: localizationSource)!
        //print("filepath given to script \(fileURL)")
        guard let contents = try? Data(contentsOf: sourceURL), let sourceFile = String(data: contents, encoding: .utf8) else {
            fatalError("Should never fail, are files missing?")
        }
        //detect linefeed or normal newlines
        let normalEndings = sourceFile.range(of: "\r\n")?.isEmpty ?? true
        let parsed:[LocalizationRow] = sourceFile.split(separator: normalEndings ? "\n" : "\r\n")
            .compactMap {
            fetchRow(String($0))
        }
        
        for language in languages {
            
            //generate strings files for Android
            generateAndroidLocalization(language, parsed)
            //generate strings files for iOS
            generateIOSLocalization(language, parsed)
        }
    }
    
    var startOfFile = false
    
    /// Fetch rows from the table and parse them
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
    
    func transformRows(_ language: LanguageFile, _ parsed: [LocalizationRow], _ transform: (_ key: String, _ word: String) -> String) -> [String] {
        
        let strings: [String] = parsed.compactMap { row in
            
            var word: String
            switch language.language {
                case .swedish:
                    word = row.swedish
                case .norwegian:
                    word = row.norwegian
                case .english:
                    word = row.english
            }
            
            if word.isEmpty {
                return nil
            }
            return transform(row.key, word)
        }
        return strings
    }
    
    
    func generateIOSLocalization(_ language: LanguageFile, _ parsed: [LocalizationRow]) {
        
        let strings = transformRows(language, parsed) { key, word in
            
            //should look like this: "maybeStuckAlertTitle" = "Stuck?";
            """
            "\(key)" = "\(word)";
            """
        }
        
        let file = String(strings.joined(separator: "\n"))
        let data = file.data(using: .utf8)!
        
        let folder = docURL.appendingPathComponent("\(language.isoName).lproj", isDirectory: true)
        try? FileManager.default.createDirectory(at: folder, withIntermediateDirectories: false, attributes: nil)
        do {
            try data.write(to: folder.appendingPathComponent("SwedbankPaySDKLocalizable.strings"))
        } catch {
            print("error writing data: \(error)")
        }
        
        //print(file)
    }
    
    func generateAndroidLocalization(_ language: LanguageFile, _ parsed: [LocalizationRow]) {
        
        let strings = transformRows(language, parsed) { key, word in
            
            let word = word.replacingOccurrences(of: "'", with: "\'")
            
            """
                <string name="\(key)">\(word)</string>
            """
        }
        let file = """
        <?xml version="1.0" encoding="utf-8"?>
        <resources>
        \(String(strings.joined(separator: "\n")))
        </resources>
        """
        
        let data = file.data(using: .utf8)!
        let androidFolder = "values-\(language.isoName)"
        let folder = docURL.appendingPathComponent(androidFolder, isDirectory: true)
        try? FileManager.default.createDirectory(at: folder, withIntermediateDirectories: false, attributes: nil)
        do {
            try data.write(to: folder.appendingPathComponent("strings.xml"))
        } catch {
            print("error writing data: \(error)")
        }
        
        //print(file)
    }
}
