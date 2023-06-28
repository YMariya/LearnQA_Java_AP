import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class WordLength {
    @Test
    public void testStringLength() {
        String text = "Текст больше 15 символов";
       // String text = "Меньше 15";
        assertTrue(text.length() > 15, "Текст должен быть длиннее 15 символов");
    }
}
