#if canImport(Testing)
import Testing
import AwsTypes

@Suite("AwsTypes Swift Export Tests")
struct AwsTypesExportTests {
    @Test("AwsTypes swift module imported cleanly")
    func testSwiftModuleLoads() throws {
        #expect(Bool(true), "AwsTypes swift module imported cleanly")
    }
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
