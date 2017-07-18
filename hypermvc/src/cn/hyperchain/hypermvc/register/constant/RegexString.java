package cn.hyperchain.hypermvc.register.constant;

/**
 * @Package cn.hyperchain.hypermvc.register.constant
 * @Author Martin
 * @Time 17/7/14
 */
public final class RegexString {
    public static final String entityName = "(?<=class ).+(?=\\{)";
    public static final String entityDescription = "(?<=@ApiModel\\(\").+(?=\")";
    public static final String entityFieldDescription = "(?<=@ApiModelProperty\\(\").+(?=\")";
    public static final String max = "(?<=max = )\\d+";
    public static final String integer = "(?<=integer = )\\d+";
    public static final String fraction = "(?<=fraction = )\\d+";
    public static final String fieldName = "(?<=private String ).*(?=;)";
}
