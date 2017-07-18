package cn.hyperchain.hypermvc.register.jopo;

/**
 * @Package cn.hyperchain.hypermvc.register.jopo
 * @Author Martin
 * @Time 17/7/14
 */
public class HyperFieldInfo {

    public String name;
    public String description;
    public String entityName;

    public Integer integer;
    public Integer fraction;

    public Boolean isId;
    public Boolean isIndex;
    public Boolean isForeignKey;
    public String foreignKeyEntityName;

    public Boolean isAddress;
    public Boolean isEnglish;

}
