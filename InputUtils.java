public class InputUtils {
    // Accepts numbers in formats: 0, 0.xxx, 1, 123, 123.45
    // Rejects numbers with leading zeros like 01000 or 00.5
    public static boolean isValidAmountString(String s) {
        if (s == null) return false;
        s = s.trim();
        // regex: integer part is either 0 OR non-zero-leading digits; optional fractional part
        return s.matches("^(?:0|[1-9]\\d*)(?:\\.\\d+)?$");
    }

    public static double parseAmountOrNaN(String s) {
        if (!isValidAmountString(s)) return Double.NaN;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
}
