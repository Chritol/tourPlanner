package helpers;

import at.technikum.tolanzeilinger.tourplanner.util.StringUtilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilitiesTest {
    @Test
    public void testClearTrailingWhitespaces_InputWithTrailingWhitespaces_ReturnsStringWithoutTrailingWhitespaces() {
        // Arrange
        String input = "  Test String   ";
        String expectedOutput = "Test String";

        // Act
        String result = StringUtilities.clearTrailingWhitespaces(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testClearTrailingWhitespaces_InputWithoutTrailingWhitespaces_ReturnsSameString() {
        // Arrange
        String input = "Test String";
        String expectedOutput = "Test String";

        // Act
        String result = StringUtilities.clearTrailingWhitespaces(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    public void testClearTrailingWhitespaces_NullInput_ReturnsNull() {
        // Arrange
        String input = null;

        // Act
        String result = StringUtilities.clearTrailingWhitespaces(input);

        // Assert
        assertNull(result);
    }

    @Test
    public void testIsNullOrWhitespace_InputWithWhitespaces_ReturnsFalse() {
        // Arrange
        String input = "  Test String  ";

        // Act
        boolean result = StringUtilities.isNullOrWhitespace(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsNullOrWhitespace_InputWithoutWhitespaces_ReturnsFalse() {
        // Arrange
        String input = "TestString";

        // Act
        boolean result = StringUtilities.isNullOrWhitespace(input);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testIsNullOrWhitespace_NullInput_ReturnsTrue() {
        // Arrange
        String input = null;

        // Act
        boolean result = StringUtilities.isNullOrWhitespace(input);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testIsNullOrWhitespace_InputWithOnlyWhitespaces_ReturnsTrue() {
        // Arrange
        String input = "     ";

        // Act
        boolean result = StringUtilities.isNullOrWhitespace(input);

        // Assert
        assertTrue(result);
    }
}
