package ice.ccylice.creative.create_activity;

public class bCreative {
    public static String a(String name, String phone, String email) {
        return "BEGIN:VCARD\nVERSION:3.0\nN:" + b(name) + "\nFN:" + b(name) + "\nTEL;HOME;VOICE:" + b(phone) + "\nEMAIL;PREF;INTERNET:" + b(email) + "\nEND:VCARD";
    }

    private static String b(String str) {
        return str.replace("\n", "").replace("\r", "");
    }
}