public class InputUtils {
    public static boolean isValidAmountString(String s) {
        if (s == null) return false;
        s = s.trim();
        
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
