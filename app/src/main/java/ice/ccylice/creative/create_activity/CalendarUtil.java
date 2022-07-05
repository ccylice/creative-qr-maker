package ice.ccylice.creative.create_activity;

public class CalendarUtil {
    public static String a(String str, String str2, String str3, String str4, String str5) {
        return "BEGIN:VEVENT\nSUMMARY:" + str + "\nDTSTART;VALUE=DATE:" + str2 + "\nDTEND;VALUE=DATE:" + str3 + "\nLOCATION:" + str4 + "\nDESCRIPTION:" + str5 + "\nEND:VEVENT\n";
    }

    public static String b(String str, String str2, String str3, String str4, String str5) {
        return "BEGIN:VEVENT\nSUMMARY:" + str + "\nDTSTART:" + str2 + "\nDTEND:" + str3 + "\nLOCATION:" + str4 + "\nDESCRIPTION:" + str5 + "\nEND:VEVENT\n";
    }
}