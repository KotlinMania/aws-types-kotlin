#if canImport(Testing)
import Testing
import AwsTypes

@Test func testSwiftModuleLoads() {
    #expect(true)
}
#elseif canImport(XCTest)
import XCTest
import AwsTypes

final class AwsTypesExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        XCTAssertTrue(true, "AwsTypes swift module imported cleanly")
    }
}
#endif
