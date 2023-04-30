package ws.tool.easyexcel.pojo;

/**
 * @author WindShadow
 * @version 2023-04-29.
 */

public enum Gender {

    MALE("男"),
    FEMALE("女");

    private final String value;


    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Gender of(String value) {

        if ("男".equals(value)) {

            return MALE;
        } else if ("女".equals(value)) {

            return FEMALE;
        } else if (value == null){
            return null;
        } else {
            throw new IllegalArgumentException("An unexpected value: " + value);
        }
    }
}
