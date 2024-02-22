package com.intuit.karate.driver;

import com.intuit.karate.TestUtils;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author pthomas3
 */
class DriverOptionsTest {

    static void test(String in, String out) {
        assertEquals(out, DriverOptions.preProcessWildCard(in));
    }

    @Test
    void testPreProcess() {
        test("{}hi", "//*[normalize-space(text())='hi']");
        test("{^}hi", "//*[contains(normalize-space(text()),'hi')]");
        test("{^:}hi", "//*[contains(normalize-space(text()),'hi')]");
        test("{^:0}hi", "//*[contains(normalize-space(text()),'hi')]");
        test("{^:2}hi", "/(//*[contains(normalize-space(text()),'hi')])[2]");
        test("{:2}hi", "/(//*[normalize-space(text())='hi'])[2]");
        test("{a}hi", "//a[normalize-space(text())='hi']");
        test("{a:2}hi", "/(//a[normalize-space(text())='hi'])[2]");
        test("{^a:}hi", "//a[contains(normalize-space(text()),'hi')]");
        test("{^a/p}hi", "//a/p[contains(normalize-space(text()),'hi')]");
        test("{^a:2}hi", "/(//a[contains(normalize-space(text()),'hi')])[2]");
    }

    @Test
    void testRetry() {
        DriverOptions options = new DriverOptions(Collections.EMPTY_MAP, TestUtils.runtime(), 0, null);
        options.retry(() -> 1, x -> x < 5, "not 5", false);
    }

    // Function had 27% coverage before all tests
    // Coverage increased to 33%
    @Test
    void testSelectorWithLocatorThatStartWithLeftParentheses() {

        String locator = "(test string)";
        String contextNode = "test string";

        String result = DriverOptions.selector(locator, contextNode);

        assertEquals("(test string)", result);
    }

    // Coverage increased to 38%
    @Test
    void testSelectorWithLoctorThatsStartsWithOtherParentheses() {

        String locator = "{}";
        String contextNode = "test string";

        String result = DriverOptions.selector(locator, contextNode);

        assertFalse(result.contains(locator));
    }

    // coverage increased to 50%
    @Test
    void testSelectorWithForwardSlash() {

        String locator = "/tester";
        String contextNode = "document";

        String result = DriverOptions.selector(locator, contextNode);

        assertTrue(result.startsWith("document.evaluate"));
    }

    // coverage increased to 61%
    @Test
    void testSelectorWithContextNodeEqualToDocument() {

        String locator = "/(tester";
        String contextNode = "document";

        String result = DriverOptions.selector(locator, contextNode);

        assertTrue(result.startsWith("document.evaluate"));
    }

    // coverage increased to 66%
    @Test
    void testSelectorWithContextNodeNotEqualToDocument() {

        String locator = "/(tester";
        String contextNode = "notdocument";

        String result = DriverOptions.selector(locator, contextNode);

        assertTrue(result.startsWith("document.evaluate"));
    }
}
